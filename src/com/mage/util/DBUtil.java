package com.mage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	/**
	 * ����jdbc������
	 */
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER Error");
			e.printStackTrace();
		}
	}
	
		//��ȡ����
		public static Connection getConn(){
			Connection conn=null;
			try {
				conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ebuy", "root", "234111");
			} catch (SQLException e) {
				System.out.println("Connection Error");
				e.printStackTrace();
			}
			return conn;
		}
		
		//�ر�����
		public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
			closeResult(rs);
			closeState(pstmt);
			closeConn(conn);
		}
		//�ر�����
		public static void closeConn(Connection conn){
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("CLOSE ERROR"+conn);
					e.printStackTrace();
				}
			}
		}
		//�رչܵ�
		public static void closeState(PreparedStatement pstmt){
			if(null!=pstmt){
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println("CLOSE ERROR"+pstmt);
					e.printStackTrace();
				}
			}
		}
		//�رս��
		public static void closeResult(ResultSet rs){
			if(null!=rs){
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("CLOSE ERROR"+rs);
					e.printStackTrace();
				}
			}
		}

}
