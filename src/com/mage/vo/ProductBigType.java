package com.mage.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductBigType {
	private int id;//����id
	private String name;//����
	private String remarks;//��ע
	private List<ProductSmallType> smallTypeList=new ArrayList<ProductSmallType>();//С��𼯺�
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<ProductSmallType> getSmallTypeList() {
		return smallTypeList;
	}
	public void setSmallTypeList(List<ProductSmallType> smallTypeList) {
		this.smallTypeList = smallTypeList;
	}
	
}
