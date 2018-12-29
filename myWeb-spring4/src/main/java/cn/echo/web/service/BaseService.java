package cn.echo.web.service;

import java.util.List;

public interface BaseService<T> {

	int insert(T record);
	
	int insertSelective(T record);
	
	int deleteByPrimaryKey(String recordId);
	
	T selectByPrimaryKey(String recordId);
	
	int updateByPrimaryKey(T record);
	
	long countAll();

	
}
