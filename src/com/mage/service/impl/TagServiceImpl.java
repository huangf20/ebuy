package com.mage.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mage.service.TagService;
import com.mage.util.DBUtil;
import com.mage.vo.Tag;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class TagServiceImpl implements TagService{

	@Override
	public List<Tag> findAll() {
		// TODO 自动生成的方法存根
		List<Tag> tagList=new ArrayList<>();
		//ÉùÃ÷Á´½Ó
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=(Connection) DBUtil.getConn();
			String sql="SELECT * FROM T_TAG";
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Tag t=new Tag();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				t.setUrl(rs.getString("url"));
				tagList.add(t);
			}
			return tagList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

}
