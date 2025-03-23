package com.jaaaain.service.impl;

import com.jaaaain.entity.Ratings;
import com.jaaaain.mapper.RatingsMapper;
import com.jaaaain.service.RatingsService;
import com.jaaaain.vo.RatAvgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 存储对话的评分结果(Ratings)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service
public class RatingsServiceImpl implements RatingsService {

    @Autowired
    private RatingsMapper ratingsMapper;

    /**
     * 创建评分记录
     * @param sid
     * @param aiResponse
     * @return
     */
    @Override
    public boolean newRating(String sid,String aiResponse) {
        // 检验ai的回复符合解析规范
        if(!checkRating(aiResponse)){
            return false;
        }
        // 创建对象实例
        Ratings ratings = new Ratings();
        ratings.setSessionId(sid);
        // 解析
        ratings = regexForRating(ratings,aiResponse);
        System.out.println(ratings);
        // 存储
        ratingsMapper.insert(ratings);
        return true;
    }

    /**
     * 通过ID查询单条数据
     * @param ratingId 主键
     * @return 实例对象
     */
    @Override
    public Ratings queryByRatingId(Integer ratingId) {
        return ratingsMapper.queryByRatingId(ratingId);
    }

    @Override
    public Ratings queryBySessionId(String sessionId) {
        return ratingsMapper.queryBysessionId(sessionId);
    }

    /**
     * 通过主键删除数据
     * @param ratingId 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer ratingId) {
        return ratingsMapper.deleteById(ratingId) > 0;
    }

    /**
     * 获取用户所有会话历史评分
     *
     * @param uid
     * @return
     */
    @Override
    public List<Ratings> queryByUserId(String uid) {
        return ratingsMapper.queryByUserId(uid);
    }

    /**
     * 获取用户各维度平均分
     *
     * @param uid
     * @return
     */
    @Override
    public RatAvgVO avgByUserId(String uid) {
        return ratingsMapper.queryAvgByUserId(uid);
    }


    private Ratings regexForRating(Ratings ratings,String str){
        String pattern1 = ".*(?<dimension>L|A|S|T).*：(?<score>\\d+)/10，(?<comment>.*)";
        String pattern2 = "(.*\n?)+\n*suggestion：\n*(?<suggestion>(.*\n?)*)";
        Pattern r1 = Pattern.compile(pattern1);
        Pattern r2 = Pattern.compile(pattern2);
        Matcher m1 = r1.matcher(str);
        Matcher m2 = r2.matcher(str);
        Integer score =0;
        while (m1.find()) {
            Integer score_temp = Integer.valueOf(m1.group("score"));
            String comment = m1.group("comment");
            switch (m1.group("dimension")) {
                case "L": {
                    ratings.setL(score_temp);
                    ratings.setL_comment(comment);
                    break;
                }
                case "A": {
                    ratings.setA(score_temp);
                    ratings.setA_comment(comment);
                    break;
                }
                case "S": {
                    ratings.setS(score_temp);
                    ratings.setS_comment(comment);
                    break;
                }
                case "T": {
                    ratings.setT(score_temp);
                    ratings.setT_comment(comment);
                    break;
                }
            }
            score += score_temp;
        }
        score = score / 4;
        ratings.setScore(score);
        if(m2.find())
            ratings.setSuggestion(m2.group("suggestion"));
        return ratings;
    }

    private boolean checkRating(String str){
        String pattern = "(.*(L|A|S|T).*：\\d+/10，.*\\n){4}(\\n*.*)*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

}
