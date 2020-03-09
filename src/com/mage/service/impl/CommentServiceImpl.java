package com.mage.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.CommentService;
import com.mage.util.DBUtil;
import com.mage.vo.Comment;
import com.mage.vo.PageBean;
import com.mysql.jdbc.Connection;

public class CommentServiceImpl implements CommentService{

	
	@Override
	public List<Comment> findAllCommentList(PageBean pageBean) {
		List<Comment> commentList=null;
		Connection conn=null;
		
		try {
			//创建连接通道
			conn=(Connection) DBUtil.getConn();
			//编写Sql语句
			String sql="SELECT * FROM T_COMMENT ORDER BY CREATETIME LIMIT ?,?";
			//设置绑定参数
			Object[] objs=new Object[]{(pageBean.getPage()-1)*pageBean.getPageSize(),pageBean.getPageSize()};
			//创建QueryRunner对象
			QueryRunner qr=new QueryRunner();
			commentList=qr.query(conn, sql, new BeanListHandler<Comment>(Comment.class), objs);
		} catch (SQLException e) {
			System.out.println("FINDALLCOMMENTLIST ERROR");
			e.printStackTrace();
		}finally{
			DBUtil.closeConn(conn);
		}
		return commentList;
	}
	
	//查询留言信息的总数量
		@Override
		public int commentListCount() {
			//声明结果集
			int count=0;
			//声明连接通道
			Connection conn=null;
			try {
				//创建连接通道
				conn=(Connection) DBUtil.getConn();
				//编写sql语句
				String sql="SELECT * FROM T_COMMENT ";
				//绑定参数
				Object[] objs=new Object[] {};
				//创建QueryRunner对象
				QueryRunner qr=new QueryRunner();
				//查询
				List<Comment> ls=qr.query(conn, sql, new BeanListHandler<Comment>(Comment.class), objs);
				count=ls.size();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
		}
		//保存留言
		@Override
		public void saveComment(Comment comment) {
			Connection conn = null;
			try {
				conn = (Connection) DBUtil.getConn();
				String sql = "INSERT INTO T_COMMENT (CONTENT,CREATETIME,NICKNAME) VALUES (?,?,?)";
				
				QueryRunner qr = new QueryRunner();
				Object[] obj = new Object[] {comment.getContent(),comment.getCreateTime(),comment.getNickName()};
				qr.update(conn, sql,obj);
			}catch(SQLException e) {
				System.out.println("SAVECOMMENT ERROR");
				e.printStackTrace();
			}finally {
				DBUtil.closeConn(conn);
			}
			
		}

}
