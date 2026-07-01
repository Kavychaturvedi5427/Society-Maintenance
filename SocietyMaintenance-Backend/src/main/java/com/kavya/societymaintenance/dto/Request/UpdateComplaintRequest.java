package com.kavya.societymaintenance.dto.Request;

import com.kavya.societymaintenance.enumerated.ComplaintStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateComplaintRequest {
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Stauts is required")
    private ComplaintStatus status;

    private String note;

}
