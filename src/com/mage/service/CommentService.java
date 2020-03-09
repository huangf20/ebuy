package com.mage.service;

import java.util.List;

import com.mage.vo.Comment;
import com.mage.vo.PageBean;

public interface CommentService {
	//查询留言信息
	List<Comment>findAllCommentList(PageBean pageBean);
	//查询留言信息总数量
	int commentListCount();
	void saveComment(Comment comment);
}
