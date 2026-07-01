package com.kavya.societymaintenance.service.Impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kavya.societymaintenance.dto.Request.NoticeRequest;
import com.kavya.societymaintenance.dto.Response.NoticeResponse;
import com.kavya.societymaintenance.entity.Notice;
import com.kavya.societymaintenance.repository.NoticeRepository;
import com.kavya.societymaintenance.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticeResponse createNotice(NoticeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .important(request.isImportant())
                .postedBy(authentication.getName())
                .build();

        Notice savedNotice = noticeRepository.save(notice);

        return mapToResponse(savedNotice);
    }

    @Override
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository
                .findAllByOrderByImportantDescCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NoticeResponse updateNotice(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setImportant(request.isImportant());

        Notice updatedNotice = noticeRepository.save(notice);

        return mapToResponse(updatedNotice);
    }

    @Override
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        noticeRepository.delete(notice);

    }

    private NoticeResponse mapToResponse(Notice notice) {

        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .important(notice.isImportant())
                .postedBy(notice.getPostedBy())
                .createdAt(notice.getCreatedAt())
                .build();
    }

}