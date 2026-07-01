package com.kavya.societymaintenance.dto.Response;

import java.time.LocalDateTime;

import com.kavya.societymaintenance.enumerated.ComplaintStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintHistoryResponse {

    private ComplaintStatus oldStatus;

    private ComplaintStatus newStatus;

    private String changedBy;

    private String note;

    private LocalDateTime changedAt;
}