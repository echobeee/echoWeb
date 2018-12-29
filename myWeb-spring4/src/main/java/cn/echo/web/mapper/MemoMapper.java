package cn.echo.web.mapper;

import cn.echo.web.pojo.Comment;
import cn.echo.web.pojo.Memo;
import cn.echo.web.pojo.MemoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemoMapper extends BaseMapper<Memo> {
    /*
     * 查询我的所有备忘录
     */
	List<Memo> findAllByUserid(String userId);
		
	/**
	 * 根据userId查询用户在date期间的备忘录
	 * @param userId
	 * @param date date格式：YYYY-mm
	 * @return
	 */
	List<Memo> findByUseridWithDate(@Param("userId")String userId, @Param("date")String date);

	/**
	 * 查询所有未执行的任务
	 * @return
	 */
	List<Memo> findAll();
	
}