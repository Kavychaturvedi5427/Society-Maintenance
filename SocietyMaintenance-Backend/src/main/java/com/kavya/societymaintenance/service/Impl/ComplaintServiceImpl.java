package com.kavya.societymaintenance.service.Impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kavya.societymaintenance.dto.Request.ComplaintRequest;
import com.kavya.societymaintenance.dto.Request.UpdateComplaintRequest;
import com.kavya.societymaintenance.dto.Request.UpdatePriorityRequest;
import com.kavya.societymaintenance.dto.Response.ComplaintHistoryResponse;
import com.kavya.societymaintenance.dto.Response.ComplaintResponse;
import com.kavya.societymaintenance.dto.Response.DashboardResponse;
import com.kavya.societymaintenance.entity.Complaint;
import com.kavya.societymaintenance.entity.ComplaintHistory;
import com.kavya.societymaintenance.entity.User;
import com.kavya.societymaintenance.enumerated.ComplaintCategory;
import com.kavya.societymaintenance.enumerated.ComplaintPriority;
import com.kavya.societymaintenance.enumerated.ComplaintStatus;
import com.kavya.societymaintenance.repository.ComplaintHistoryRepository;
import com.kavya.societymaintenance.repository.ComplaintRespository;
import com.kavya.societymaintenance.repository.UserRepository;
import com.kavya.societymaintenance.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRespository complaintRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final ComplaintHistoryRepository complaintHistoryRepository;

    // find the logged in user...
    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    // method for converting the complaint to the ComplaintResponse..
    private ComplaintResponse mapToResponse(Complaint complaint) {
        ComplaintResponse response = modelMapper.map(complaint, ComplaintResponse.class);
        response.setResidentName(complaint.getResident().getName());
        response.setPriority(complaint.getPriority());
        response.setCategory(complaint.getCategory());
        return response;
    }

    // method for converting to ComplaintHistoryResponse..
    private ComplaintHistoryResponse mapHistoryResponse(ComplaintHistory history) {

        return ComplaintHistoryResponse.builder()
                .oldStatus(history.getOldStatus())
                .newStatus(history.getNewStatus())
                .changedBy(history.getChangedBy())
                .note(history.getNote())
                .changedAt(history.getChangedAt())
                .build();
    }

    @Override
    public ComplaintResponse createComplaint(ComplaintRequest request) {

        // first get the logged in user...
        User user = getLoggedUser();

        Complaint complaint = Complaint.builder().title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .resident(user)
                .status(ComplaintStatus.PENDING)
                .priority(ComplaintPriority.LOW)
                .category(request.getCategory()).build();

        // save complaint..
        Complaint savedComplaint = complaintRepo.save(complaint);

        ComplaintHistory history = ComplaintHistory.builder()
                .complaint(savedComplaint)
                .oldStatus(null)
                .newStatus(savedComplaint.getStatus())
                .changedBy(savedComplaint.getResident().getEmail())
                .note("Complaint created")
                .changedAt(LocalDateTime.now())
                .build();

        complaintHistoryRepository.save(history);

        return mapToResponse(savedComplaint);
    }

    @Override
    public List<ComplaintResponse> getAllComplaints() {
        List<Complaint> allComplaints = complaintRepo.findAll();

        return allComplaints.stream().map(this::mapToResponse).toList();

    }

    @Override
    public List<ComplaintResponse> getMycomplaints() {
        User user = getLoggedUser();
        List<Complaint> myComplaints = complaintRepo.findByResident(user);

        // fetching each complaint from the list and mappping it to the
        // complaintresponse dto...
        return myComplaints.stream().map(this::mapToResponse).toList();

    }

    @Override
    public ComplaintResponse updateComplaintStatus(Long id, UpdateComplaintRequest request) {

        // Fetch complaint
        Complaint complaint = complaintRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        // Save old status before updating
        ComplaintStatus oldStatus = complaint.getStatus();

        // Update complaint status
        complaint.setStatus(request.getStatus());

        // Save updated complaint
        Complaint updatedComplaint = complaintRepo.save(complaint);

        // Create history entry
        ComplaintHistory history = ComplaintHistory.builder()
                .complaint(updatedComplaint)
                .oldStatus(oldStatus)
                .newStatus(updatedComplaint.getStatus())
                .changedBy("ADMIN") // We'll improve this later
                .note(request.getNote()) // Optional note
                .changedAt(LocalDateTime.now())
                .build();

        complaintHistoryRepository.save(history);

        // Return response
        return mapToResponse(updatedComplaint);
    }

    @Override
    public ComplaintResponse updateComplaintPriority(Long id, UpdatePriorityRequest request) {

        Complaint complaint = complaintRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found."));

        complaint.setPriority(request.getPriority());

        Complaint updatedComplaint = complaintRepo.save(complaint);

        return mapToResponse(updatedComplaint);
    }

    @Override
    public DashboardResponse getDashboard() {

        // sorting the category wise complaint in the map...
        Map<String, Long> categoryWise = new HashMap<>();
        for (ComplaintCategory category : ComplaintCategory.values()) {
            categoryWise.put(category.name(), complaintRepo.countByCategory(category));
        }

        return DashboardResponse.builder()
                .totalComplaints(complaintRepo.count())
                .pending(complaintRepo.countByStatus(ComplaintStatus.PENDING))
                .inProgress(complaintRepo.countByStatus(ComplaintStatus.IN_PROGRESS))
                .resolved(complaintRepo.countByStatus(ComplaintStatus.RESOLVED))
                .categoryWise(categoryWise)
                .build();

    }

    @Override
    public List<ComplaintHistoryResponse> getComplaintHistory(Long complaintId) {
        // Check complaint exists
        complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        return complaintHistoryRepository
                .findByComplaintIdOrderByChangedAtAsc(complaintId)
                .stream()
                .map(this::mapHistoryResponse)
                .toList();
    }

}
