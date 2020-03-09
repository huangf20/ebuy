package com.mage.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.OrderProductService;
import com.mage.service.ProductService;
import com.mage.util.DBUtil;
import com.mage.vo.OrderProduct;

public class OrderProductServiceImpl implements OrderProductService{
	private ProductService productService=new ProductServiceImpl();
	@Override
	public List<OrderProduct> findByOrderId(int orderId) {
		//声明结果集合
		List<OrderProduct> orderProductList=null;
		//声明连接
		Connection conn = null;
		try {
			//获取
			conn = DBUtil.getConn();
			String sql = "SELECT * FROM T_ORDER_PRODUCT WHERE ORDERID =?";
			
			//设置绑定变量
			Object[] pars = new Object[] {orderId};
			
			QueryRunner qr = new QueryRunner();
			
			orderProductList=qr.query(conn, sql, new BeanListHandler<OrderProduct>(OrderProduct.class), pars);
			for(OrderProduct op:orderProductList){
				op.setProduct(productService.getProductById(op.getProductId()));
			}
		}catch(SQLException e) {
			System.out.println("find big type error");
			e.printStackTrace();
		}finally {
			DBUtil.closeConn(conn);
		}
		return orderProductList;
	}
	
	@Override
	public void save(OrderProduct orderProduct) {
		// TODO Auto-generated method stub
		
	}

}
