package com.mage.service;

import java.util.List;

import com.mage.vo.OrderProduct;

public interface OrderProductService {
	//查询所有订单
	List<OrderProduct> findByOrderId(int orderId);
	//保存结算
	void save(OrderProduct orderProduct);
}
