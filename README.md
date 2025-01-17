# dynamic-slim
一个简易的动态线程池组件，基于[Java线程池实现原理及其在美团业务中的实践](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)进行实现。

## 实现思路

- 后端服务需将要使用到的线程池注册到 Spring 容器中。
- 动态线程池组件从 Spring 中获取线程池，通过 `getter` 获取线程池参数信息，也可基于 `setter` 对线程池参数进行修改。
- 前端与动态线程池组件之间维护一条 WebSocket 连接，动态线程池组件基于 Spring Task 定时向前端传输线程池参数供前端展示，而当开发人员通过前端界面对线程池参数信息进行修改后，前端需要将修改后的参数信息传递到动态线程池组件。
- 基于 SpringBoot 的自动装配，当其他项目引入动态线程池组件后，组件即可生效，无需特别配置。

![](https://pic1.imgdb.cn/item/678a7ee4d0e0a243d4f54dad.png)

## 使用方式

1. 要使用动态线程池组件的模块引入动态线程池组件的依赖：

```XML
<dependency>
    <groupId>com.chenxi</groupId>
    <artifactId>dynamic-slim</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 将动态线程池注册到 Spring 中，在需要使用的地方再从 Spring 中拿取就好，例：

```Java
@Configuration
public class UserPoolConfig {
    @Bean
    public DynamicThreadPool userThreadPool() {
        return new DynamicThreadPool(10, 20, 10L, TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }
}

// 需要使用时，从 Spring 中进行注入
@Autowired
private DynamicThreadPool userThreadPool;
```

3. 通过前端（resources/index.html）可实时查看动态线程池的参数信息，也可直接对核心线程数与最大线程数进行修改：

![](https://pic1.imgdb.cn/item/678a801dd0e0a243d4f54e16.png)



