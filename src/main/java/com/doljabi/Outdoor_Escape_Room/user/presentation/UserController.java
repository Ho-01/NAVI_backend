package com.doljabi.Outdoor_Escape_Room.user.presentation;

import com.doljabi.Outdoor_Escape_Room.common.api.ApiResponse;
import com.doljabi.Outdoor_Escape_Room.common.security.service.CustomUserDetails;
import com.doljabi.Outdoor_Escape_Room.user.application.UserService;
import com.doljabi.Outdoor_Escape_Room.user.presentation.dto.request.ProfileUpdateRequest;
import com.doljabi.Outdoor_Escape_Room.user.presentation.dto.response.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public ApiResponse<ProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(userService.findUserProfile(userDetails.getUserId()));
    }

    @PutMapping("/users/me")
    public ApiResponse<ProfileResponse> updateMyName(
            @RequestBody ProfileUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ApiResponse.success(userService.updateUserProfile(userDetails.getUserId(), request.getName()));
    }

    @DeleteMapping("/users/me")
    public ApiResponse<String> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ApiResponse.success(userService.deleteUser(userDetails.getUserId()));
    }
}
