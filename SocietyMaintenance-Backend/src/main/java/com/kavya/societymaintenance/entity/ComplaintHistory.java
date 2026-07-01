package com.kavya.societymaintenance.entity;

import java.time.LocalDateTime;

import com.kavya.societymaintenance.enumerated.ComplaintStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "complaint_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus newStatus;

    @Column(nullable = false)
    private String changedBy;

    private String note;

    @Column(nullable = false)
    private LocalDateTime changedAt;
}