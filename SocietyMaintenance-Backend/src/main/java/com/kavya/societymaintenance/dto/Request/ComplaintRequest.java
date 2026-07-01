package com.kavya.societymaintenance.dto.Request;

import com.kavya.societymaintenance.enumerated.ComplaintCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintRequest {

    @NotBlank(message = "Title is requried")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String imageUrl;

    private ComplaintCategory category;

}
