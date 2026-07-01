package com.kavya.societymaintenance.dto.Response;

import java.time.LocalDateTime;

import com.kavya.societymaintenance.enumerated.ComplaintCategory;
import com.kavya.societymaintenance.enumerated.ComplaintPriority;
import com.kavya.societymaintenance.enumerated.ComplaintStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintResponse {

    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private ComplaintStatus status;

    private ComplaintPriority priority;

    private ComplaintCategory category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String residentName;

}
