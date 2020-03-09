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
	//��������
	Connection conn = null;
	try{
	//��ȡ����ͨ��
	conn = (Connection) DBUtil.getConn();
	String sql = "INSERT INTO T_USER (ADDRESS,BIRTHDAY,DENTITYCODE,"
	+ "EMAIL,MOBILE,PASSWORD,SEX,STATUS,USERNAME) VALUES ("
	+ "?,?,?,?,?,?,?,?,?)";
	//���ð󶨱���
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
		//��������
		Connection conn=null;
		conn=(Connection) DBUtil.getConn();
		try {
			String sql="SELECT * FROM T_USER WHERE USERNAME= ?";
			Object[] obj=new Object[]{uName};
			QueryRunner qr=new QueryRunner();
			
			User u=(User) qr.query(conn,sql, new BeanHandler(User.class), obj);
			if(null==u){
				flag = true;//true ����û�в�ѯ������userName�����ݣ�userName���Խ���ʹ��
			}else{
				flag = false;//false �����ѯ���ˣ�userName���ܽ���ʹ��
			}
		} catch (SQLException e) {
			System.out.println("checkUserName ERROR");
			e.printStackTrace();
		}
		return flag;
		}
	
	
	//�û���½�ķ���
		@Override
		public User login(User user) {
			//����һ������ֵ
			User u = null;
			//��������
			Connection conn = null;
			try {
				//��ȡ
				conn = (Connection) DBUtil.getConn();
				String sql = "SELECT * FROM T_USER WHERE USERNAME = ? AND PASSWORD = ?";
				
				//���ð󶨱���
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

		//�û���Ϣ�޸ı��淽��
		//���²���qr.update(a,b,c);
		@Override
		public void update(User user) {
			//��������
			Connection conn = null;
			try {
				//��ȡ
				conn = (Connection) DBUtil.getConn();
				String sql = "UPDATE  T_USER  SET ADDRESS = ? ,BIRTHDAY = ?,DENTITYCODE = ? ,"
						+ "EMAIL = ?,MOBILE = ?,PASSWORD = ?,SEX = ?,STATUS = ?,USERNAME = ?,TRUENAME=? WHERE ID =?";
				//���ð󶨱���
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
