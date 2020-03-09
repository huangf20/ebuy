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
 * ��ҳ��չʾ
 * @author 95284
 *
 */
public class InitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//�����Ʒ������Ϣ
	private ProductBigTypeService bigTypeService=new ProductBigTypeServiceImpl();
	//����
	private NewsService  newsService =new NewsServiceImpl();
	//����
	private NoticeService  noticeService =new NoticeServiceImpl();
	//�ؼ���Ʒ
	private ProductService productService =new ProductServiceImpl();
	//���ű�ǩ
	private TagService tagService=new TagServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ѯ��������Ϣ
		List<ProductBigType> bigTypeList=bigTypeService.findAllBigType();
		//��ѯ���ţ�
		List<News> newsList= newsService.findAll();
		//��ѯ����
		List<Notice> noticeList=noticeService.findAll();
		//��ѯ�ؼ���Ʒ
		List<Product> specialPriceProductList=productService.findSpecialPrice();
		//��ѯ�ȶ���Ʒ
		List<Product> hotProductList=productService.findHotProductList();
		//��ѯ���ű�ǩ
		List<Tag>tagList=tagService.findAll();
		//����ѯ����ŵ����������
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
