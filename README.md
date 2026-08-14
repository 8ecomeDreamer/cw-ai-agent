# shaolin‑soccer‑ai‑agent

>
> Spring‑AI Agent 沉浸式剧情项目｜基于《少林足球》文字互动游戏，实战学习 Spring‑AI 全套 Agent 能力
> 开发周期：14 天单人开发
> 技术栈：SpringBoot3.x + Spring‑AI + JDK17

## 项目介绍

本项目搭建沉浸式 AI‑Agent 互动程序，AI 角色为电影主角**阿星（五师兄、大力金刚腿）**。
用户作为阿星的搭档，跟随原版电影完整主线开启冒险：废品谋生、偶遇落魄酒鬼「黄金右脚」明锋、寻访五位落魄的少林同门、组建少林足球队、一路闯关，最终迎战服用禁药的魔鬼队，争夺超级杯冠军。

本项目属于学习导向工程，所有游戏功能用来循序渐进实操 Spring‑AI 核心知识点：基础对话、SSE 流式输出、会话记忆、Tool 工具调用、RAG 知识库、任务规划 Agent、结构化实体返回。

## ✨ 项目亮点

1. **严格复刻原版电影设定**
   完整收录人物履历、时间线、经典台词、师兄弟身世、武功球场定位；内置 RAG 本地知识库，约束 AI 固定为热血无厘头的阿星，不会错认身份充当球队教练。
2. Tool 工具驱动全部剧情
   依靠`@Tool`函数调用机制，AI 自动触发偶遇明锋、招募师兄弟、馒头摊打工、球队特训、足球赛事、隐藏彩蛋等剧情。
3. 多玩家独立游戏存档
   通过 session‑id 隔离每一位使用者，Agent 记忆当前进度、队员列表、已经触发过的剧情事件。
4. SSE 流式打字机对话效果，模拟真人聊天输出

## 📁 项目目录结构

```
com.example.CWAIAgent
├── config                # Spring‑AI配置、ChatClient、记忆、向量库配置
├── controller            # HTTP聊天接口、SSE流式对话接口
├── prompt                # 存放全部提示词模板
├── agent                 # Agent核心模块
│   ├── tools             # @Tool 游戏剧情工具集合
│   ├── planner           # 任务规划器
│   └── memory            # 会话记忆管理
├── rag                   # RAG少林足球知识库
│   ├── document
│   ├── vector
│   └── service
├── entity                # 球员、球队、对战结果、游戏进度实体
├── service               # 游戏业务、战力计算、胜负判定、随机事件
├── util                  # 通用工具类
└── CwAiAgentApplication # SpringBoot启动入口
```

### resources 资源文件夹

```
resources
├── prompts
│   └── star‑coach‑system.txt     # 阿星角色人设Prompt
├── rag‑data
│   └── shaolin‑soccer‑story.txt  # 电影剧情、人物、台词知识库
├── application.yml
└── application‑local.yml          # 本地环境、大模型密钥配置
```

## 📚 可习得 Spring‑AI Agent 技能清单

1. ChatClient 同步对话 + SSE 流式打字机输出
2. 外部 txt 文件加载 `ResourcePromptTemplate` 提示词模板
3. ChatMemory 上下文会话记忆、多玩家 session 隔离、Redis 持久化存档
4. `@Tool` 工具函数调用，Agent 操控 Java 业务推进游戏剧情
5. RAG 检索增强：文档加载、文本切片、Embedding 向量、内存向量库
6. SequentialPlanner 任务拆解，一句自然语言自动执行整套任务链
7. BeanOutputParser 结构化 JSON 输出，返回值自动映射 Java 实体

## 🚀 环境启动教程

### 前置依赖

- JDK 17+
- Maven
- 可用的大模型 API‑Key（通义千问、DeepSeek 等兼容 Spring‑AI 即可）

### 本地配置

修改 `application-local.yml`，填入你的模型密钥

```
spring:
  ai:
    dashscope:
      api-key: 你的密钥
```

### 运行项目

启动主启动类 `CwAiAgentApplication`
默认服务端口：`8080`

### 接口测试

#### 1、普通对话接口

- 请求地址：`http://127.0.0.1:8080/api/shaolin/chat`
- 请求方式：`POST`
- 请求体：`你是谁`

#### 2、SSE 流式聊天接口

```
GET http://127.0.0.1:8080/api/shaolin/streamChat?msg=开启我的冒险&sessionId=user001
```

>
> sessionId 用来区分不同玩家，实现独立游戏存档

## 🎮 游戏使用示例

直接发送自然语言指令驱动主线剧情，示例指令：

```
开启我的少林足球故事
前去偶遇酒鬼明锋
劝说明锋担任球队教练
招募大师兄铁头功入伙
全队开启功夫特训
和霸王队开启足球比赛
```

## 📅 14 天单人开发计划表

表格

| 天数 | 开发任务 |
| --- | --- |
| Day1‑2 | 搭建 SpringBoot+Spring‑AI 环境、配置阿星人设、调试流式聊天接口 |
| Day2‑3 | 接入 ChatMemory 会话记忆，实现独立游戏存档 |
| Day3‑7 | 开发全部 @Tool 剧情工具，打通 Agent 函数调用、搭建游戏闭环 |
| Day7‑9 | 搭建 RAG 知识库，约束 AI 人设和原版电影剧情 |
| Day9‑11 | 接入任务规划器，支持一次性下达多步任务 |
| Day11‑12 | 配置结构化输出，对战数据自动映射实体类 |
| Day12‑13 | 编写极简前端聊天页面，对接 SSE 流式接口 |
| Day14 | 功能调试、补齐彩蛋剧情、完整通关测试、成品交付 |

## ⚠️ 开发规范

1. 前期开发优先使用 `InMemoryChatMemory`、`SimpleVectorStore` 内存组件；全部功能调试完毕后再升级 Redis、专业向量数据库
2. 战力数值、比赛胜负、随机事件交由 Java 代码运算；AI 只负责剧情对话、调度工具，规避大模型不稳定问题
3. 角色 Prompt、电影知识库统一放在 resources 文本，修改人设无需改动 Java 代码
4. `.idea`、本地配置文件已经录入 gitignore，禁止提交 IDE 配置、密钥信息

## 📈 后续拓展方向

1. 接入文生图接口，生成球员、赛场海报
2. 双 Agent 切换对话：阿星 / 教练明锋
3. 扩充支线隐藏剧情
4. Vue 前端页面、球员可视化面板

## 📄 开源协议

本项目仅用于个人 Spring‑AI 技术学习，禁止商用。