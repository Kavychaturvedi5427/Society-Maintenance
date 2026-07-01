package com.kavya.societymaintenance.dto.Response;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalComplaints;

    private long pending;

    private long inProgress;

    private long resolved;

    private Map<String, Long> categoryWise;
}