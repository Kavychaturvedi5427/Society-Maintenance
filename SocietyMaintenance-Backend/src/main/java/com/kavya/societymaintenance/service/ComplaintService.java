package com.kavya.societymaintenance.service;

import com.kavya.societymaintenance.dto.Request.ComplaintRequest;
import com.kavya.societymaintenance.dto.Request.UpdateComplaintRequest;
import com.kavya.societymaintenance.dto.Request.UpdatePriorityRequest;
import com.kavya.societymaintenance.dto.Response.ComplaintHistoryResponse;
import com.kavya.societymaintenance.dto.Response.ComplaintResponse;
import com.kavya.societymaintenance.dto.Response.DashboardResponse;

import java.util.*;


public interface ComplaintService {
    
    ComplaintResponse createComplaint(ComplaintRequest request);

    List<ComplaintResponse> getAllComplaints();

    List<ComplaintResponse> getMycomplaints();

    ComplaintResponse updateComplaintStatus(Long id, UpdateComplaintRequest request);

    ComplaintResponse updateComplaintPriority(Long id, UpdatePriorityRequest request);

    DashboardResponse getDashboard();

    List<ComplaintHistoryResponse> getComplaintHistory(Long complaintId);

}
