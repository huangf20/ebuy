package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mage.service.NoticeService;
import com.mage.util.DBUtil;
import com.mage.vo.News;
import com.mage.vo.Notice;

@SuppressWarnings("unused")
public class NoticeServiceImpl implements NoticeService{

	@Override
	public List<Notice> findAll() {
		List<Notice> noticeList=new ArrayList<>();
		//声明链接
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConn();
			String sql="SELECT * FROM T_Notice WHERE ID <7";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Notice n=new Notice();
				n.setId(rs.getInt("id"));
				n.setContent(rs.getString("content"));
				n.setCreateTime(rs.getDate("createTime"));
				n.setTitle(rs.getString("title"));
				noticeList.add(n);
			}
			return noticeList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

	//公告详情
	public Notice getNoticeById(int noticeId) {
		Notice notice=null;
		Connection conn=null;
		conn=DBUtil.getConn();
		String sql="select * from t_notice where id=?";
		Object[] objs=new Object[] {noticeId};
		QueryRunner qr=new QueryRunner();
		try {
			notice=(Notice) qr.query(conn, sql, new BeanHandler(Notice.class), objs);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		return notice;
	}

}
