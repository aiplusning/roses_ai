# AGENTS.md

## 角色定位

你是 `roses_ai` 项目的代码生成 agent。你的职责不是“随手写代码”，而是严格按照本仓库��工程规范、模块划分和通用设计风格，生成高质量、可直接落地的 Java 代码。

你必须遵守以下规则：
- 优先保持与现有代码风格一致
- 优先兼容当前 Maven 多模块结构
- 优先使用 Spring Boot 3 + Java 17
- 优先保持中文注释和项目语义一致
- 优先遵守当前模块的异常、响应、枚举、POJO 设计规范

## 项目背景

这个仓库是一个 Maven 多模块 Java 工程，采用 `roses-kernel` 作为父工程，模块命名以 `kernel-` 开头：

- `kernel-a-rule`
- `kernel-d-ai-reactor`
- `kernel-d-auth`
- `kernel-d-cache`
- `kernel-d-config`
- `kernel-d-db`
- `kernel-d-email`
- `kernel-d-event`
- `kernel-d-file`
- `kernel-d-groovy`
- `kernel-d-i18n`
- `kernel-d-jwt`
- `kernel-d-log`
- `kernel-d-office`
- `kernel-d-pinyin`
- `kernel-d-scanner`
- `kernel-d-security`
- `kernel-d-sms`
- `kernel-d-timer`
- `kernel-d-tree`
- `kernel-d-validator`
- `kernel-d-wrapper`

根模块的 Maven 配置说明：
- `groupId`: `com.javaguns.roses`
- `artifactId`: `roses-kernel`
- `java.version`: `17`
- Spring Boot Parent: `3.2.10`
- 使用 Lombok、MyBatis-Plus、Hutool、Fastjson 等通用依赖

## 代码风格规范

### 1. 统一包结构

所有 Java 代码应遵循如下结构：

- `cn.stylefeng.roses.kernel.rule.*` ：基础规则层
- `cn.stylefeng.roses.kernel.ai.reactor.*` ：AI 交互与流式响应层
- 其他模块统一按 `cn.stylefeng.roses.kernel.<模块名>.*` 组织

如需创建新类：
- 优先放在模块对应的 `pojo` / `service` / `dao` / `controller` / `enums` / `exception` / `constants` / `context` 等目录
- 保持包名和目录结构一一对应

### 2. 对象和 POJO 规范

- 优先使用 Lombok：`@Data`, `@Getter`, `@Setter`, `@EqualsAndHashCode`
- 所有 DTO / VO / Response / Request 默认使用 `@Data`
- 对字段添加 `@ChineseDescription`，用于资源扫描和文档生成
- 需要序列化的对象尽量显式设计 `serialVersionUID`（如基础类）

### 3. 异常规范

所有业务异常均按以下方式处理：
- 业务异常统一使用 `ServiceException`
- 所有异常枚举实现 `AbstractExceptionEnum`
- 异常枚举按类型分类：
  - `UserExceptionEnum`
  - `BusinessExceptionEnum`
  - `ThirdExceptionEnum`
- 每个模块应维护自己的模块异常集合，不要混用全局异常

参考：
- `kernel-a-rule/.../exception/base/ServiceException.java`
- `kernel-a-rule/.../exception/AbstractExceptionEnum.java`

### 4. 响应封装规范

统一使用项目中的通用返回结构：
- `ResponseData<T>`
- `BaseResponse`
- `SuccessResponseData`
- `ErrorResponseData`

通用约定：
- `success` 表示请求是否成功
- `code` 表示返回码
- `message` 表示提示语
- `data` 表示业务数据

### 5. 枚举规范

- 所有枚举如果要参与接口或读值场景，优先实现 `ReadableEnum<T>`
- 若用于 MyBatis-Plus / JSON 反射，使用 `@EnumValue`、`@JsonValue`、`@JsonCreator`
- 取值必须有 `key` 和 `name`，符合 “编码 + 可读描述” 的设计

参考：
- `kernel-d-ai-reactor/.../enums/FluxResponseTypeEnum.java`
- `kernel-a-rule/.../base/ReadableEnum.java`

### 6. AI 流式响应规范

AI 模块中使用流式返回的场景，应按如下流程：
- 使用 `FluxResponse` 包装响应
- 使用 `FluxResponseTypeEnum` 表示状态
- 使用 `ChatStreamConstants.CHAT_STREAM_FINISH_SIGNAL` 作为结束标识
- 工具方法：`thinking`, `success`, `error`, `attachment`, `sinkNext`, `sinkSuccess`, `sinkError`

参考：
- `kernel-d-ai-reactor/.../pojo/FluxResponse.java`
- `kernel-d-ai-reactor/.../enums/FluxResponseTypeEnum.java`
- `kernel-d-ai-reactor/.../constants/ChatStreamConstants.java`

## 生成代码的基本原则

### 必须遵循

1. 先理解模块职责，再生成代码
2. 先看现有类的命名和注释风格，再统一输出
3. 使用当前项目包名和目录结构，不要硬编码无关包
4. 生成代码时优先兼容 Maven、Spring Boot、MyBatis-Plus 生态
5. 生成类时优先保证：可编译、可扩展、可读、可维护
6. 保持中文注释、清晰结构和最小依赖

### 禁止事项

- 不要引入与当前项目不一致的代码风格
- 不要在常规业务代码中直接使用 `System.out.println`
- 不要绕过 `ServiceException` 和项目返回封装
- 不要在异常处理中随意吞掉错误
- 不要在无必要时引入第三方依赖
- 不要破坏 Maven 多模块的层次关系

## 代码生成模板要求

当生成新业务代码时，采用以下脚手架结构：

- `controller`：HTTP 接口入口
- `service`：业务逻辑
- `service/impl`：实现类
- `dao` / `mapper`：持久层接口
- `entity` / `pojo`：实体或数据对象
- `enums`：枚举
- `exception`：模块异常
- `constants`：常量
- `context`：上下文或配置对象

对每个新类，必须保证：
- 有清晰的类注释
- 使用中文注释描述职责
- 有明确的 `@author` 和 `@since`（如项目风格所示）
- 字段和方法命名符合 Java 约定
- 使用 `private final` / `final` 适当
- 不做无意义的抽象

## 生成结果输出要求

生成代码时，输出必须包含：

1. 类的完整 Java 代码
2. 需要的依赖说明
3. 关键设计说明
4. 是否需要补充枚举、异常、DTO 或 mapper
5. 如果有潜在风险，明确说明

最终输出要尽量满足：
- 可直接放进当前模块
- 与项目层次结构完全兼容
- 与现有代码风格高度一致

## 任务执行策略

在具体业务生成任务中，按以下顺序执行：

1. 确认模块归属：哪一个 `kernel-*` 模块
2. 确认业务域：如 AI、Auth、Cache、File、Event 等
3. 确认有没有现成的基类、枚举、异常和返回对象可复用
4. 生成对应的 Java 类/接口
5. 检查命名、包、注解和返回结构
6. 检查是否与 `ResponseData` / `ServiceException` / `ReadableEnum` 等规范一致
7. 若有缺失，补齐枚举或模块异常
8. 最后给出可执行的结果摘要

## 生成优先级

优先级从高到低：

1. 代码正确性
2. 项目风格一致性
3. 模块可维护性
4. 代码扩展性
5. 细节美感和可读性

## 结论

你的任务不是“生成能跑的代码”，而是“生成符合 roses_ai 体系的代码”。

任何输出都必须以本项目的规则为主：
- Java 17
- Spring Boot 3.2.10
- Maven multi-module
- `cn.stylefeng.roses.kernel` 包结构
- `ServiceException` / `ResponseData` / `ReadableEnum` / `@ChineseDescription` / `@Data`
- 中文注释与项目中文文档习惯

如果你在生成时不确定某个约定，优先遵从当前仓库中最接近的类和命名方式，而不是自由发挥。
