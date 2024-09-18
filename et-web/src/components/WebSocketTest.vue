<template>
  <div>
    <label for="options">请选择训练模式:</label>
    <select v-model="selectedOption" id="options">
      <option value="1">模式1</option>
      <option value="2">模式2</option>
      <option value="3">模式3</option>
      <option value="4">模式4</option>
    </select>
    <button @click="startTrain">开始训练</button>
    
    <input v-model="messageContent" placeholder="Type a message...">
    <button @click="sendMessage">send</button>

    <div v-for="(message,index) in messages" :key="index">
      <p><strong>{{ message.sender }}:</strong> {{ message.msg }}</p>
    </div>
  </div>
</template>

<script>
import { parse } from 'ipaddr.js';
export default {
  data() {
    return {
      userid: 1,
      username:"小张",
      selectedOption:1,
      messages: [],
      messageContent: '',
      websocket: null
    };
  },
  mounted() {
  },
  methods: {
    startTrain(){
      this.connect(this.selectedOption,this.userid);

    },
    connect(selectedOption, userid) {
    let socketUrl = `ws://localhost:8080/api/websocket/conversation/${userid}?option=${selectedOption}`;
    this.websocket = new WebSocket(socketUrl);

    // 使用箭头函数确保 `this` 指向 Vue 实例
    this.websocket.onmessage = (message) => {
      console.log(message.data);
      console.log(JSON.stringify(this.messages));
      this.onMessageReceived(message);
    };
  },
  
  onMessageReceived(message) {
    console.log("ok");
    try {
      const parsedMessage = JSON.parse(message.data); // 解析 JSON 字符串
      this.messages.push(parsedMessage); // 将解析后的消息加入 messages 数组
    } catch (e) {
      console.error("消息解析失败: ", e);
    }
  },
    sendMessage() {
      if (this.messageContent.trim()) {
        const message = {
          sender: this.username,
          msg: this.messageContent
        };
        this.websocket.send(JSON.stringify(message)); // websocket 发送消息
        this.messages.push(message); // 消息历史
        console.log(JSON.stringify(this.messages));
        this.messageContent = ''; // 清空输入框
      }
    }
  }
};
</script>
