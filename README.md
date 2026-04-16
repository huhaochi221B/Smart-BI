# Smart BI 后端系统

基于 Spring Boot 3 + MyBatis-Plus + AI 的智能图表分析后端系统，支持用户上传数据、AI 自动生成图表和分析结论。

## 📋 项目简介

Smart BI 是一个智能化的商业智能分析平台后端服务，通过集成大语言模型（AI），能够根据用户上传的数据和分析目标，自动生成可视化图表和分析结论。系统采用异步任务处理、消息队列、限流等机制，确保高并发场景下的稳定性和可靠性。

### 核心功能

- **用户管理**：注册、登录、权限控制（普通用户/管理员）
- **图表管理**：创建、查询、删除图表，支持分页和条件筛选
- **智能分析**：基于 AI 自动生成图表配置和分析结论
- **异步处理**：使用线程池和 RabbitMQ 实现异步任务处理
- **限流保护**：基于 Redis + Redisson 的分布式限流
- **文件上传**：支持 Excel 文件上传和数据解析
- **对象存储**：集成腾讯云 COS 进行文件存储
- **接口文档**：集成 Knife4j 提供在线 API 文档

## 🛠️ 技术栈

### 后端框架
- **Spring Boot 3.2.8** - 核心框架
- **Java 17** - 开发语言
- **MyBatis-Plus 3.5.6** - ORM 框架
- **Spring AOP** - 面向切面编程

### 数据存储
- **MySQL 8.0.33** - 关系型数据库
- **Redis** - 缓存和会话管理
- **Elasticsearch** - 搜索引擎（可选）

### 消息队列
- **RabbitMQ** - 异步任务处理

### AI 集成
- **Spring AI 1.0.0-M6** - AI 框架
  - OpenAI API（阿里云通义千问）
  - Ollama（本地大模型）

### 工具库
- **Lombok 1.18.30** - 简化代码
- **Hutool 5.8.8** - Java 工具类库
- **Apache Commons Lang3** - 常用工具类
- **EasyExcel 3.1.1** - Excel 处理
- **Knife4j 4.4.0** - API 文档（Swagger3 增强）

### 第三方服务
- **腾讯云 COS** - 对象存储
- **微信公众平台** - 微信集成（可选）

### 其他
- **FreeMarker** - 模板引擎（代码生成）
- **Redisson 3.21.3** - Redis 客户端
- **Docker** - 容器化部署

## 📁 项目结构

```
Smart-BI-backend/
├── src/main/java/com/yupi/springbootinit/
│   ├── annotation/          # 自定义注解
│   │   └── AuthCheck.java   # 权限校验注解
│   ├── aop/                 # 切面
│   │   ├── AuthInterceptor.java    # 权限拦截器
│   │   └── LogInterceptor.java     # 日志拦截器
│   ├── bizmq/               # 业务消息队列
│   │   ├── BiInitMain.java         # MQ 初始化
│   │   ├── BiMessageConsumer.java  # 消息消费者
│   │   ├── BiMessageProducer.java  # 消息生产者
│   │   └── BiMqConstant.java       # MQ 常量
│   ├── common/              # 通用类
│   │   ├── BaseResponse.java       # 统一响应
│   │   ├── ChartStatus.java        # 图表状态枚举
│   │   ├── DeleteRequest.java      # 删除请求
│   │   ├── ErrorCode.java          # 错误码
│   │   ├── PageRequest.java        # 分页请求
│   │   └── ResultUtils.java        # 结果工具类
│   ├── config/              # 配置类
│   │   ├── ChatConfig.java         # AI 聊天配置
│   │   ├── CorsConfig.java         # 跨域配置
│   │   ├── CosClientConfig.java    # COS 配置
│   │   ├── JsonConfig.java         # JSON 配置
│   │   ├── MyBatisPlusConfig.java  # MyBatis-Plus 配置
│   │   ├── RedissonConfig.java     # Redisson 配置
│   │   ├── ThreadPoolExecutorConfig.java  # 线程池配置
│   │   └── WxOpenConfig.java       # 微信配置
│   ├── constant/            # 常量定义
│   ├── controller/          # 控制器层
│   │   ├── ChartController.java    # 图表接口
│   │   ├── FileController.java     # 文件接口
│   │   ├── UserController.java     # 用户接口
│   │   ├── PostController.java     # 帖子接口
│   │   ├── QueueController.java    # 队列监控接口
│   │   └── ...
│   ├── exception/           # 异常处理
│   │   ├── BusinessException.java       # 业务异常
│   │   ├── GlobalExceptionHandler.java  # 全局异常处理器
│   │   └── ThrowUtils.java             # 异常抛出工具
│   ├── manager/             # 管理器
│   │   ├── CosManager.java            # COS 管理器
│   │   └── RedisLimiterManager.java   # 限流管理器
│   ├── mapper/              # MyBatis Mapper
│   ├── model/               # 数据模型
│   │   ├── dto/             # 数据传输对象
│   │   ├── entity/          # 实体类
│   │   ├── enums/           # 枚举类
│   │   └── vo/              # 视图对象
│   ├── service/             # 服务层
│   │   ├── impl/            # 服务实现
│   │   ├── ChartService.java        # 图表服务
│   │   ├── UserService.java         # 用户服务
│   │   └── ...
│   ├── utils/               # 工具类
│   │   ├── ExcelUtils.java          # Excel 工具
│   │   ├── SqlUtils.java            # SQL 工具
│   │   └── ...
│   ├── job/                 # 定时任务
│   ├── esdao/               # Elasticsearch DAO
│   └── MainApplication.java # 启动类
├── src/main/resources/
│   ├── mapper/              # MyBatis XML 映射文件
│   ├── templates/           # FreeMarker 模板
│   ├── application.yml      # 主配置文件
│   ├── application-dev.yml  # 开发环境配置
│   ├── application-prod.yml # 生产环境配置
│   └── application-test.yml # 测试环境配置
├── sql/                     # SQL 脚本
│   ├── create_table.sql     # 建表语句
│   └── post_es_mapping.json # ES 映射配置
├── doc/                     # 文档
├── Dockerfile               # Docker 构建文件
├── pom.xml                  # Maven 配置
└── README.md                # 项目说明
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.x（可选，用于异步任务）

### 安装步骤

#### 1. 克隆项目

```bash
git clone https://github.com/your-username/Smart-BI-backend.git
cd Smart-BI-backend
```

#### 2. 数据库初始化

执行 `sql/create_table.sql` 文件创建数据库和表：

```bash
mysql -u root -p < sql/create_table.sql
```

#### 3. 配置修改

修改 `src/main/resources/application.yml` 中的配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yubi
    username: your_username
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password
  
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

  ai:
    openai:
      api-key: ${OPENAI_API_KEY}  # 设置你的 API Key
```

#### 4. 编译运行

```bash
# 编译项目
mvn clean package -DskipTests

# 运行项目
java -jar target/Smart-BI-backend-0.0.1-SNAPSHOT.jar
```

或使用 Maven 直接运行：

```bash
mvn spring-boot:run
```

#### 5. 访问应用

- **API 接口**: http://localhost:8080/api
- **接口文档**: http://localhost:8080/api/doc.html

## 📊 核心业务流程

### 图表生成流程

1. **用户上传数据**：上传 Excel 文件或输入 CSV 数据
2. **设定分析目标**：描述想要分析的维度和指标
3. **提交分析任务**：
   - 同步模式：直接调用 AI 生成（适合低频场景）
   - 异步模式：发送到消息队列处理（适合高频场景）
4. **AI 分析处理**：
   - 解析数据结构
   - 生成 ECharts 配置
   - 生成分析结论
5. **返回结果**：保存图表配置和结论到数据库
6. **前端展示**：使用 ECharts 渲染图表

### 异步处理架构

```
用户请求 → 限流检查 → 创建任务记录 → 发送MQ消息 → 返回任务ID
                                    ↓
                          消费者接收消息
                                    ↓
                          调用AI生成图表
                                    ↓
                          更新任务状态和结果
```

## 🔑 API 接口说明

### 用户接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户注册 | POST | /api/user/register | 用户注册 |
| 用户登录 | POST | /api/user/login | 用户登录 |
| 获取当前用户 | GET | /api/user/get/login | 获取登录用户信息 |
| 用户登出 | POST | /api/user/logout | 用户登出 |

### 图表接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 创建图表 | POST | /api/chart/add | 创建图表记录 |
| 删除图表 | POST | /api/chart/delete | 删除图表 |
| 更新图表 | POST | /api/chart/update | 更新图表（仅管理员） |
| 获取图表 | GET | /api/chart/get/vo | 根据 ID 获取图表 |
| 分页查询 | POST | /api/chart/list/page | 分页获取图表列表 |
| 我的图表 | POST | /api/chart/my/list/page | 获取当前用户的图表 |
| 智能生成（同步） | POST | /api/chart/gen | 同步生成图表 |
| 智能生成（异步） | POST | /api/chart/gen/async | 异步生成图表 |
| 重试生成 | POST | /api/chart/gen/retry | 重新生成失败的图表 |

### 文件接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 文件上传 | POST | /api/file/upload | 上传文件到 COS |

## ⚙️ 配置说明

### 环境变量

| 变量名 | 说明 | 必填 |
|--------|------|------|
| OPENAI_API_KEY | AI API 密钥 | 是 |

### 主要配置项

#### 数据库配置
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/yubi
    username: root
    password: 123456
```

#### Redis 配置
```yaml
spring:
  data:
    redis:
      database: 1
      host: localhost
      port: 6379
      timeout: 5000
      password: your_password
```

#### RabbitMQ 配置
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

#### AI 配置
```yaml
spring:
  ai:
    openai:
      base-url: https://dashscope.aliyuncs.com/compatible-mode
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: qwen-max
          temperature: 0.7
```

## 🐳 Docker 部署

### 构建镜像

```bash
docker build -t smart-bi-backend .
```

### 运行容器

```bash
docker run -d \
  -p 8080:8080 \
  -e OPENAI_API_KEY=your_api_key \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host:3306/yubi \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  --name smart-bi-backend \
  smart-bi-backend
```

## 🔐 权限控制

系统使用自定义注解 `@AuthCheck` 实现权限控制：

```java
// 仅管理员可访问
@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
@PostMapping("/update")
public BaseResponse<Boolean> updateChart(...) {
    // ...
}

// 需要登录
@AuthCheck
@GetMapping("/get/vo")
public BaseResponse<Chart> getChartById(...) {
    // ...
}
```

## 📈 性能优化

- **异步处理**：使用线程池和 RabbitMQ 处理耗时任务
- **分布式限流**：基于 Redis + Redisson 实现滑动窗口限流
- **数据库索引**：为常用查询字段添加索引
- **逻辑删除**：使用 MyBatis-Plus 逻辑删除，避免物理删除
- **连接池**：使用 HikariCP 数据库连接池

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行指定测试类
mvn test -Dtest=ChartMapperTest
```

## 📝 开发规范

- 使用 Lombok 简化代码
- 统一使用 `BaseResponse` 封装返回结果
- 异常统一由 `GlobalExceptionHandler` 处理
- 使用 `ThrowUtils` 进行参数校验
- 遵循 RESTful API 设计规范

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 开源协议

本项目遵循 MIT 协议开源。



## 🔗 相关链接

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis-Plus 官方文档](https://baomidou.com/)
- [Spring AI 官方文档](https://spring.io/projects/spring-ai)
- [Knife4j 文档](https://doc.xiaominfo.com/)

## ⭐ Star History

如果这个项目对你有帮助，请给个 Star 支持一下！

---

**注意**：本项目仅供学习交流使用，请勿用于商业用途。
