package com.mage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.OrderProductService;
import com.mage.service.OrderService;
import com.mage.service.impl.OrderProductServiceImpl;
import com.mage.service.impl.OrderServiceImpl;
import com.mage.util.DateUtil;
import com.mage.util.NavUtil;
import com.mage.vo.Order;
import com.mage.vo.OrderProduct;
import com.mage.vo.Product;
import com.mage.vo.ShoppingCart;
import com.mage.vo.ShoppingCartItem;
import com.mage.vo.User;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/orderServlet")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService=new OrderServiceImpl();
	
	private OrderProductService orderProductService=new OrderProductServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ�����oper
		String oper = request.getParameter("oper");
		//�ַ�����
		if(!"".equals(oper)&&oper!=null&&"list".equals(oper)) {//�����б�
			list(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"status".equals(oper)) {//ȷ���ջ�
			status(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"search".equals(oper)) {//ģ����ѯ
			search(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"save".equals(oper)) {//���ﳵ���㹦��
			save(request,response);
		}
		
	}
	//���ﳵ�Ľ��㹦��
	private void save(HttpServletRequest req, HttpServletResponse response) {
		//��ȡsession����
		HttpSession session=req.getSession();
		//����һ���յ�order
		Order order=new Order();
		//��ȡ��ǰ��ǰ��½�û�
		User currentUsre=(User)session.getAttribute("currentUser");
		order.setUser(currentUsre);
		order.setCreateTime(new Date());
		try {
			order.setOrderNo(DateUtil.getCurrentDateStr());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order.setStatus(1);
		
		ShoppingCart shoppingCart=(ShoppingCart) session.getAttribute("shoppingCart");
		//��ȡ���ﳵ�е�������Ʒ����
		List<ShoppingCartItem> shoppingCartItemList=shoppingCart.getShoppingCartItems();
		//�������ܼ۸�
		float cost=0;
		//������һ������������ļ���
		List<OrderProduct> orderProductList=new ArrayList<OrderProduct>();
		//ѭ��
		for(ShoppingCartItem shoppingCartItem:shoppingCartItemList){
			//��ȡÿһ����Ʒ��Ϣ
			Product product=shoppingCartItem.getProduct();
			//��������������
			OrderProduct orderProduct=new OrderProduct();
			//��װ����
			orderProduct.setNum(shoppingCartItem.getCount());
			orderProduct.setOrder(order);
			orderProduct.setProduct(product);
			cost+=product.getPrice()*shoppingCartItem.getCount();//����Ʒ�ļ۸�*����Ʒ������
			//��װ����������
			orderProductList.add(orderProduct);
		}
		/**
		 * 1.�Ѿ��õ��˶���������ļ���
		 * 2.ֻ��Ҫ����ѭ���洢����
		 */
		for(OrderProduct op:orderProductList){
			orderProductService.save(op);
		}
		//��װ������
		order.setOrderProductList(orderProductList);
		order.setCost(cost);
		
		orderService.saveOrder(order);
		String navCode=NavUtil.genNavCode("����");
		String mainPage="shopping/shopping-result.jsp";
		
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		session.removeAttribute("shoppingCart");
		//��תҳ��
		try {
			req.getRequestDispatcher("/shoppingMain.jsp").forward(req, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//ģ����ѯ
	private void search(HttpServletRequest request, HttpServletResponse response) {
		String orderNoString=request.getParameter("orderNo");
		HttpSession session=request.getSession();
		User currentUsre=(User)session.getAttribute("currentUser");
		int userId=currentUsre.getId();
		List<Order> orderList=null;
		if("".equals(orderNoString)){//�ж�ǰ̨�Ƿ����붩����
			//û�����룬Ĭ�ϲ�ѯ���ж���
			orderList=orderService.findAll(userId);
		}else{
			//�����ˣ�ģ����ѯ
			long orderNo=Long.parseLong(orderNoString);
			orderList=orderService.search(orderNo, userId);
		}
		String navCode=NavUtil.genNavCode("������������");
		String mainPage="userCenter/orderList.jsp";
		
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("orderList", orderList);
		//��תҳ��
		try {
			request.getRequestDispatcher("/userCenter.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//ȷ���ջ��ķ���
	private void status(HttpServletRequest request, HttpServletResponse response) {
		//��ȡ����
		int orderId=Integer.parseInt(request.getParameter("orderId"));
		System.out.println(orderId);
		//ȷ���ջ�
		orderService.status(orderId);	
	}

	//չʾ�������б�ķ���
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session=req.getSession();
		User currentUsre=(User)session.getAttribute("currentUser");
		List<Order> orderList=orderService.findAll(currentUsre.getId());
		String navCode=NavUtil.genNavCode("��������");
		String mainPage="userCenter/orderList.jsp";
		
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		req.setAttribute("orderList", orderList);
		//��תҳ��
		try {
			req.getRequestDispatcher("/userCenter.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
