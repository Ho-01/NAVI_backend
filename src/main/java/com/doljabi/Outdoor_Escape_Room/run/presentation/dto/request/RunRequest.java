package com.doljabi.Outdoor_Escape_Room.run.presentation.dto.request;

import com.doljabi.Outdoor_Escape_Room.run.domain.Scenario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RunRequest {
    private Scenario scenario;
}
