package com.mage.service;

import java.util.List;

import com.mage.vo.Notice;

public interface NoticeService {
	List<Notice> findAll();
	Notice getNoticeById(int noticeId);
}
