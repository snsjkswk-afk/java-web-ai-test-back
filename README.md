# JavaWeb AI Test Backend (ulias 项目)

这是一个基于 JavaWeb 技术栈开发的简易后端项目，采用 Maven 多模块架构进行构建，旨在提供清晰、规范的后端 API 接口服务。

## 📂 项目结构

本项目采用 Maven 多模块（Multi-module）架构，各模块职责划分清晰：

*   `ulias-parent`：Maven 父工程，统一管理依赖版本（如 Spring Boot、数据库驱动等）和插件配置。
*   `ulias-pojo`：实体类模块（POJO），存放与数据库表对应的实体类、DTO（数据传输对象）及 VO（视图对象）。
*   `ulias-utils`：工具类模块，存放项目中通用的工具方法（如 JWT 鉴权、日期处理、统一响应结果封装等）。
*   `demo`：核心业务模块/Web 层，包含 Controller、Service、Mapper 等核心业务代码与接口路由。
*   `.idea`：IDEA 项目配置目录（已加入 `.gitignore` 忽略提交，此处为历史遗留）。

## 🛠️ 技术栈

*   **开发语言**：Java (JDK 1.8+)
*   **构建工具**：Maven
*   **后端框架**：Spring Boot / Spring MVC (根据实际情况调整)
*   **数据库**：MySQL (请根据实际情况修改)
*   **持久层框架**：MyBatis / MyBatis-Plus (请根据实际情况修改)

## 🚀 本地运行指南

1.  **环境准备**：
    *   确保本地已安装 JDK 17 或更高版本。
    *   确保本地已安装 Maven 并配置好环境变量。
    *   本地启动 MySQL 数据库，并导入相应的 SQL 脚本（如果有的话）。
2.  **克隆项目**：
    ```bash
    git clone https://github.com/snsjkswk-afk/java-web-ai-test-back.git
