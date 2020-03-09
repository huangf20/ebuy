package com.mage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mage.service.NewsService;
import com.mage.service.impl.NewsServiceImpl;
import com.mage.util.NavUtil;
import com.mage.vo.News;


@WebServlet("/newsServlet")
public class newsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
private NewsService newsService=new NewsServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int newsId=Integer.parseInt(request.getParameter("newsId"));
		News news=newsService.getNewsById(newsId);
		String navCode=NavUtil.genNavCode("新闻信息");
		String mainPage="news/newsDetails.jsp";
		request.setAttribute("news", news);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("navCode", navCode);
		request.getRequestDispatcher("/newsMain.jsp").forward(request, response);
	}

}
