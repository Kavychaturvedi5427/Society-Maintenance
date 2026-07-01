package com.kavya.societymaintenance.controller;

import com.kavya.societymaintenance.service.ComplaintService;
import com.kavya.societymaintenance.service.NoticeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.kavya.societymaintenance.dto.Request.ComplaintRequest;
import com.kavya.societymaintenance.dto.Response.ComplaintHistoryResponse;
import com.kavya.societymaintenance.dto.Response.ComplaintResponse;
import com.kavya.societymaintenance.dto.Response.NoticeResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/complaints/")
public class ResidentController {

    private final ComplaintService complaintService;
    private final NoticeService noticeService;

    // -------------------------------------- Resident Endpoints
    // -------------------------------------------

    @GetMapping("/my")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaint() {
        return ResponseEntity.ok(complaintService.getMycomplaints());
    }

    @PostMapping
    public ResponseEntity<ComplaintResponse> registerComplaint(@Valid @RequestBody ComplaintRequest request) {
        System.out.println("Request Reached.");
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.createComplaint(request));
    }

    // complaint history mapping...
    @GetMapping("/{id}/history")
    public ResponseEntity<List<ComplaintHistoryResponse>> getComplaintHistory(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.getComplaintHistory(id));
    }

    // --------------------------------------- Notice Endpoints -----------------------------------------------

    @GetMapping("/notices")
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {

        return ResponseEntity.ok(
                noticeService.getAllNotices());
    }

}
