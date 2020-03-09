package com.mage.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.ProductService;
import com.mage.service.impl.ProductServiceImpl;
import com.mage.util.NavUtil;
import com.mage.util.PageUtil;
import com.mage.util.StringUtil;
import com.mage.vo.PageBean;
import com.mage.vo.Product;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/productServlet")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService=new ProductServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//请求分发
		request.setCharacterEncoding("UTF-8");
		String oper=request.getParameter("oper");
		if(!"".equals(oper)&&oper!=null&&"smallType".equals(oper)) {//确定这个请求是商品小分类搜索的请求
			searchProductSmallType(request,response);
			}else if(!"".equals(oper)&&oper!=null&&"productType".equals(oper)) {//确定这个请求是商品大分类搜索的请求
				searchProductBigType(request,response);
			}else if(!"".equals(oper)&&oper!=null&&"search".equals(oper)) {//确定这个请求是商品搜索的请求
					s_product(request,response);
			}else if(!"".equals(oper)&&oper!=null&&"addCard".equals(oper)) {//确定这个请求是商品详情
						addProduct2Card(request,response);
						}
		}
		//商品详情展示
		private void addProduct2Card(HttpServletRequest request, HttpServletResponse response) {
			int productId=Integer.parseInt(request.getParameter("productId"));
			Product product=productService.getProductById(productId);
			String navCode=NavUtil.genNavCode("商品详情");
			String mainPage="product/productDetails.jsp";
			saveCurrentBrowse(product,request);
			request.setAttribute("navCode", navCode);
			request.setAttribute("mainPage", mainPage);
			request.setAttribute("product", product);
			try {
				request.getRequestDispatcher("/productMain.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//添加最近浏览商品
				private void saveCurrentBrowse(Product product,HttpServletRequest req){
					HttpSession session=req.getSession();
					List<Product> currentBrowseProduct=(List<Product>) session.getAttribute("currentBrowse");
					if(currentBrowseProduct==null){
						currentBrowseProduct=new LinkedList<Product>();
					}
					boolean flag=true;
					for(Product p:currentBrowseProduct){
						if(p.getId()==product.getId()){
							flag=false;
							break;
						}
					}
					if(flag){
						currentBrowseProduct.add(0,product);
					}
					if(currentBrowseProduct.size()==5){
						currentBrowseProduct.remove(4);
					}
					session.setAttribute("currentBrowse", currentBrowseProduct);
				}
	//top搜索按钮
	private void s_product(HttpServletRequest request, HttpServletResponse response) {
		
		
		String  productName=request.getParameter("product");
		//获取分页内容
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		//创建一个PageBean对象
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		//查询返回数据List集合
		List productList=productService.findProductList(pageBean, productName);
		//查询数据的总数量
		int total=productService.getProductCount(productName);
		
		// 生产分页代码
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
				"/productServlet?oper=search&product="+productName, total, Integer.parseInt(page),8);
		String mainPage="product/productList.jsp";
		String navCode=NavUtil.genNavCode("搜索");
		
		
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("productList", productList);
		request.setAttribute("pageCode", pageCode);
		try {
			request.getRequestDispatcher("/productMain.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

//大分类查询
	private void searchProductBigType(HttpServletRequest request, HttpServletResponse response) {
		
		int bigTypeId=Integer.parseInt(request.getParameter("id"));
		//获取分页内容
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		//创建一个PageBean对象
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		//查询返回数据List集合
		List productBigType=productService.findProductListBigType(pageBean, bigTypeId);
		//查询数据的总数量
		int total=productService.getProductCountBigType(bigTypeId);
		// 生产分页代码
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
				"/productServlet?oper=productType&id="+bigTypeId, total, Integer.parseInt(page),8);
		String mainPage="product/productList.jsp";
		String navCode=NavUtil.genNavCode("大分类");
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("productList", productBigType);
		request.setAttribute("pageCode", pageCode);
		try {
			request.getRequestDispatcher("/productMain.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void searchProductSmallType(HttpServletRequest request, HttpServletResponse response) {
		//获取查询的id值
		int smallType = Integer.parseInt(request.getParameter("id"));
		//获取分页内容
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		List<Product> productList=productService.findProductListSmallType(pageBean, smallType);//查询商品集合
		
		int total=productService.getProductCountSmallType(smallType);//查询商品的总数量
		// 生产分页代码
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
				"/productServlet?oper=smallType&id="+smallType, total, Integer.parseInt(page),8);
		
		String mainPage="product/productList.jsp";
		String navCode=NavUtil.genNavCode("小分类");
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("productList", productList);
		request.setAttribute("pageCode", pageCode);
		try {
			request.getRequestDispatcher("/productMain.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
