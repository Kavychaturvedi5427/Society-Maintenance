package com.kavya.societymaintenance.service;

import java.util.List;

import com.kavya.societymaintenance.dto.Request.NoticeRequest;
import com.kavya.societymaintenance.dto.Response.NoticeResponse;

public interface NoticeService {

    NoticeResponse createNotice(NoticeRequest request);

    List<NoticeResponse> getAllNotices();

    NoticeResponse updateNotice(Long id, NoticeRequest request);

    void deleteNotice(Long id);
}