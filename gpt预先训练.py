import random
import os
from openai import OpenAI
# 设置代理 URL 和端口
proxy_url = 'http://127.0.0.1'
proxy_port = '10809'

# 设置 http_proxy 和 https_proxy 环境变量
os.environ['http_proxy'] = f'{proxy_url}:{proxy_port}'
os.environ['https_proxy'] = f'{proxy_url}:{proxy_port}'

# 使用API密钥创建OpenAI客户端实例
client = OpenAI(api_key='')
def get_gpt4_response(conversation_history):
    response = client.chat.completions.create(
        model="gpt-4",  # 使用GPT-4模型
        messages=conversation_history,
        max_tokens=150,
        n=1,
        stop=None,
        temperature=0.7
    )
    # 正确访问消息内容
    return response.choices[0].message.content.strip()


def get_gpt4_feedback(conversation_history):
    # 将评分请求添加到对话历史中
    conversation_history.append({
        "role": "system",
        "content": "请根据以上对话给出基于 LAST 原则的评分，并提供改进建议。"
    })

    # 获取评分结果
    feedback_response = client.chat.completions.create(
        model="gpt-4",
        messages=conversation_history,
        max_tokens=300,  # 可调整生成的反馈字数
        n=1,
        stop=None,
        temperature=0.7
    )

    return feedback_response.choices[0].message.content.strip()

def start_complaint_simulation():
    complaints = [
        "你们餐厅怎么回事？我刚才在你们这里买的餐点里面居然有虫卵！太恶心了！你们怎么解释？",
        "你们的服务太差了！我等了半小时才上菜，这还算是服务吗？",
        "你们的食物不新鲜，我吃了一口就觉得不对劲，现在肚子很不舒服！",
        "你们的餐厅环境太脏了，桌子上还有食物残渣，怎么吃饭？",
        "你们的服务员态度非常恶劣，完全没有礼貌！",
        "我点的外卖居然送错了，怎么办？",
        "你们的饮料里居然有一根头发，太恶心了！",
        "我经过餐区过道时摔倒在地，你们的地面太滑了，谁来负责？"
    ]

    initial_complaint = random.choice(complaints)

    conversation_history = [
        {"role": "system", "content": "你是一个难缠的顾客，对餐厅员工进行投诉。"},
        {"role": "admin", "content": "您好，欢迎光临。请问有什么可以帮您的？"},
        {"role": "assistant", "content": initial_complaint}  # 将 initial_complaint 设置为 assistant 的角色
    ]

    print("开始模拟难缠的投诉顾客，请输入您的回复（输入 'exit' 结束对话）")
    print(f"Customer: {initial_complaint}")

    while True:
        employee_response = input("Employee: ")
        if employee_response.lower() == 'exit':
            break
        conversation_history.append({"role": "admin", "content": employee_response})

        customer_response = get_gpt4_response(conversation_history)
        conversation_history.append({"role": "assistant", "content": customer_response})
        print(f"Customer: {customer_response}")
  # 对话结束后调用评分功能
    print("\n对话结束，正在生成评分和反馈...\n")
    feedback = get_gpt4_feedback(conversation_history)
    print(f"评分与反馈:\n{feedback}")



if __name__ == "__main__":
    start_complaint_simulation()
