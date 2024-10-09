package com.jaaaain.service;

public interface AiService {

    String newOpenAiService(String sid, Integer option, Integer uid);

    String getResponse(String sid, String message);

    String getFeedBack(String sid);
}
