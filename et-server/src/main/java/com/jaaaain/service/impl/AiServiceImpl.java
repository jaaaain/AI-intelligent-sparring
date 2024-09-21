package com.jaaaain.service.impl;

import com.jaaaain.constant.ChatConstant;
import com.jaaaain.properties.ChatProperties;
import com.jaaaain.service.AiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private ChatProperties chatProperties;

    public void Chat() {
        System.setProperty("http.proxyHost", chatProperties.getProxyHost());
        System.setProperty("http.proxyPort", chatProperties.getProxyPort());
        System.setProperty("https.proxyHost", chatProperties.getProxyHost());
        System.setProperty("https.proxyPort", chatProperties.getProxyPort());
        OpenAiService service = new OpenAiService(chatProperties.getOpenaiKey()); // 创建一个AI对话service
        List<ChatMessage> conversation_history = new ArrayList<>(Arrays.asList(
                new ChatMessage("system", ChatConstant.SYSTEM_INITIAL),
                new ChatMessage("user", ChatConstant.EMPLOYEE_GREETING),
                new ChatMessage("assistant", ChatConstant.TOO_SLOW)
        ));

        System.out.print("Customer: "+ ChatConstant.TOO_SLOW+"\nEmployee: ");  // 在终端打印顾客抱怨开场白
        Scanner scanner = new Scanner(System.in); // 获取回应输入
        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), scanner.nextLine()); // 将用户输入的第一条消息封装成一个 ChatMessage 对象，并将其加入到 conversation_history 列表中。
        conversation_history.add(firstMsg);

        while (true) {
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .messages(conversation_history)
                    .model(chatProperties.getModel())
                    .maxTokens(150)
                    .temperature(chatProperties.getTemperature())
                    .build();
            ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
            ChatMessage response = result.getChoices().get(0).getMessage();
            conversation_history.add(response);

            System.out.print("Customer: " + response.getContent()+"\nEmployee: ");
            String nextLine = scanner.nextLine();
            if (nextLine.equalsIgnoreCase("exit")) {
                break;
            }
            conversation_history.add(new ChatMessage(ChatMessageRole.USER.value(), nextLine));
        }

        System.out.println("对话结束，正在生成评分和反馈...");
        conversation_history.add(new ChatMessage("system",ChatConstant.SYSTEM_FEEDBACK));
        ChatCompletionRequest feedbackCompletionRequest = ChatCompletionRequest
                .builder()
                .messages(conversation_history)
                .model(chatProperties.getModel())
                .maxTokens(400)
                .temperature(chatProperties.getTemperature())
                .build();
        ChatCompletionResult result = service.createChatCompletion(feedbackCompletionRequest);
        ChatMessage response = result.getChoices().get(0).getMessage();
        conversation_history.add(response);
        System.out.print(response.getContent());
    }

//    private void GetResponse(List<ChatMessage> conversation_history){
//        while (true) {
//            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
//                    .builder()
//                    .messages(conversation_history)
//                    .model(chatProperties.getModel())
//                    .maxTokens(150)
//                    .temperature(chatProperties.getTemperature())
//                    .build();
//            ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
//            ChatMessage response = result.getChoices().get(0).getMessage();
//            conversation_history.add(response);
//
//            System.out.print("Customer: " + response.getContent()+"\nEmployee: ");
//            String nextLine = scanner.nextLine();
//            if (nextLine.equalsIgnoreCase("exit")) {
//                break;
//            }
//            conversation_history.add(new ChatMessage(ChatMessageRole.USER.value(), nextLine));
//        }
//    }

}
