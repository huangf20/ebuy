package com.mage.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductBigType {
	private int id;//主建id
	private String name;//名称
	private String remarks;//备注
	private List<ProductSmallType> smallTypeList=new ArrayList<ProductSmallType>();//小类别集合
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
