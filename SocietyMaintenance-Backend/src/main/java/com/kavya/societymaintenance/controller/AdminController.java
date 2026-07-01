package com.kavya.societymaintenance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kavya.societymaintenance.dto.Request.UpdateComplaintRequest;
import com.kavya.societymaintenance.dto.Request.UpdatePriorityRequest;
import com.kavya.societymaintenance.dto.Response.ComplaintResponse;
import com.kavya.societymaintenance.dto.Response.DashboardResponse;
import com.kavya.societymaintenance.service.ComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ComplaintService complaintService;

    // --------------------------------------- Admin Endpoints
    // -----------------------------------------------

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // for displaying the current complaint counts...
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(){
        return ResponseEntity.ok(complaintService.getDashboard());
    }
    

    // update status endpoint....
    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<ComplaintResponse> updateStatus(@PathVariable Long id,
            @Valid @RequestBody UpdateComplaintRequest request) {
        return ResponseEntity.ok(complaintService.updateComplaintStatus(id, request));
    }

    // updating the priority of the complaint....
    @PutMapping("/complaints/{id}/priority")
    public ResponseEntity<ComplaintResponse> updatePriority(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePriorityRequest request) {

        return ResponseEntity.ok(
                complaintService.updateComplaintPriority(id, request));
    }

}
