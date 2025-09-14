package com.doljabi.Outdoor_Escape_Room.user.application;

import com.doljabi.Outdoor_Escape_Room.auth.domain.RefreshTokenRepository;
import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.user.domain.User;
import com.doljabi.Outdoor_Escape_Room.user.domain.UserRepository;
import com.doljabi.Outdoor_Escape_Room.user.presentation.dto.response.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProfileResponse findUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(GlobalErrorCode.USER_NOT_FOUND));
        return new ProfileResponse(userId, user.getName(), user.getProvider());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProfileResponse updateUserProfile(Long userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(GlobalErrorCode.USER_NOT_FOUND));
        user.updateUser(name);
        return new ProfileResponse(userId, user.getName(), user.getProvider());

    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(GlobalErrorCode.USER_NOT_FOUND);
        }
        refreshTokenRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
        return "회원 탈퇴 완료";
    }
}
