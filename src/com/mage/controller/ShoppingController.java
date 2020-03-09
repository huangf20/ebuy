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
		//����ַ�
				String oper=request.getParameter("oper");
				if(!"".equals(oper)&&oper!=null&&"addShopping".equals(oper)) {//ȷ������������û���ӹ��ﳵ��һ������
					try {
						addShopping(request,response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(!"".equals(oper)&&oper!=null&&"list".equals(oper)) {//ȷ�����������չʾ���ﳵ
					list(request,response);
				}else if(!"".equals(oper)&&oper!=null&&"remove".equals(oper)) {//ȷ�����������չʾ���ﳵ
					remove(request,response);
				}
	}

	//���ﳵ��ɾ����Ʒ�ķ���
		private void remove(HttpServletRequest request, HttpServletResponse response) {
			
			//��ȡsession�����
			HttpSession session=request.getSession();
			//��ȡ���ﳵ��������Ϣ
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

		//չʾ���ﳵ
		private void list(HttpServletRequest request, HttpServletResponse response) {
					// ��ȡ��ʾ��Ϣ
					String navCode=NavUtil.genNavCode("���ﳵ");
					// �洢��Ϣ
					request.setAttribute("navCode", navCode);
					//��ת����ҳ��
					String mainPage="shopping/shopping.jsp";
					//�洢��Ϣ
					request.setAttribute("mainPage", mainPage);
					//��תҳ��
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
		//��ȡ��Ʒ��id
		int productId = Integer.parseInt(request.getParameter("productId"));
		//��ȡsession�����
		HttpSession session=request.getSession();
		//����id��ѯ��Ʒ����Ϣ
		Product product=productService.getProductById(productId);
		//��ȡ���ﳵ��������Ϣ
		ShoppingCart shoppingCart=(ShoppingCart) session.getAttribute("shoppingCart");
	
		if(shoppingCart==null){//���ﳵ�л�û���κ���Ʒ
			shoppingCart=new ShoppingCart();//��Ҫ����һ�����ﳵ
			//�Թ��ﳵ���з�װ���洢��Ϣ��
			User currentUser=(User) session.getAttribute("currentUser");
			//�洢userId
			shoppingCart.setUserId(currentUser.getId());
		}
		//���ﳵ����Ʒ�ļ���
		List<ShoppingCartItem> shoppingCartItemList=shoppingCart.getShoppingCartItems();
		
		boolean flag=true;//����Ҫ��ӵ���Ʒ��Ϣ�ڹ��ﳵ�в�û��
		for(ShoppingCartItem scI:shoppingCartItemList){
			if(scI.getProduct().getId()==productId){
				scI.setCount(scI.getCount()+1);//���ﳵ���Ѿ�����������Ҫ��ӹ��ﳵ����Ʒ����ôֱ�Ӷ���������+1
				flag=false;
				break;
			}
		}
		
		ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
		
		if(flag){//true ���ﳵ��û������Ҫ��ӵ���Ʒ�����Ǿ�Ҫ����һ����¼
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}
		session.setAttribute("shoppingCart", shoppingCart);
		PrintWriter out = response.getWriter();
		out.print("true");
	}
		
}


