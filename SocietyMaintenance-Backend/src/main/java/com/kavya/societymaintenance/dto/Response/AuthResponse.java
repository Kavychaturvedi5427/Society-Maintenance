package com.kavya.societymaintenance.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder        // with the help of this parameter position error is dealt...
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String type;
    private String role;
    private Long userId;
    private String name;
    private String email;
    private String message;
}
