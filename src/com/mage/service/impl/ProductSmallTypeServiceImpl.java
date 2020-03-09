package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mage.service.ProductSmallTypeService;
import com.mage.util.DBUtil;
import com.mage.vo.ProductSmallType;

public class ProductSmallTypeServiceImpl implements ProductSmallTypeService{

	@Override
	public List<ProductSmallType> findByBigTypeId(int bigTypeId) {
		List<ProductSmallType> stList=new ArrayList<>();
		//声明链接
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConn();
			String sql="SELECT * FROM t_smalltype WHERE BIGTYPEID =?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bigTypeId);
			rs=pstmt.executeQuery();
			//封装结果集合
			while(rs.next()){
				ProductSmallType p=new ProductSmallType();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setRemarks(rs.getString("remarks"));
				p.setBigTypeId(rs.getInt("bigTypeId"));
				stList.add(p);
			}
			return stList;
		} catch (SQLException e) {
			System.out.println("List<ProductSmallType> ERROR");
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

}
