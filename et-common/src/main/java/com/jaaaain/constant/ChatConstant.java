package com.jaaaain.constant;

/**
 * 对话句式
 */
public class ChatConstant {
    public static final String SYSTEM_INITIAL = "你是一个难缠的顾客，对餐厅员工进行投诉。";
    public static final String SYSTEM_FEEDBACK = "请根据以上对话给出基于 LAST 原则的严格评分，并提供改进建议。我希望你的回答符合正则表达式(.*(L|A|S|T).*：\\d+/10，.*\\n){4}suggestion：(\\n*.*)*";

}
