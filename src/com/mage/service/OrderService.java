package com.mage.service;

import java.util.List;

import com.mage.vo.Order;

public interface OrderService {
	//չʾ�����б�
	List<Order> findAll(int userId);
	//ȷ���ջ�
	void status(int orderId);
	//ģ������
	List<Order> search(Long orderNo,int userId);
	//����
	void saveOrder(Order order);
}
