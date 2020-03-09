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
	 * �ؼ�
	 */
	@Override
	public List<Product> findSpecialPrice() {
		List<Product> productList=new ArrayList<>();
				//��������
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
	 * �ȶ�
	 */
	@Override
	public List<Product> findHotProductList() {
		List<Product> productList=new ArrayList<>();
		//��������
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
	//���С�����ѯlist����
		@Override
		public List<Product> findProductListSmallType(PageBean pageBean, int smallType) {
			List<Product> productList = null;
			//��������
			Connection conn = null;
			try {
				//��ȡ
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
		//���С�����ѯ��ҳ������
		@Override
		public int getProductCountSmallType(int smallType) {
			int i=0;
			//��������
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
		
		
		//top������ѯ����
		@Override
		public List<Product> findProductListBigType(PageBean pageBean, int bigTypeId) {
			List <Product> productList=null;
			//��������ͨ��
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
		//top������ѯ��������
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
		//������ť��ѯ����
		@Override
		public List<Product> findProductList(PageBean pageBean, String searchName) {
			//���������
			List<Product> productList = null;
			//��������
			Connection conn = null;
			try {
				//��ȡ
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
		//��������
		@Override
		public int getProductCount(String searchName) {
			//���������
			int i=0;
			//��������
			Connection conn = null;
			try {
				//��ȡ
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
		//��Ʒ����չʾ
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				System.out.println("GETPRODUCTBYID ERROR");
			}finally {
				DBUtil.closeConn(conn);
			}
			return product;
		}
		

}