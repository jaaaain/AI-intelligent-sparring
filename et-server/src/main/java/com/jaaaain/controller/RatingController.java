package com.jaaaain.controller;

import com.jaaaain.entity.Ratings;
import com.jaaaain.result.Result;
import com.jaaaain.service.RatingsService;
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

    @GetMapping("/{sid}")
    public Result getRatingForSession(@PathVariable("sid") String sessionId) {
        Ratings ratings = ratingsService.queryBySessionId(sessionId);
        return Result.success(ratings);
    }
}
