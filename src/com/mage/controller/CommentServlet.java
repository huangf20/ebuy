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
		if(!"".equals(oper)&&oper!=null&&"list".equals(oper)) {//确定这个请求是留言展示
			list(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"save".equals(oper)) {//确定这个请求是留言保存
			save(request,response);
		}
	}
	//保存留言的方法
	private void save(HttpServletRequest request, HttpServletResponse response) {
				//获取数据
				String nickName = request.getParameter("nickName1");
				
				String content = request.getParameter("content");
				Comment comment = new Comment();
				comment.setNickName(nickName);
				comment.setContent(content);
				comment.setCreateTime(new Date());
				//数据存储
				commentService.saveComment(comment);
				//接下来我们要刷新页面
				list(request,response);
		
	}
	//留言展示的方法
	private void list(HttpServletRequest request, HttpServletResponse response) {
		//获取当前页码
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){//如果没有进行过查询，当前页面中并没有page，所有我们默认展示第一页
			page="1";
		}
		//创建PageBean对象
		PageBean pagebean=new PageBean(Integer.parseInt(page),4);
		//查询list集合
		List<Comment> commentList=commentService.findAllCommentList(pagebean);
		//查询数据的总数量
		int total=commentService.commentListCount();
		//生产分页代码
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
