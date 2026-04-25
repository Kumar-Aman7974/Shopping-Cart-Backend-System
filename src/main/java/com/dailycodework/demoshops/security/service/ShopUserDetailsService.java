package com.dailycodework.demoshops.security.service;

import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.repository.UserRepository;
import com.dailycodework.demoshops.security.user.ShopUserDetails;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() ->
                       new  UsernameNotFoundException("User not found Exceptions!"));

        return ShopUserDetails.buildUserDetails(user);
    }
}
