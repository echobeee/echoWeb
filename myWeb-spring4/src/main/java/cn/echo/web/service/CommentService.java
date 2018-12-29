package cn.echo.web.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.echo.web.pojo.Comment;

public interface CommentService extends BaseService<Comment> {

	/*
	 * 查询所有的评论
	 */
    PageInfo<Comment> findAll(int pageNum, int pageSize);
    /*
     * 查询我的所有评论
     */
    PageInfo<Comment> findAllByUserid(String userId, int pageNum, int pageSize);
	
}
