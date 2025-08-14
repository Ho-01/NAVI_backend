package com.doljabi.Outdoor_Escape_Room.user.presentation.dto.response;

import com.doljabi.Outdoor_Escape_Room.user.domain.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String name;
    private Provider provider;
}
