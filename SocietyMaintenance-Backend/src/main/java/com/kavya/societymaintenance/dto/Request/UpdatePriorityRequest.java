package com.kavya.societymaintenance.dto.Request;

import com.kavya.societymaintenance.enumerated.ComplaintPriority;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePriorityRequest {
    @NotNull
    private ComplaintPriority priority;
    
}