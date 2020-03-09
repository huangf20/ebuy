package com.mage.service;

import java.util.List;

import com.mage.vo.ProductSmallType;

public interface ProductSmallTypeService {
	
	List<ProductSmallType> findByBigTypeId(int bigTypeid);

}
