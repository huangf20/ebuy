package com.mage.service;

import java.util.List;

import com.mage.vo.PageBean;
import com.mage.vo.Product;

public interface ProductService {
	//��ҳ�ؼ���Ʒ�б�չʾ
		public List<Product> findSpecialPrice();
		//��ҳ�ȶ���Ʒ�б�չʾ
		public List<Product> findHotProductList();
		//���С�����ѯlist����
		public List<Product> findProductListSmallType(PageBean pageBean, int smallType);
		//���С�����ѯ��ҳ������
		public int getProductCountSmallType(int smallType);
		
		//�ϲ��ִ�����ѯlist����
		public List<Product> findProductListBigType(PageBean pageBean, int bigTypeId);
		//�ϲ��ִ�����ѯ��ҳ������
		public int getProductCountBigType(int bigTypeId);
		
		//��ҳ������ť
		List<Product> findProductList(PageBean pageBean, String searchName);
		//��ҳ������ť��ѯ������
		int getProductCount(String searchName);
		
		//��Ʒ������չʾ
		Product getProductById(int productId);
		
}	
