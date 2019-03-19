package cn.echo.web.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.echo.web.mapper.BaseMapper;
import cn.echo.web.mapper.CommentMapper;
import cn.echo.web.mapper.MemoMapper;
import cn.echo.web.mapper.UserMapper;
import cn.echo.web.service.BaseService;
import cn.pconline.prolib.util.GenericsUtils;


public class BaseServiceImpl<T> implements BaseService<T> {
	
	protected BaseMapper<T> baseMapper;
	
	@Autowired
    protected UserMapper userMapper;

    @Autowired
    protected CommentMapper commentMapper;

    @Autowired
    protected MemoMapper memoMapper;

	/*
	 *  初始化baseMapper，哪种类型的service实现调用该方法，baseMapper就是那种类型
	 */
	@SuppressWarnings("rawtypes")
	@PostConstruct
	void initBaseMapper() throws Exception {
		// 获取泛型的信息
//		Type type =  this.getClass().getGenericSuperclass();
//		ParameterizedType parameterizedType = (ParameterizedType) type;

//		Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
		Class clazz = GenericsUtils.getSuperClassGenricType(getClass(), 0);
		
		//拼接出pojo+mapper的字符串
		String localField = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1) + "Mapper";
	
		//通过反射来获取成员变量的值
		Field field = this.getClass().getSuperclass().getDeclaredField(localField);
		Field baseField = this.getClass().getSuperclass().getDeclaredField("baseMapper");
		
		//将baseMapper进行实例化
		baseField.set(this, field.get(this));
	}
	
	@Override
	public int insert(T record) {
		return baseMapper.insert(record);
	}

	@Override
	public int insertSelective(T record) {
		return baseMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String recordId) {
		return baseMapper.deleteByPrimaryKey(recordId);
	}

	@Override
	public T selectByPrimaryKey(String recordId) {
		return baseMapper.selectByPrimaryKey(recordId);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return baseMapper.updateByPrimaryKey(record);
	}

	@Override
	public long countAll() {
		// TODO Auto-generated method stub
		return baseMapper.countAll();
	}


}
