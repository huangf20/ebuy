package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mage.service.ProductBigTypeService;
import com.mage.service.ProductSmallTypeService;
import com.mage.util.DBUtil;
import com.mage.vo.ProductBigType;
import com.mage.vo.ProductSmallType;

public class ProductBigTypeServiceImpl implements ProductBigTypeService {
	
	private ProductSmallTypeService smallService=new ProductSmallTypeServiceImpl();
	
	@Override
	public List<ProductBigType> findAllBigType() {
		List<ProductBigType> stList=new ArrayList<>();
		//声明链接
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConn();
			String sql="SELECT * FROM t_bigtype";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//封装结果集合
			while(rs.next()){
				ProductBigType p=new ProductBigType();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setRemarks(rs.getString("remarks"));
				List<ProductSmallType> pst= smallService.findByBigTypeId(rs.getInt("id"));
				p.setSmallTypeList(pst);
				stList.add(p);
			}
			return stList;
		} catch (SQLException e) {
			System.out.println("List<ProductBigType> ERROR");
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

}
