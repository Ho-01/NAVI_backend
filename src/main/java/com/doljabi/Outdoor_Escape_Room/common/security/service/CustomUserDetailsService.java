package com.doljabi.Outdoor_Escape_Room.common.security.service;

import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import com.doljabi.Outdoor_Escape_Room.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId: "+userId+"에 해당하는 유저 찾을 수 없음"));
        return new CustomUserDetails(user);
    }
}
