package com.mage.service.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mage.service.UserService;
import com.mage.util.DBUtil;
import com.mage.vo.User;
import com.mysql.jdbc.Connection;

public class UserServiceImpl implements UserService{
	

	public void register(User user) {
	//声明连接
	Connection conn = null;
	try{
	//获取连接通道
	conn = (Connection) DBUtil.getConn();
	String sql = "INSERT INTO T_USER (ADDRESS,BIRTHDAY,DENTITYCODE,"
	+ "EMAIL,MOBILE,PASSWORD,SEX,STATUS,USERNAME) VALUES ("
	+ "?,?,?,?,?,?,?,?,?)";
	//设置绑定变量
	Object[] pars=new Object[]{
	user.getAddress(),user.getBirthday(),
	user.getDentityCode(),user.getEmail(),
	user.getMobile(),user.getPassword(),
	user.getSex(),user.getStatus(),
	user.getUserName()
	};
	QueryRunner qr=new QueryRunner();
	qr.update(conn, sql, pars);
	}catch(Exception e){
	System.out.println("register ERROR");
	e.printStackTrace();
	}finally{
	DBUtil.closeConn(conn);
	}
	}

	@Override
	public boolean checkName(String uName) {
		boolean flag=false;
		//声明连接
		Connection conn=null;
		conn=(Connection) DBUtil.getConn();
		try {
			String sql="SELECT * FROM T_USER WHERE USERNAME= ?";
			Object[] obj=new Object[]{uName};
			QueryRunner qr=new QueryRunner();
			
			User u=(User) qr.query(conn,sql, new BeanHandler(User.class), obj);
			if(null==u){
				flag = true;//true 代表没有查询到这条userName的数据，userName可以进行使用
			}else{
				flag = false;//false 代表查询到了，userName不能进行使用
			}
		} catch (SQLException e) {
			System.out.println("checkUserName ERROR");
			e.printStackTrace();
		}
		return flag;
		}
	
	
	//用户登陆的方法
		@Override
		public User login(User user) {
			//声明一个返回值
			User u = null;
			//声明连接
			Connection conn = null;
			try {
				//获取
				conn = (Connection) DBUtil.getConn();
				String sql = "SELECT * FROM T_USER WHERE USERNAME = ? AND PASSWORD = ?";
				
				//设置绑定变量
				Object[] pars = new Object[] {user.getUserName(),user.getPassword()};
				
				QueryRunner qr = new QueryRunner();
				
				u = (User) qr.query(conn, sql, new BeanHandler(User.class), pars);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				DBUtil.closeConn(conn);
			}
			return u;
		}

		//用户信息修改保存方法
		//更新操作qr.update(a,b,c);
		@Override
		public void update(User user) {
			//声明连接
			Connection conn = null;
			try {
				//获取
				conn = (Connection) DBUtil.getConn();
				String sql = "UPDATE  T_USER  SET ADDRESS = ? ,BIRTHDAY = ?,DENTITYCODE = ? ,"
						+ "EMAIL = ?,MOBILE = ?,PASSWORD = ?,SEX = ?,STATUS = ?,USERNAME = ?,TRUENAME=? WHERE ID =?";
				//设置绑定变量
				Object[] pars = new Object[] {
						user.getAddress(),user.getBirthday(),
						user.getDentityCode(),user.getEmail(),
						user.getMobile(),user.getPassword(),
						user.getSex(),user.getStatus(),
						user.getUserName(),user.getTrueName(),user.getId()
						};
				
				QueryRunner qr = new QueryRunner();
				
				qr.update(conn, sql,  pars);
				
			}catch(SQLException e) {
				System.out.println("UPDATEUSER ERROR");
				e.printStackTrace();
			}finally {
				DBUtil.closeConn(conn);
			}
		}

	
	
	
}
