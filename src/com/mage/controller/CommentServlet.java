package com.mage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mage.service.CommentService;
import com.mage.service.impl.CommentServiceImpl;
import com.mage.util.PageUtil;
import com.mage.util.StringUtil;
import com.mage.vo.Comment;
import com.mage.vo.PageBean;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommentService commentService=new CommentServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String oper=request.getParameter("oper");
		if(!"".equals(oper)&&oper!=null&&"list".equals(oper)) {//ȷ���������������չʾ
			list(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"save".equals(oper)) {//ȷ��������������Ա���
			save(request,response);
		}
	}
	//�������Եķ���
	private void save(HttpServletRequest request, HttpServletResponse response) {
				//��ȡ����
				String nickName = request.getParameter("nickName1");
				
				String content = request.getParameter("content");
				Comment comment = new Comment();
				comment.setNickName(nickName);
				comment.setContent(content);
				comment.setCreateTime(new Date());
				//���ݴ洢
				commentService.saveComment(comment);
				//����������Ҫˢ��ҳ��
				list(request,response);
		
	}
	//����չʾ�ķ���
	private void list(HttpServletRequest request, HttpServletResponse response) {
		//��ȡ��ǰҳ��
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){//���û�н��й���ѯ����ǰҳ���в�û��page����������Ĭ��չʾ��һҳ
			page="1";
		}
		//����PageBean����
		PageBean pagebean=new PageBean(Integer.parseInt(page),4);
		//��ѯlist����
		List<Comment> commentList=commentService.findAllCommentList(pagebean);
		//��ѯ���ݵ�������
		int total=commentService.commentListCount();
		//������ҳ����
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
						"/comment?oper=list", total, Integer.parseInt(page),4);
		request.setAttribute("commentList", commentList);
		request.setAttribute("pageCode", pageCode);
		try {
			request.getRequestDispatcher("comment.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
