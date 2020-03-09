package com.mage.vo;

/**
* 分页对象
 * @author 
 *
 */
public class PageBean {

	private int page; // 第几页 n
	private int pageSize; // 每页记录数 b

	
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
