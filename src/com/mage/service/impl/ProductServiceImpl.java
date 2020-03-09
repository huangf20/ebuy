package com.mage.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.ProductService;
import com.mage.util.DBUtil;
import com.mage.vo.News;
import com.mage.vo.PageBean;
import com.mage.vo.Product;


public class ProductServiceImpl implements ProductService{
	
	/**
	 * 特价
	 */
	@Override
	public List<Product> findSpecialPrice() {
		List<Product> productList=new ArrayList<>();
				//声明链接
				Connection conn=null;
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				try {
					conn=DBUtil.getConn();
					String sql="SELECT * FROM t_product WHERE SPECIALPRICE = 1 AND ID<41 ORDER BY SPECIALPRICETIME DESC";
					pstmt=conn.prepareStatement(sql);
					rs=pstmt.executeQuery();
					while(rs.next()){
						Product product = new Product();
						product.setId(rs.getInt("id"));
						product.setDescription(rs.getString("description"));
						product.setHot(rs.getInt("hot"));
						product.setHotTime(rs.getDate("hotTime"));
						product.setName(rs.getString("name"));
						product.setPrice(rs.getInt("price"));
						product.setProPic(rs.getString("proPic"));
						product.setSpecialPrice(rs.getInt("specialPrice"));
						product.setSpecialPriceTime(rs.getDate("specialPriceTime"));
						product.setStock(rs.getInt("stock"));
						productList.add(product);
					}
					return productList;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					DBUtil.closeAll(conn, pstmt, rs);
				}
				return null;
	}
	/**
	 * 热度
	 */
	@Override
	public List<Product> findHotProductList() {
		List<Product> productList=new ArrayList<>();
		//声明链接
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConn();
			String sql="SELECT * FROM t_product WHERE HOT = 1 AND ID<17 ORDER BY HOTTIME DESC";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setDescription(rs.getString("description"));
				product.setHot(rs.getInt("hot"));
				product.setHotTime(rs.getDate("hotTime"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setProPic(rs.getString("proPic"));
				product.setSpecialPrice(rs.getInt("specialPrice"));
				product.setSpecialPriceTime(rs.getDate("specialPriceTime"));
				product.setStock(rs.getInt("stock"));
				productList.add(product);
			}
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}
	//左侧小分类查询list集合
		@Override
		public List<Product> findProductListSmallType(PageBean pageBean, int smallType) {
			List<Product> productList = null;
			//声明连接
			Connection conn = null;
			try {
				//获取
				conn = DBUtil.getConn();
				
				QueryRunner qr = new QueryRunner();
				
				String sql = "SELECT * FROM T_PRODUCT WHERE SMALLTYPEID = ? ORDER BY HOTTIME LIMIT ?,?";
				
				Object[] obj = new Object[] {smallType,(pageBean.getPage()-1)*(pageBean.getPageSize()),pageBean.getPageSize()};
				List  ls = qr.query(conn, sql, new BeanListHandler<Product>(Product.class),obj);
				productList = ls;
			}catch(SQLException e) {
				System.out.println("findProductListSmallType error");
				e.printStackTrace();
			}finally {
				DBUtil.closeConn(conn);
			}
			return productList;
		}
		//左侧小分类查询分页总数量
		@Override
		public int getProductCountSmallType(int smallType) {
			int i=0;
			//声明连接
			Connection conn=null;
			try {
				conn=DBUtil.getConn();
				String sql="SELECT * FROM T_PRODUCT WHERE SMALLTYPEID = ?";
				Object[] obj=new Object[] {smallType};
				QueryRunner qr=new QueryRunner();
				List ls=qr.query(conn, sql, new BeanListHandler<Product>(Product.class), obj);
				i=ls.size();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return i;
		}
		
		
		//top大分类查询集合
		@Override
		public List<Product> findProductListBigType(PageBean pageBean, int bigTypeId) {
			List <Product> productList=null;
			//声明连接通道
			Connection conn=null;
			
			try {
				conn=DBUtil.getConn();
				String sql="SELECT * FROM T_PRODUCT WHERE BIGTYPEID = ? ORDER BY HOTTIME LIMIT ?,?";
				Object [] objs=new Object[] {bigTypeId,((pageBean.getPage()-1)*pageBean.getPageSize()),pageBean.getPageSize()};
				QueryRunner queryRunner=new QueryRunner();
				productList=queryRunner.query(conn, sql, new BeanListHandler<Product>(Product.class), objs);
			} catch (SQLException e) {
				System.out.println("FINDPRODUCTLISTBIGTYPE ERROR");
				e.printStackTrace();
			}finally{
				DBUtil.closeConn(conn);
			}
			return productList;
		}
		//top大分类查询集合数量
		@Override
		public int getProductCountBigType(int bigTypeId) {
			int i=0;
			Connection conn=null;
			
			
			try {
				conn=DBUtil.getConn();
				String sql="SELECT * FROM T_PRODUCT WHERE BIGTYPEID = ?";
				Object[] objs=new Object[]{bigTypeId};
				QueryRunner queryRunner=new QueryRunner();
				List ls=queryRunner.query(conn, sql, new BeanListHandler<Product>(Product.class), objs);
				i=ls.size();
			} catch (SQLException e) {
				System.out.println("GETPRODUCTCOUNTBIGTYPE ERROR");
				e.printStackTrace();
			}finally{
				DBUtil.closeConn(conn);
			}
			return i;
		}
		//搜索按钮查询集合
		@Override
		public List<Product> findProductList(PageBean pageBean, String searchName) {
			//声明结果集
			List<Product> productList = null;
			//声明连接
			Connection conn = null;
			try {
				//获取
				conn = DBUtil.getConn();
				
				QueryRunner qr = new QueryRunner();
				
				String sql = "SELECT * FROM T_PRODUCT WHERE NAME LIKE ? ORDER BY HOTTIME LIMIT ?,?";
				
				Object[] obj = new Object[] {"%"+searchName+"%",(pageBean.getPage()-1)*(pageBean.getPageSize()),pageBean.getPageSize()};
				
				productList = qr.query(conn, sql, new BeanListHandler<Product>(Product.class),obj);
			}catch(SQLException e) {
				System.out.println("FINDPRODUCTLIST ERROR");
				e.printStackTrace();
			}finally {
				DBUtil.closeConn(conn);
			}
			return productList;
		}
		//搜索数量
		@Override
		public int getProductCount(String searchName) {
			//声明结果集
			int i=0;
			//声明连接
			Connection conn = null;
			try {
				//获取
				conn = DBUtil.getConn();
						
				QueryRunner qr = new QueryRunner();
						
				String sql = "SELECT * FROM T_PRODUCT WHERE NAME LIKE ? ";
						
				Object[] obj = new Object[] {"%"+searchName+"%"};
						
			    List ls= qr.query(conn, sql, new BeanListHandler<Product>(Product.class),obj);
			    i=ls.size();
				}catch(SQLException e) {
					System.out.println("FINDPRODUCTLIST ERROR");
					e.printStackTrace();
				}finally {
					DBUtil.closeConn(conn);
				}
			return i;
		}
		//商品详情展示
		@Override
		public Product getProductById(int productId) {
			Product product=null;
			Connection conn=null;
			try {
				
				conn=DBUtil.getConn();
				String sql="select * from t_product where id=?";
				Object [] objs=new Object[] {productId};
				QueryRunner qr=new QueryRunner();
				product=qr.query(conn, sql, new BeanHandler<Product>(Product.class), objs);
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("GETPRODUCTBYID ERROR");
			}finally {
				DBUtil.closeConn(conn);
			}
			return product;
		}
		

}