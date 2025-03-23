package com.jaaaain.controller;

import com.jaaaain.entity.Ratings;
import com.jaaaain.result.Result;
import com.jaaaain.service.RatingsService;
import com.jaaaain.vo.RatAvgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private RatingsService ratingsService;

    /**
     * 获取单次会话评分
     * @param sid
     * @return
     */
    @GetMapping("/session/{sid}")
    public Result getRatingForSession(@PathVariable("sid") String sid) {
        Ratings ratings = ratingsService.queryBySessionId(sid);
        return Result.success(ratings);
    }

    /**
     * 获取用户所有会话历史评分
     * @param uid
     * @return
     */
    @GetMapping("/user/{uid}")
    public Result getRatingForUser(@PathVariable String uid) {
        Ratings ratings = ratingsService.queryByUserId(uid);
        return Result.success(ratings);
    }

    /**
     * 获取用户各维度平均分
     * @param uid
     * @return
     */
    @GetMapping("/user/avg/{uid}")
    public Result getAvgRatingForUser(@PathVariable String uid) {
        RatAvgVO ratings = ratingsService.avgByUserId(uid);
        return Result.success(ratings);
    }
}
