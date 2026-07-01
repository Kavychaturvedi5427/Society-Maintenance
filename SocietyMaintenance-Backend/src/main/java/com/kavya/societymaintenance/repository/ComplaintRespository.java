package com.kavya.societymaintenance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kavya.societymaintenance.entity.Complaint;
import com.kavya.societymaintenance.entity.User;

import java.util.List;
import java.util.Optional;

import com.kavya.societymaintenance.enumerated.ComplaintCategory;
import com.kavya.societymaintenance.enumerated.ComplaintStatus;



public interface ComplaintRespository extends JpaRepository<Complaint, Long>{

    List<Complaint> findByResident(User resident);

    List<Complaint> findByStatus(ComplaintStatus status);

    Optional<Complaint> findById(Long id);

    long countByStatus(ComplaintStatus status);

    long countByCategory(ComplaintCategory category);
    
}
