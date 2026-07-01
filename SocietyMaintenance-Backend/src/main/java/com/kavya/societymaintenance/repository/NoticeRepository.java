package com.kavya.societymaintenance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kavya.societymaintenance.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByOrderByImportantDescCreatedAtDesc();

}