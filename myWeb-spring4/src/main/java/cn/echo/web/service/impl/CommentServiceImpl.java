package cn.echo.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.echo.web.mapper.CommentMapper;
import cn.echo.web.pojo.Comment;
import cn.echo.web.service.CommentService;
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements
		CommentService {

	@Autowired
	CommentMapper commentMapper;
	
	@Override
	public PageInfo<Comment> findAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> comments = commentMapper.findAll();
		PageInfo<Comment> pages = new PageInfo<Comment>(comments);
		return pages;
	}

	@Override
	public PageInfo<Comment> findAllByUserid(String userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> comments = commentMapper.findAllByUserid(userId);
		PageInfo<Comment> pages = new PageInfo<Comment>(comments);
		return pages;
	}

}
