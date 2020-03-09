package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mage.service.NewsService;
import com.mage.util.DBUtil;
import com.mage.vo.News;

public class NewsServiceImpl  implements NewsService{

	@Override
	public List<News> findAll() {
		List<News> newss=new ArrayList<>();
		//声明链接
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConn();
			String sql="SELECT * FROM T_NEWS WHERE ID <7";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				News n=new News();
				n.setId(rs.getInt("id"));
				n.setContent(rs.getString("content"));
				n.setCreateTime(rs.getDate("createTime"));
				n.setTitle(rs.getString("title"));
				newss.add(n);
			}
			return newss;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

	//新闻的详情页面展示
	@Override
	public News getNewsById(int newsId) {
		News news=null;
		Connection conn=null;
		
		try {
			conn=DBUtil.getConn();
			String sql="select * from t_news where id =?";
			QueryRunner qr=new QueryRunner();
			Object[] obj=new Object[] {newsId};
			news=(News) qr.query(conn, sql, new BeanHandler(News.class), obj);
		} catch (SQLException e) {
			System.out.println("GETNEWSBYID ERROR");
			e.printStackTrace();
		}finally {
			DBUtil.closeConn(conn);
		}
		
		
		return news;
	}

}
