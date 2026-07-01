package com.kavya.societymaintenance.dto.Response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeResponse {

    private Long id;

    private String title;

    private String content;

    private boolean important;

    private String postedBy;

    private LocalDateTime createdAt;
}