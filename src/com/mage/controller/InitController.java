package com.mage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mage.service.NewsService;
import com.mage.service.NoticeService;
import com.mage.service.ProductBigTypeService;
import com.mage.service.ProductService;
import com.mage.service.TagService;
import com.mage.service.impl.NewsServiceImpl;
import com.mage.service.impl.NoticeServiceImpl;
import com.mage.service.impl.ProductBigTypeServiceImpl;
import com.mage.service.impl.ProductServiceImpl;
import com.mage.service.impl.TagServiceImpl;
import com.mage.vo.News;
import com.mage.vo.Notice;
import com.mage.vo.Product;
import com.mage.vo.ProductBigType;
import com.mage.vo.Tag;
/**
 * 首页的展示
 * @author 95284
 *
 */
public class InitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//左侧商品分类信息
	private ProductBigTypeService bigTypeService=new ProductBigTypeServiceImpl();
	//新闻
	private NewsService  newsService =new NewsServiceImpl();
	//公告
	private NoticeService  noticeService =new NoticeServiceImpl();
	//特价商品
	private ProductService productService =new ProductServiceImpl();
	//热门标签
	private TagService tagService=new TagServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//查询左侧分类信息
		List<ProductBigType> bigTypeList=bigTypeService.findAllBigType();
		//查询新闻，
		List<News> newsList= newsService.findAll();
		//查询公告
		List<Notice> noticeList=noticeService.findAll();
		//查询特价商品
		List<Product> specialPriceProductList=productService.findSpecialPrice();
		//查询热度商品
		List<Product> hotProductList=productService.findHotProductList();
		//查询热门标签
		List<Tag>tagList=tagService.findAll();
		//将查询结果放到域对象里面
		ServletContext sc=request.getServletContext();
		/*request.setAttribute("bigTypeList", bigTypeList);
		request.setAttribute("newsList", newsList);
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("specialPriceProductList", specialPriceProductList);
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("tagList",tagList);*/
		sc.setAttribute("bigTypeList", bigTypeList);
		sc.setAttribute("newsList", newsList);
		sc.setAttribute("noticeList", noticeList);
		sc.setAttribute("specialPriceProductList", specialPriceProductList);
		sc.setAttribute("hotProductList", hotProductList);
		sc.setAttribute("tagList", tagList);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
