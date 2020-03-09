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
		if(!"".equals(oper)&&oper!=null&&"checkName".equals(oper)) {//确定这个请求是检查用户名是否唯一的请求
			checkName(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"register".equals(oper)) {//确定这个请求是注册的请求
			try {
				register(request,response);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(!"".equals(oper)&&oper!=null&&"login".equals(oper)) {//确认这个请求是登陆请求
			userLogin(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"logout".equals(oper)) {//确认这个请求是注销请求
			logout(request,response);	
		}else if(!"".equals(oper)&&oper!=null&&"userCenter".equals(oper)) {//确认这个请求是跳转个人信息中心的请求
			userCenter(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"userInfo".equals(oper)) {//确认这个请求是个人信息修改页面展示
			userInfo(request,response);
		}else if(!"".equals(oper)&&oper!=null&&"userSave".equals(oper)) {//确认这个请求是个人信息修改保存方法
			userSave(request,response);
		}
	}
	
	//用户信息修改保存的方法
		private void userSave(HttpServletRequest req, HttpServletResponse response) {
			/**
			 * 1 更新用户信息
			 * 2 更新session中的user
			 * 3 页面跳转
			 */
			//获取session对象
			HttpSession session=req.getSession();
			//从session对象中获取当前登陆用户
			User u=(User) session.getAttribute("currentUser");
			//获取用户信息
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
			//构建对象
			User user = new User(userName, password, sex, birthday, dentityCode, email, mobile, address);
			user.setTrueName(trueName);
			user.setId(u.getId());
			//用户信息更新
			userService.update(user);
			//更新session中的用户
			session.setAttribute("currentUser", user);
			//修改用户信息的跳转页面
			userCenter(req,response);
		}

		//修改用户信息的跳转页面
		private void userInfo(HttpServletRequest request, HttpServletResponse response) {
			//获取session对象
			HttpSession session=request.getSession();
			//从session对象中获取当前登陆用户
			User user=(User) session.getAttribute("currentUser");
			//获取提示信息
			String navCode=NavUtil.genNavCode("修改当前用户");
			//前台动态加载页面
			String mainPage="userCenter/userSave.jsp";
			//往前台返回数据
			request.setAttribute("mainPage", mainPage);
			request.setAttribute("navCode", navCode);
			request.setAttribute("user", user);
			//请求转发
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

	//个人中心
		private void userCenter(HttpServletRequest request, HttpServletResponse response) {
			//写一个提示信息
			String navCode=NavUtil.genNavCode("个人中心");
		
			//页面的动态镶嵌
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
	//注册
	private void register(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	//获取请求参数
	String userName = req.getParameter("userName");
	String password = req.getParameter("password");
	String sex = req.getParameter("sex");
	Date birthday = DateUtil.formatString(req.getParameter("birthday"), "yyyy-MM-dd");
	String dentityCode = req.getParameter("dentityCode");
	String email = req.getParameter("email");
	String mobile = req.getParameter("mobile");
	String address = req.getParameter("address");
	//构建对象
	User user = new User(userName, password, sex, birthday, dentityCode, email, mobile, address);
	//添加
	userService.register(user);
	//跳转
	req.getRequestDispatcher("/reg-result.jsp").forward(req, resp);
	}
	
	private void checkName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uname = req.getParameter("userName");
		boolean flag=userService.checkName(uname);
		PrintWriter out = resp.getWriter();
		out.print(flag);
	}
	
	//登录
	private void userLogin(HttpServletRequest request, HttpServletResponse response) {
		//获取请求参数 准备登陆
		String uname = request.getParameter("userName");
		String pwd = request.getParameter("password");
		String imageCodeInput = request.getParameter("imageCode");//是我们输入的验证码
		//获取Session对象 获取存储的验证码
		HttpSession session=request.getSession();//获取session域对象
		String  imageCode = (String) session.getAttribute("sRand");//在session域对象中获取验证码信息
		User user = new User(uname,pwd);
		if(imageCodeInput.equals(imageCode)){
		//判定用户是否注册
			User currentUser= userService.login(user);
			if(currentUser!=null) {
				//存储作用域
				request.getSession().setAttribute("currentUser", currentUser);
				//跳转页面
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
				error = "用户名或者密码错误";
				}
		}else {
			//跳转页面
			error = "验证码错误";
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
	
	//注销
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//清空session
		req.getSession().invalidate();
		//跳转页面
		resp.sendRedirect(req.getContextPath()+"/index");
		}

}
