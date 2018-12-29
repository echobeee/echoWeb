package cn.echo.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.echo.web.mapper.MemoMapper;
import cn.echo.web.pojo.Comment;
import cn.echo.web.pojo.Memo;
import cn.echo.web.service.MemoService;
@Service
public class MemoServiceImpl extends BaseServiceImpl<Memo> implements MemoService {

	@Autowired
	MemoMapper memoMapper;
	
	@Override
	public PageInfo<Memo> findAllByUserid(String userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Memo> list = memoMapper.findAllByUserid(userId);
		PageInfo<Memo> pages = new PageInfo<Memo>(list);
		return pages;
	}

	@Override
	public List<Memo> findMemoByUseridWithDate(String userId, String date) {
		
		return memoMapper.findByUseridWithDate(userId, date);
	}

	@Override
	public List<Memo> findAllMemos() {
		
		return memoMapper.findAll();
	}
	
	
	
}
