package com.platform.config.user;

import com.platform.config.entity.UserDetailInfo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProductUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        PcmcUser pcmcUser = this.pcmcUserService.queryPcmcUser(username);
        UserDetailInfo productUser = new UserDetailInfo();
        productUser.setUserName("zengzheng");
        if (null == productUser) {
            throw new UsernameNotFoundException("product.error.00003");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encode = encoder.encode("888888");
            return User.withUsername("zengzheng").password(encode).authorities(AuthorityUtils.NO_AUTHORITIES).build();
        }
    }
}
