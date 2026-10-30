# JavaWeb AI Test Backend（ulias 项目）

学生信息管理系统的后端服务，基于 Spring Boot 3 + MyBatis + MySQL 构建，采用 Maven 多模块结构，为前端项目 [java-web-ai-test-fronttt](https://github.com/snsjkswk-afk/java-web-ai-test-fronttt) 提供 REST 接口。

- 统一响应体（`code` / `msg` / `data`）+ 全局异常处理
- JWT 登录鉴权（请求头 `token`），拦截器统一校验
- AOP 操作日志：注解 `@Logoperation` 标注的方法自动写入 `operate_log` 表
- 阿里云 OSS 文件上传，AccessKey 通过环境变量注入，不落仓库
- PageHelper 分页，支持员工、班级、学生等列表的多条件查询

## 功能模块

| 模块 | 接口前缀 | 主要能力 |
|------|----------|----------|
| 登录鉴权 | `/login` | 账号密码登录，签发 JWT |
| 员工管理 | `/emps` | 分页条件查询、新增、详情（含工作经历）、修改、删除 |
| 部门管理 | `/depts` | 部门列表、新增、修改、删除 |
| 班级管理 | `/clazzs` | 班级分页查询（按名称、班主任、学科、结课时间筛选）、增删改查 |
| 学生管理 | `/students` | 分页查询、增删改查；违纪登记、扣分、撤销处分、违纪记录查询 |
| 数据统计 | `/report` | 员工职位分布、性别分布、学员学历分布、班级人数统计 |
| 操作日志 | `/log` | 操作日志分页查询 |
| 文件上传 | `/upload` | 图片上传至阿里云 OSS，返回访问 URL |

## 技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 语言 / 运行时 | Java | 17 |
| 框架 | Spring Boot（Web / AOP） | 3.5.14 |
| 持久层 | MyBatis Spring Boot Starter | 3.0.3 |
| 分页 | PageHelper | 2.1.0 |
| 数据库 | MySQL（`mysql-connector-j`） | 8.x |
| 鉴权 | JJWT | 0.9.1 |
| 对象存储 | 阿里云 OSS SDK | 3.18.4 |
| 工具 | Lombok、HttpClient | 1.18.x / 4.5.14 |
| 构建 | Maven（多模块） | 3.6+ |

## 项目结构

```
web-ai-project01
├── ulias-parent          # 父工程：Spring Boot 版本、依赖版本统一管理
├── ulias-pojo            # 实体与查询参数（Emp、Dept、Clazz、Student、ClassQueryParam 等）
├── ulias-utils           # 通用工具：JWT（Jutis）、阿里云 OSS 上传（AliOssUtil）
└── demo                  # Web 层：Controller / Service / Mapper / AOP / 拦截器 / 配置
    └── src/main/
        ├── java/com/example/
        │   ├── controller/   # 接口层
        │   ├── service/      # 业务层（impl 为实现）
        │   ├── mapper/       # MyBatis Mapper 接口
        │   ├── pojo/         # 请求/响应对象
        │   ├── aop/          # 操作日志切面、耗时统计
        │   ├── intercepter/  # JWT 登录拦截器
        │   └── config/       # WebMvc 配置（拦截器注册）
        └── resources/
            ├── application.yml
            └── com/example/mapper/   # MyBatis XML 映射
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.x（本地 3306）
- 可选：阿里云 OSS 账号（仅文件上传功能需要）

### 2. 准备数据库

```sql
CREATE DATABASE empp DEFAULT CHARACTER SET utf8mb4;
```

导入建表脚本与初始数据（建表语句与测试数据整理后放在 `db/` 目录，随数据库作业一并提交），涉及表：

`emp`、`dept`、`emp_expr`、`emp_log`、`clazz`、`student`、`student_violation_record`、`operate_log`

### 3. 配置数据源

编辑 `demo/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/empp
    username: root
    password: 你的密码
```

### 4. 配置 OSS 密钥（环境变量，不要写进配置文件）

`application.yml` 中读取的是环境变量，仓库里没有真实密钥：

```yaml
aliyun:
  oss:
    accessKeyId: ${ALIYUN_OSS_ACCESS_KEY_ID:}
    accessKeySecret: ${ALIYUN_OSS_ACCESS_KEY_SECRET:}
```

IDEA 中在 `Run/Debug Configurations → Environment variables` 填入：

```
ALIYUN_OSS_ACCESS_KEY_ID=你的AccessKeyId;ALIYUN_OSS_ACCESS_KEY_SECRET=你的AccessKeySecret
```

命令行启动时：

```bash
export ALIYUN_OSS_ACCESS_KEY_ID=xxx
export ALIYUN_OSS_ACCESS_KEY_SECRET=yyy
```

> 不配置也能启动，仅 `/upload` 接口会失败，其余功能不受影响。

### 5. 启动

```bash
git clone https://github.com/snsjkswk-afk/java-web-ai-test-back.git
cd web-ai-project01
mvn clean package -DskipTests
java -jar demo/target/demo-0.0.1-SNAPSHOT.jar
```

或在 IDEA 中运行 `demo/src/main/java/com/example/DemoApplication.java`。

服务默认端口 **8080**，无 context-path。前端开发服务器通过 `/api` 代理到该地址。

## 接口约定

### 统一响应体

```json
{ "code": 1, "msg": "success", "data": {} }
```

`code = 1` 表示成功，`0` 表示业务失败（`msg` 为提示信息）。

### 鉴权

除 `/login/**` 外，所有接口需在请求头携带登录返回的 JWT：

```
token: eyJhbGciOiJIUzI1NiJ9...
```

拦截器校验失败返回 HTTP 401，前端据此跳转登录页。

### 常用接口示例

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/login` | 登录，返回 `{ id, username, name, token }` |
| GET | `/emps?page=1&pageSize=10&name=&gender=&begin=&end=` | 员工分页查询 |
| POST | `/emps` | 新增员工（含工作经历列表） |
| GET | `/clazzs/list?name=&masterName=&subject=` | 班级下拉 / 条件筛选 |
| GET | `/report/empsJobData` | 员工职位分布（图表数据） |
| GET | `/log/page?page=1&pageSize=10` | 操作日志分页 |
| POST | `/upload` | `multipart/form-data`，字段名 `file`，返回图片 URL |

## 开发约定

- 提交信息遵循 Conventional Commits：`feat` / `fix` / `style` / `chore` / `docs`，格式 `类型(范围): 简述`
- 构建产物与 IDE 配置不入库：`target/`、`*.iml`、`.idea/` 已在 `.gitignore` 中忽略
- 密钥、账号密码等敏感配置一律走环境变量，禁止提交到仓库
- 新增 SQL 请在 `resources/com/example/mapper/` 下维护对应 XML，保持与 Mapper 接口一致

## 相关仓库

- 前端：https://github.com/snsjkswk-afk/java-web-ai-test-fronttt （Vue 3 + Vite + Element Plus）

## 说明

本项目为课程作业，用于演示 Spring Boot 三层架构、JWT 鉴权、AOP 日志与前后端分离联调，未做生产级的高并发与安全加固。
