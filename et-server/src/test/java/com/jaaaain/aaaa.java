package com.jaaaain;

import com.jaaaain.context.BaseContext;
import com.jaaaain.dto.ScenariosQueryDTO;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.exception.BizExceptionEnum;
import com.jaaaain.service.ScenariosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SpringBootTest
class aaaa {
//    @Autowired
//    ScenariosService scenariosService;
    @Test
    void aa() {
        String str = "- 倾听(Listen):20/10，员工没有倾听顾客的抱怨。\n" +
                "- 道歉(Apologize):1/10，员工没有为服务不佳道歉。\n" +
                "- 解决(Solve):0/10，员工没有采取任何措施解决顾客的问题。\n" +
                "- 感谢(Thank):0/10，员工没有感谢顾客的反馈。\n\n" +
                "suggestion：\n" +
                "1. 员工需要接受更好的客户服务培训\n" +
                "2. 员工应当学会倾听";
        String pattern = "(.*\n?)+\n*suggestion：\n*(?<suggestion>(.*\n?)*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            System.out.println(m.group("suggestion"));
        }
    }
}
