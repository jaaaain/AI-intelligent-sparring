package com.jaaaain.mapper;

import com.jaaaain.entity.Ratings;
import com.jaaaain.vo.RatAvgVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 存储对话的评分结果(Ratings)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
 @Mapper
public interface RatingsMapper {
    /**
     * 通过ID查询单条数据
     * @param ratingId 主键
     * @return 实例对象
     */
    @Select("select * from ratings where rating_id=#{ratingId}")
    Ratings queryByRatingId(Integer ratingId);
    @Select("select * from ratings where session_id=#{sessionId}")
    Ratings queryBysessionId(String sessionId);

    /**
     * 通过主键删除数据
     * @param ratingId 主键
     * @return 影响行数
     */
    @Delete("delete from ratings where rating_id = #{ratingId}")
    int deleteById(Integer ratingId);
    
    /**
     * 新增数据
     * @param ratings 实例对象
     * @return 影响行数
     */
    int insert(Ratings ratings);

    /**
     * 获取用户所有会话历史评分
     * @param userId
     * @return
     */
    List<Ratings> queryByUserId(String userId);

    /**
     * 获取用户各维度平均分
     * @param uid
     * @return
     */
    RatAvgVO queryAvgByUserId(String uid);
}

