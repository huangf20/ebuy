package com.mage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mage.service.NoticeService;
import com.mage.service.impl.NoticeServiceImpl;
import com.mage.util.NavUtil;
import com.mage.vo.Notice;

/**
 * Servlet implementation class NoticController
 */
@WebServlet("/noticServlet")
public class NoticController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NoticeService noticeService=new NoticeServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int noticeId=Integer.parseInt(request.getParameter("noticeId"));
		Notice notice=noticeService.getNoticeById(noticeId);
		String navCode=NavUtil.genNavCode("¹«¸æÏêÇé");
		String mainPage="notice/noticeDetails.jsp";
		
		request.setAttribute("notice", notice);
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.getRequestDispatcher("/noticeMain.jsp").forward(request, response);
	}

}
