package com.kavya.societymaintenance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kavya.societymaintenance.entity.ComplaintHistory;

public interface ComplaintHistoryRepository extends JpaRepository<ComplaintHistory, Long> {

    List<ComplaintHistory> findByComplaintIdOrderByChangedAtAsc(Long complaintId);

}
