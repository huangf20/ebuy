package com.mage.vo;

public class ShoppingCartItem {

	private int id;
	private Product product;//��Ʒ
	private int count;//��Ʒ������
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
