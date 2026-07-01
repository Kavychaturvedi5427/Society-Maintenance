package com.kavya.societymaintenance.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoticeRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private boolean important;
}