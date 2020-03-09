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
		//����ַ�
		request.setCharacterEncoding("UTF-8");
		String oper=request.getParameter("oper");
		if(!"".equals(oper)&&oper!=null&&"smallType".equals(oper)) {//ȷ�������������ƷС��������������
			searchProductSmallType(request,response);
			}else if(!"".equals(oper)&&oper!=null&&"productType".equals(oper)) {//ȷ�������������Ʒ���������������
				searchProductBigType(request,response);
			}else if(!"".equals(oper)&&oper!=null&&"search".equals(oper)) {//ȷ�������������Ʒ����������
					s_product(request,response);
			}else if(!"".equals(oper)&&oper!=null&&"addCard".equals(oper)) {//ȷ�������������Ʒ����
						addProduct2Card(request,response);
						}
		}
		//��Ʒ����չʾ
		private void addProduct2Card(HttpServletRequest request, HttpServletResponse response) {
			int productId=Integer.parseInt(request.getParameter("productId"));
			Product product=productService.getProductById(productId);
			String navCode=NavUtil.genNavCode("��Ʒ����");
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
		//�����������Ʒ
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
	//top������ť
	private void s_product(HttpServletRequest request, HttpServletResponse response) {
		
		
		String  productName=request.getParameter("product");
		//��ȡ��ҳ����
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		//����һ��PageBean����
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		//��ѯ��������List����
		List productList=productService.findProductList(pageBean, productName);
		//��ѯ���ݵ�������
		int total=productService.getProductCount(productName);
		
		// ������ҳ����
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
				"/productServlet?oper=search&product="+productName, total, Integer.parseInt(page),8);
		String mainPage="product/productList.jsp";
		String navCode=NavUtil.genNavCode("����");
		
		
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

//������ѯ
	private void searchProductBigType(HttpServletRequest request, HttpServletResponse response) {
		
		int bigTypeId=Integer.parseInt(request.getParameter("id"));
		//��ȡ��ҳ����
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		//����һ��PageBean����
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		//��ѯ��������List����
		List productBigType=productService.findProductListBigType(pageBean, bigTypeId);
		//��ѯ���ݵ�������
		int total=productService.getProductCountBigType(bigTypeId);
		// ������ҳ����
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
				"/productServlet?oper=productType&id="+bigTypeId, total, Integer.parseInt(page),8);
		String mainPage="product/productList.jsp";
		String navCode=NavUtil.genNavCode("�����");
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
		//��ȡ��ѯ��idֵ
		int smallType = Integer.parseInt(request.getParameter("id"));
		//��ȡ��ҳ����
		String page = request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		List<Product> productList=productService.findProductListSmallType(pageBean, smallType);//��ѯ��Ʒ����
		
		int total=productService.getProductCountSmallType(smallType);//��ѯ��Ʒ��������
		// ������ҳ����
		String pageCode=PageUtil.genPaginationNoParam(request.getContextPath()+
				"/productServlet?oper=smallType&id="+smallType, total, Integer.parseInt(page),8);
		
		String mainPage="product/productList.jsp";
		String navCode=NavUtil.genNavCode("С����");
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
