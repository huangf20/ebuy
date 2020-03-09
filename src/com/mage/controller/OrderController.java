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
		//获取请求的oper
		String oper = request.getParameter("oper");
		//分发请求
		if(!"".equals(oper)&&oper!=null&&"list".equals(oper)) {//订单列表
			list(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"status".equals(oper)) {//确认收货
			status(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"search".equals(oper)) {//模糊查询
			search(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"save".equals(oper)) {//购物车结算功能
			save(request,response);
		}
		
	}
	//购物车的结算功能
	private void save(HttpServletRequest req, HttpServletResponse response) {
		//获取session对象
		HttpSession session=req.getSession();
		//创建一个空的order
		Order order=new Order();
		//获取当前当前登陆用户
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
		//获取购物车中的所有商品集合
		List<ShoppingCartItem> shoppingCartItemList=shoppingCart.getShoppingCartItems();
		//订单的总价格
		float cost=0;
		//声明了一个订单关联表的集合
		List<OrderProduct> orderProductList=new ArrayList<OrderProduct>();
		//循环
		for(ShoppingCartItem shoppingCartItem:shoppingCartItemList){
			//获取每一个商品信息
			Product product=shoppingCartItem.getProduct();
			//创建订单关联表
			OrderProduct orderProduct=new OrderProduct();
			//封装对象
			orderProduct.setNum(shoppingCartItem.getCount());
			orderProduct.setOrder(order);
			orderProduct.setProduct(product);
			cost+=product.getPrice()*shoppingCartItem.getCount();//该商品的价格*该商品的数量
			//封装订单关联表
			orderProductList.add(orderProduct);
		}
		/**
		 * 1.已经得到了订单关联表的集合
		 * 2.只需要进行循环存储对象
		 */
		for(OrderProduct op:orderProductList){
			orderProductService.save(op);
		}
		//封装订单表
		order.setOrderProductList(orderProductList);
		order.setCost(cost);
		
		orderService.saveOrder(order);
		String navCode=NavUtil.genNavCode("购物");
		String mainPage="shopping/shopping-result.jsp";
		
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		session.removeAttribute("shoppingCart");
		//跳转页面
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
	//模糊查询
	private void search(HttpServletRequest request, HttpServletResponse response) {
		String orderNoString=request.getParameter("orderNo");
		HttpSession session=request.getSession();
		User currentUsre=(User)session.getAttribute("currentUser");
		int userId=currentUsre.getId();
		List<Order> orderList=null;
		if("".equals(orderNoString)){//判断前台是否输入订单号
			//没有输入，默认查询所有订单
			orderList=orderService.findAll(userId);
		}else{
			//输入了，模糊查询
			long orderNo=Long.parseLong(orderNoString);
			orderList=orderService.search(orderNo, userId);
		}
		String navCode=NavUtil.genNavCode("订单个人中心");
		String mainPage="userCenter/orderList.jsp";
		
		request.setAttribute("navCode", navCode);
		request.setAttribute("mainPage", mainPage);
		request.setAttribute("orderList", orderList);
		//跳转页面
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
	//确认收货的方法
	private void status(HttpServletRequest request, HttpServletResponse response) {
		//获取参数
		int orderId=Integer.parseInt(request.getParameter("orderId"));
		System.out.println(orderId);
		//确认收货
		orderService.status(orderId);	
	}

	//展示订单的列表的方法
	private void list(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session=req.getSession();
		User currentUsre=(User)session.getAttribute("currentUser");
		List<Order> orderList=orderService.findAll(currentUsre.getId());
		String navCode=NavUtil.genNavCode("个人中心");
		String mainPage="userCenter/orderList.jsp";
		
		req.setAttribute("navCode", navCode);
		req.setAttribute("mainPage", mainPage);
		req.setAttribute("orderList", orderList);
		//跳转页面
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
