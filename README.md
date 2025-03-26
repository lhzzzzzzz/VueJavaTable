# Vue + SpringBoot 用户管理系统

这是一个使用Vue 3和SpringBoot构建的用户管理系统，实现了用户数据的展示和管理功能。

## 项目结构

```
VueJavaTable/
├── frontend/          # Vue 3前端项目
└── backend/          # SpringBoot后端项目
```

## 前端部分 (frontend)

### 技术栈
- Vue 3
- Element Plus
- Axios
- Vite

### 功能特点
- 用户数据表格展示
- 实时搜索过滤
- 数据刷新功能
- 响应式设计
- 美观的UI界面

### 运行方式
1. 进入前端目录：
```bash
cd frontend
```

2. 安装依赖：
```bash
npm install
```

3. 启动开发服务器：
```bash
npm run dev
```

4. 访问地址：`http://localhost:5173`

## 后端部分 (backend)

### 技术栈
- SpringBoot 2.7.0
- Spring Data JPA
- MySQL
- Lombok

### 功能特点
- RESTful API设计
- 数据库集成
- 跨域支持
- 错误处理

### 数据库配置
- 数据库：MySQL
- 数据库名：for_demo
- 表名：user
- 表结构：
  - id (主键)
  - name (用户名)
  - password (密码)

### 运行方式
1. 进入后端目录：
```bash
cd backend
```

2. 使用Maven构建项目：
```bash
mvn clean install
```

3. 运行项目：
```bash
mvn spring-boot:run
```

4. API地址：`http://localhost:8080/api/users`

## 开发环境要求
- Node.js >= 16
- Java >= 8
- Maven >= 3.6
- MySQL >= 8.0

## 注意事项
1. 确保MySQL服务已启动
2. 检查数据库连接配置是否正确
3. 前端开发服务器默认端口为5173
4. 后端API服务器默认端口为8080 