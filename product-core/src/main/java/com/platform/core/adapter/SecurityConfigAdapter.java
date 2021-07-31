package com.platform.core.adapter;

import com.platform.common.cache.Cache;
import com.platform.core.filter.FunctionPermissionFilter;
import com.platform.core.filter.JwtAuthorizationTokenFilter;
import com.platform.core.filter.JwtTokenDebugFilter;
import com.platform.core.filter.LoginPreFilter;
import com.platform.core.handler.JwtLogoutSuccessHandler;
import com.platform.core.handler.LoginFailureHandler;
import com.platform.core.handler.ProductAccessDeniedHandler;
import com.platform.core.handler.ProductAuthenticationEntryPoint;
import com.platform.core.userdetail.ProductUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 配置让swagger跳过spring-security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL = "/auth/system/login";
    private static final String LOGIN_OUT = "/auth/system/loginOut";
    private static final String LOGIN_SUCCESS_URL = LOGIN_URL.concat("/success");
    private static final String LOGIN_FAILURE_URL = LOGIN_URL.concat("/failure");
    private static final String LOGIN_OUT_FAILURE_URL = LOGIN_OUT.concat("/failure");

    @Autowired
    private ProductUserDetailsService productUserDetailsService;
    @Value("${product.jwt.offlineUserEnable:false}")
    private boolean offlineUserEnable;

    @Autowired
    Cache cache;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter() {
        return new JwtAuthorizationTokenFilter(this.offlineUserEnable);
    }

    public JwtTokenDebugFilter jwtTokenDebugFilter() {
        return new JwtTokenDebugFilter();
    }

    public LoginPreFilter loginPreFilter() {
        return new LoginPreFilter("/auth/system/login", cache);
    }

    public FunctionPermissionFilter functionPermissionFilter() {
        return new FunctionPermissionFilter();
    }

    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    public JwtLogoutSuccessHandler logoutSuccessHandler(){
        return new JwtLogoutSuccessHandler();
    }

    public ProductAuthenticationEntryPoint authenticationEntryPoint(){
        return new ProductAuthenticationEntryPoint();
    }

    public ProductAccessDeniedHandler accessDeniedHandler(){
        return new ProductAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] URL_WHITELIST = {
            "/no-auth/**","/favicon.ico", "/swagger-ui.html",
    };

    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                //登录配置
                .formLogin().loginPage("/login")
                .loginProcessingUrl(LOGIN_URL)
                .successForwardUrl(LOGIN_SUCCESS_URL)
                .failureHandler(this.loginFailureHandler())

                .and()
                .logout()
                .logoutSuccessHandler(this.logoutSuccessHandler())

                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()

                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint())
                .accessDeniedHandler(this.accessDeniedHandler())

                // 配置自定义的过滤器
                .and()
                .addFilterBefore(this.jwtTokenDebugFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(this.jwtAuthorizationTokenFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(this.loginPreFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.functionPermissionFilter(), FilterSecurityInterceptor.class);

    }

    public void configure(WebSecurity web) {
        //配置需要跳过security过滤的资源或者接口
        web.ignoring().antMatchers(new String[]{
                "/v2/api-docs", "/favicon.ico", "/css/**",
                "/swagger-resources", "/swagger*",
                "/swagger-resources/**", "/configuration/ui",
                "/configuration/security", "/swagger-ui.html/**",
                "/webjars/**", "/druid/**", "/actuator/**",
                "/no-auth/**", "/rest/**",
                "/messageServer/**", "/app/**"});
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(this.productUserDetailsService);
        provider.setPasswordEncoder(this.passwordEncoderBean());
        auth.authenticationProvider(provider);
    }

}
