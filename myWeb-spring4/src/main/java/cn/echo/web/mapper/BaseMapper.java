package cn.echo.web.mapper;


/**
 * 
 * @author echo
 * baseDao 泛型dao，通用的增删改查方法
 * 其它mapper继承该mapper，需要多的方法再创建新方法
 * @param <T>
 */
public interface BaseMapper<T> {
	
	int insert(T record);
	
	int insertSelective(T record);
	
	int deleteByPrimaryKey(String recordId);
	
	T selectByPrimaryKey(String recordId);
	
	int updateByPrimaryKey(T record);
	
	long countAll();
	
}
