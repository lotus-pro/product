package com.platform.core.adapter;

import com.platform.common.cache.Cache;
import com.platform.core.filter.FunctionPermissionFilter;
import com.platform.core.filter.JwtAuthorizationTokenFilter;
import com.platform.core.filter.JwtTokenDebugFilter;
import com.platform.core.filter.LoginPreFilter;
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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置让swagger跳过spring-security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL = "/auth/system/login";
    private static final String LOGIN_OUT = "/auth/system/loginOut";
    private static final String LOGIN_SUCCESS_URL = LOGIN_URL.concat("/success");
    private static final String LOGIN_FAILURE_URL = LOGIN_URL.concat("/failure");
    private static final String LOGIN_OUT_FAILURE_URL = LOGIN_OUT.concat("/failure");
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private ProductUserDetailsService productUserDetailsService;
    @Value("${pte.jwt.offlineUserEnable:false}")
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

    //    @Conditional({IsPcmcCondition.class})
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(this.productUserDetailsService);
        provider.setPasswordEncoder(this.passwordEncoderBean());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] URL_WHITELIST = {
            "/auth/system/kaptcha",
            "/auth/system/login"
    };

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((HttpSecurity)((HttpSecurity)httpSecurity.csrf().disable()).cors().and()).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and())
                .authorizeRequests().anyRequest())
                .authenticated().and()).exceptionHandling().accessDeniedHandler(new ProductAccessDeniedHandler()).authenticationEntryPoint(new ProductAuthenticationEntryPoint()).and())
                .addFilterBefore(this.jwtTokenDebugFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(this.jwtAuthorizationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.functionPermissionFilter(), FilterSecurityInterceptor.class);

        ((HttpSecurity) httpSecurity.authorizeRequests().and())
                .addFilterBefore(this.loginPreFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginProcessingUrl(LOGIN_URL).successForwardUrl(LOGIN_SUCCESS_URL)
                .failureHandler(this.loginFailureHandler());
    }

    public void configure(WebSecurity web) {
        //配置需要跳过security过滤的资源或者接口
        web.ignoring().antMatchers(new String[]{
                "/v2/api-docs", "/favicon.ico", "/css/**",
                "/swagger-resources", "/swagger*",
                "/swagger-resources/**", "/configuration/ui",
                "/configuration/security", "/swagger-ui.html/**",
                "/webjars/**", "/druid/**", "/actuator/**",
                "/auth/system/kaptcha", "/auth/system/kaptcha", "/rest/**",
                "/messageServer/**", "/app/**"});
    }

}
