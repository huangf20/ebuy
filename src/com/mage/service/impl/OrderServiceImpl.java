package com.mage.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.OrderProductService;
import com.mage.service.OrderService;
import com.mage.util.DBUtil;
import com.mage.vo.Order;
import com.mage.vo.OrderProduct;

public class OrderServiceImpl implements OrderService {
	private OrderProductService orderProductService=new OrderProductServiceImpl();
	//订单表所有信息进行展示
	@Override
	public List<Order> findAll(int userId) {
		List<Order> orderList=null;
		//声明连接
		Connection conn = null;
		try {
			//获取
			conn = DBUtil.getConn();
			String sql = "SELECT * FROM T_ORDER WHERE USERID =?";
			
			//设置绑定变量
			Object[] pars = new Object[] {userId};
			
			QueryRunner qr = new QueryRunner();
			
			orderList=qr.query(conn, sql, new BeanListHandler<>(Order.class), pars);
			for(Order o:orderList){
				List<OrderProduct> orderProductList=orderProductService.findByOrderId(o.getId());
				o.setOrderProductList(orderProductList);
			}
		}catch(SQLException e) {
			System.out.println("FINDALLORDER ERROR");
			e.printStackTrace();
		}finally {
			DBUtil.closeConn(conn);
		}
		return orderList;
	}
	//确认收货
	@Override
	public void status(int orderId) {
				//声明连接
				Connection conn = null;
				try {
					//获取
					conn = DBUtil.getConn();
					String sql = "UPDATE T_ORDER SET STATUS = ? WHERE ID=?";
					
					//设置绑定变量
					Object[] pars = new Object[] {4,orderId};
					
					QueryRunner qr = new QueryRunner();
					
					qr.update(conn, sql, pars);
					
				}catch(SQLException e) {
					System.out.println("STATUS ERROR");
					e.printStackTrace();
				}finally {
					DBUtil.closeConn(conn);
				}
		
	}
	//模糊搜索
	@Override
	public List<Order> search(Long orderNo, int userId) {
		List<Order> orderList=null;
		//声明连接
		Connection conn = null;
		try {
			//获取
			conn = DBUtil.getConn();
			String sql = "SELECT * FROM T_ORDER WHERE ORDERNO LIKE ? AND USERID = ?";
			
			//设置绑定变量
			Object[] pars = new Object[] {"%"+orderNo+"%",userId};
			
			QueryRunner qr = new QueryRunner();
			
			orderList=qr.query(conn, sql, new BeanListHandler<>(Order.class), pars);
			for(Order o:orderList){
				List<OrderProduct> orderProductList=orderProductService.findByOrderId(o.getId());
				o.setOrderProductList(orderProductList);
			}
		}catch(SQLException e) {
			System.out.println("SEARCHLIKE ERROR");
			e.printStackTrace();
		}finally {
			DBUtil.closeConn(conn);
		}
		return orderList;
	}
	//结算
	@Override
	public void saveOrder(Order order) {
				//声明连接
				Connection conn = null;
				try {
					//获取
					conn = DBUtil.getConn();
					String sql = "INSERT INTO T_ORDER (COST,CREATETIME,ORDERNO,"
							+ "STATUS,USERID) VALUES (?,?,?,?,?)";
					
					//设置绑定变量
					Object[] pars = new Object[] {
							order.getCost(),order.getCreateTime(),
							order.getOrderNo(), order.getStatus(),order.getUser().getId()};
					
					QueryRunner qr = new QueryRunner();
					
					qr.update(conn, sql,  pars);
					
				}catch(SQLException e) {
					System.out.println("SAVEORDER ERROR");
					e.printStackTrace();
				}finally {
					DBUtil.closeConn(conn);
				}	
	}

}
