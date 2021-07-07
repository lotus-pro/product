package com.platform.core.userdetail;

import com.platform.common.pojo.admin.ProductUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductUserDetailsService implements UserDetailsService {

    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProductUser productUser = this.userService.queryProductUser(username);
        if (null == productUser) {
            throw new UsernameNotFoundException("product.error.00003");
        } else {
            return User.withUsername(username).password(productUser.getUserPassword()).authorities(AuthorityUtils.NO_AUTHORITIES).build();
        }
    }
}
