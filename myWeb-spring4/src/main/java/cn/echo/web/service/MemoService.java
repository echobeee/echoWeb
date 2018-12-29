package cn.echo.web.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.echo.web.pojo.Memo;

public interface MemoService extends BaseService<Memo> {
	/*
     * 查询我的所有备忘录
     */
	PageInfo<Memo> findAllByUserid(String userId, int pageNum, int pageSize);
	
	/**
	 * 根据userId查询用户在date期间的备忘录
	 * @param userId
	 * @param date date格式：YYYY-mm
	 * 			ps: 传入的月份必须包含2个数字，即1月->01
	 * @return
	 */
	List<Memo> findMemoByUseridWithDate(String userId, String date);
	
	List<Memo> findAllMemos();
}
