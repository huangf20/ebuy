package com.mage.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order {
	private int id;//id
	private String orderNo;//订单编号
	private Date createTime;//创建时间
	private float cost;//价格
	private int status; // 状态
	private User user;//所属用户
	//中间表
	private List<OrderProduct> orderProductList=new ArrayList<OrderProduct>();
	
	public Order() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
	
	
	
}
