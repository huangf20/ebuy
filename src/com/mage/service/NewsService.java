package com.mage.service;

import java.util.List;

import com.mage.vo.News;
import com.mage.vo.Notice;

public interface NewsService {
	List<News> findAll();
	News getNewsById(int newsId);
}
