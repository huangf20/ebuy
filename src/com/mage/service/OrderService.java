package com.mage.service;

import java.util.List;

import com.mage.vo.Order;

public interface OrderService {
	//展示订单列表
	List<Order> findAll(int userId);
	//确认收货
	void status(int orderId);
	//模糊搜索
	List<Order> search(Long orderNo,int userId);
	//结算
	void saveOrder(Order order);
}
