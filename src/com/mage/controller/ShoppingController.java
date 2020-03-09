package com.mage.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.mage.vo.Product;
import com.mage.vo.ShoppingCart;
import com.mage.vo.ShoppingCartItem;
import com.mage.vo.User;

/**
 * Servlet implementation class ShoppingController
 */
@WebServlet("/shopping")
public class ShoppingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductService productService=new ProductServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//请求分发
				String oper=request.getParameter("oper");
				if(!"".equals(oper)&&oper!=null&&"addShopping".equals(oper)) {//确定这个请求是用户添加购物车的一个请求
					try {
						addShopping(request,response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(!"".equals(oper)&&oper!=null&&"list".equals(oper)) {//确定这个请求是展示购物车
					list(request,response);
				}else if(!"".equals(oper)&&oper!=null&&"remove".equals(oper)) {//确定这个请求是展示购物车
					remove(request,response);
				}
	}

	//购物车中删除商品的方法
		private void remove(HttpServletRequest request, HttpServletResponse response) {
			
			//获取session域对象
			HttpSession session=request.getSession();
			//获取购物车域对象的信息
			ShoppingCart shoppingCart=(ShoppingCart) session.getAttribute("shoppingCart");
			
			int productId = Integer.parseInt(request.getParameter("productId"));
			int i=0;
			boolean IsFind=false;

			
			for(ShoppingCartItem sc:shoppingCart.getShoppingCartItems())
			{
				i++;
				if(sc.getProduct().getId()==productId)
				{
					IsFind=true;
					break;
				}
			}
			if(IsFind)
			shoppingCart.Remove(i-1);
			session.setAttribute("shoppingCart", shoppingCart);
			list(request,response);

			
		}

		//展示购物车
		private void list(HttpServletRequest request, HttpServletResponse response) {
					// 获取提示信息
					String navCode=NavUtil.genNavCode("购物车");
					// 存储信息
					request.setAttribute("navCode", navCode);
					//跳转包含页面
					String mainPage="shopping/shopping.jsp";
					//存储信息
					request.setAttribute("mainPage", mainPage);
					//跳转页面
					try {
						request.getRequestDispatcher("/shoppingMain.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		}

	private void addShopping(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取商品的id
		int productId = Integer.parseInt(request.getParameter("productId"));
		//获取session域对象
		HttpSession session=request.getSession();
		//根据id查询商品的信息
		Product product=productService.getProductById(productId);
		//获取购物车域对象的信息
		ShoppingCart shoppingCart=(ShoppingCart) session.getAttribute("shoppingCart");
	
		if(shoppingCart==null){//购物车中还没有任何商品
			shoppingCart=new ShoppingCart();//需要创建一个购物车
			//对购物车进行封装（存储信息）
			User currentUser=(User) session.getAttribute("currentUser");
			//存储userId
			shoppingCart.setUserId(currentUser.getId());
		}
		//购物车中商品的集合
		List<ShoppingCartItem> shoppingCartItemList=shoppingCart.getShoppingCartItems();
		
		boolean flag=true;//我们要添加的商品信息在购物车中并没有
		for(ShoppingCartItem scI:shoppingCartItemList){
			if(scI.getProduct().getId()==productId){
				scI.setCount(scI.getCount()+1);//购物车中已经存在了我们要添加购物车的商品，那么直接对数量进行+1
				flag=false;
				break;
			}
		}
		
		ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
		
		if(flag){//true 购物车中没有我们要添加的商品，我们就要创建一条记录
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}
		session.setAttribute("shoppingCart", shoppingCart);
		PrintWriter out = response.getWriter();
		out.print("true");
	}
		
}


