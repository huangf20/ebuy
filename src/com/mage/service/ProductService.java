package com.mage.service;

import java.util.List;

import com.mage.vo.PageBean;
import com.mage.vo.Product;

public interface ProductService {
	//首页特价商品列表展示
		public List<Product> findSpecialPrice();
		//首页热度商品列表展示
		public List<Product> findHotProductList();
		//左侧小分类查询list集合
		public List<Product> findProductListSmallType(PageBean pageBean, int smallType);
		//左侧小分类查询分页总数量
		public int getProductCountSmallType(int smallType);
		
		//上部分大分类查询list集合
		public List<Product> findProductListBigType(PageBean pageBean, int bigTypeId);
		//上部分大分类查询分页总数量
		public int getProductCountBigType(int bigTypeId);
		
		//首页搜索按钮
		List<Product> findProductList(PageBean pageBean, String searchName);
		//首页搜索按钮查询总数量
		int getProductCount(String searchName);
		
		//商品的详情展示
		Product getProductById(int productId);
		
}	
