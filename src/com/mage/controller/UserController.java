package com.mage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mage.service.UserService;
import com.mage.service.impl.UserServiceImpl;
import com.mage.util.DateUtil;
import com.mage.util.NavUtil;
import com.mage.vo.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/userServlet")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
	private String error;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oper=request.getParameter("oper");
		if(!"".equals(oper)&&oper!=null&&"checkName".equals(oper)) {//ȷ����������Ǽ���û����Ƿ�Ψһ������
			checkName(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"register".equals(oper)) {//ȷ�����������ע�������
			try {
				register(request,response);
			} catch (Exception e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}else if(!"".equals(oper)&&oper!=null&&"login".equals(oper)) {//ȷ����������ǵ�½����
			userLogin(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"logout".equals(oper)) {//ȷ�����������ע������
			logout(request,response);	
		}else if(!"".equals(oper)&&oper!=null&&"userCenter".equals(oper)) {//ȷ�������������ת������Ϣ���ĵ�����
			userCenter(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"userInfo".equals(oper)) {//ȷ����������Ǹ�����Ϣ�޸�ҳ��չʾ
			userInfo(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"userSave".equals(oper)) {//ȷ����������Ǹ�����Ϣ�޸ı��淽��
			userSave(request,response);
		}
	}
	
	//�û���Ϣ�޸ı���ķ���
		private void userSave(HttpServletRequest req, HttpServletResponse response) {
			/**
			 * 1 �����û���Ϣ
			 * 2 ����session�е�user
			 * 3 ҳ����ת
			 */
			//��ȡsession����
			HttpSession session=req.getSession();
			//��session�����л�ȡ��ǰ��½�û�
			User u=(User) session.getAttribute("currentUser");
			//��ȡ�û���Ϣ
			String userName = req.getParameter("userName");
			String trueName = req.getParameter("trueName");
			String password = req.getParameter("password");
			String sex = req.getParameter("sex");
			Date birthday=null;
			try {
				birthday = DateUtil.formatString(req.getParameter("birthday"), "yyyy-MM-dd");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String dentityCode = req.getParameter("dentityCode");
			String email = req.getParameter("email");
			String mobile = req.getParameter("mobile");
			String address = req.getParameter("address");
			//��������
			User user = new User(userName, password, sex, birthday, dentityCode, email, mobile, address);
			user.setTrueName(trueName);
			user.setId(u.getId());
			//�û���Ϣ����
			userService.update(user);
			//����session�е��û�
			session.setAttribute("currentUser", user);
			//�޸��û���Ϣ����תҳ��
			userCenter(req,response);
		}

		//�޸��û���Ϣ����תҳ��
		private void userInfo(HttpServletRequest request, HttpServletResponse response) {
			//��ȡsession����
			HttpSession session=request.getSession();
			//��session�����л�ȡ��ǰ��½�û�
			User user=(User) session.getAttribute("currentUser");
			//��ȡ��ʾ��Ϣ
			String navCode=NavUtil.genNavCode("�޸ĵ�ǰ�û�");
			//ǰ̨��̬����ҳ��
			String mainPage="userCenter/userSave.jsp";
			//��ǰ̨��������
			request.setAttribute("mainPage", mainPage);
			request.setAttribute("navCode", navCode);
			request.setAttribute("user", user);
			//����ת��
			try {
				request.getRequestDispatcher("userCenter.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	//��������
		private void userCenter(HttpServletRequest request, HttpServletResponse response) {
			//дһ����ʾ��Ϣ
			String navCode=NavUtil.genNavCode("��������");
		
			//ҳ��Ķ�̬��Ƕ
			String mainPage="userCenter/userInfo.jsp";
			request.setAttribute("navCode", navCode);
			request.setAttribute("mainPage", mainPage);
			try {
				request.getRequestDispatcher("userCenter.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	//ע��
	private void register(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	//��ȡ�������
	String userName = req.getParameter("userName");
	String password = req.getParameter("password");
	String sex = req.getParameter("sex");
	Date birthday = DateUtil.formatString(req.getParameter("birthday"), "yyyy-MM-dd");
	String dentityCode = req.getParameter("dentityCode");
	String email = req.getParameter("email");
	String mobile = req.getParameter("mobile");
	String address = req.getParameter("address");
	//��������
	User user = new User(userName, password, sex, birthday, dentityCode, email, mobile, address);
	//���
	userService.register(user);
	//��ת
	req.getRequestDispatcher("/reg-result.jsp").forward(req, resp);
	}
	
	private void checkName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uname = req.getParameter("userName");
		boolean flag=userService.checkName(uname);
		PrintWriter out = resp.getWriter();
		out.print(flag);
	}
	
	//��¼
	private void userLogin(HttpServletRequest request, HttpServletResponse response) {
		//��ȡ������� ׼����½
		String uname = request.getParameter("userName");
		String pwd = request.getParameter("password");
		String imageCodeInput = request.getParameter("imageCode");//�������������֤��
		//��ȡSession���� ��ȡ�洢����֤��
		HttpSession session=request.getSession();//��ȡsession�����
		String  imageCode = (String) session.getAttribute("sRand");//��session������л�ȡ��֤����Ϣ
		User user = new User(uname,pwd);
		if(imageCodeInput.equals(imageCode)){
		//�ж��û��Ƿ�ע��
			User currentUser= userService.login(user);
			if(currentUser!=null) {
				//�洢������
				request.getSession().setAttribute("currentUser", currentUser);
				//��תҳ��
				try {
					request.getRequestDispatcher("/index").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}else {
				error = "�û��������������";
				}
		}else {
			//��תҳ��
			error = "��֤�����";
		}	
		request.setAttribute("error",error);
		try {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ע��
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//���session
		req.getSession().invalidate();
		//��תҳ��
		resp.sendRedirect(req.getContextPath()+"/index");
		}

}
