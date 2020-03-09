package com.mage.service;

import java.util.List;

import com.mage.vo.Comment;
import com.mage.vo.PageBean;

public interface CommentService {
	//��ѯ������Ϣ
	List<Comment>findAllCommentList(PageBean pageBean);
	//��ѯ������Ϣ������
	int commentListCount();
	void saveComment(Comment comment);
}
