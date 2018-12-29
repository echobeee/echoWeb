package cn.echo.web.mapper;

import cn.echo.web.pojo.Comment;
import cn.echo.web.pojo.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends  BaseMapper<Comment>{
	
	/*
	 * 查询所有的评论
	 */
    List<Comment> findAll();
    /*
     * 查询我的所有评论
     */
    List<Comment> findAllByUserid(String userId);
}