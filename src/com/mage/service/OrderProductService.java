package com.mage.service;

import java.util.List;

import com.mage.vo.OrderProduct;

public interface OrderProductService {
	//��ѯ���ж���
	List<OrderProduct> findByOrderId(int orderId);
	//�������
	void save(OrderProduct orderProduct);
}
