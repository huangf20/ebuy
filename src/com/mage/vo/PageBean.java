package com.mage.vo;

/**
* ��ҳ����
 * @author 
 *
 */
public class PageBean {

	private int page; // �ڼ�ҳ n
	private int pageSize; // ÿҳ��¼�� b

	
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
