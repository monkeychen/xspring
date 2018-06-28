# xspring quick start
## English
xspring is a Java component library base spring-framework.


## 中文
xspring是一个基于Spring的Java组件库。

> 详细使用说明可参考：[基于Spring的动态多数据源组件使用文档](http://cloudnoter.com/2017/09/11/%E5%9F%BA%E4%BA%8ESpring%E7%9A%84%E5%8A%A8%E6%80%81%E5%A4%9A%E6%95%B0%E6%8D%AE%E6%BA%90%E7%BB%84%E4%BB%B6%E4%BD%BF%E7%94%A8%E6%96%87%E6%A1%A3/)

### xspring-core组件
core组件是所有其他组件的基础，其提供了通用的事件总线、日志管理等功能。其中`XspringApplication`是整个框架的启动类，通过调用这个启动类的startup静态方法，并提供您打上`@org.springframework.context.annotation.Configuration`注解的启动类来启动您的应用。

> 框架启动时按如下顺序加载配置文件
> * file:./config/env.properties
> * classpath:/config/env.properties
> * file:./config/xspring.properties
> * file:./xspring.properties
> * classpath:./config/xspring.properties
> * classpath:./xspring.properties

上述配置文件中`env.properties`文件中的属性信息支持热加载（即每隔指定时间框架都会读取该文件并更新至SystemProperties），相关源码请参考类`EnvironmentInitializer`.

基于Xspring框架的应用启动示例代码如下：

```
@Configuration
@ImportResource("classpath:/config/customized-beans.xml")   // 可以通过ImportResource方式加载定义在XML中的bean信息。
public class DemoApplication {
    private ApplicationContext applicationContext;

    private DemoService demoService;

    public static void main(String[] args) throws Exception {
        applicationContext = XspringApplication.startup(DemoApplication.class, args);
        demoService = applicationContext.getBean("demoService", DemoService.class);
    }
}
```

`XspringApplication`类的`startup`方法会创建`XspringApplication`实例并调用其`run`方法：

```
public static ConfigurableApplicationContext startup(Class<?> annotatedClass, String[] args) {
    return new XspringApplication().run(annotatedClass, args);
}
```

在`run`方法中，框架会通过SPI方式加载其他组件提供的标注有`@Configuration`注解的`org.xspring.core.extension.ModuleConfiguration`接口实现类，从而启动其他组件。

SPI声明统一存放在各个组件的`classpath:/META-INF/spring.factories`文件中，整个加载过程源码如下：

```
public ConfigurableApplicationContext run(Class<?> annotatedClass, String[] args) {
    logger.debug("The input arguments is:{}, {}", annotatedClass, args);
    AnnotationConfigApplicationContext context = null;
    // Load other configurations in spring.factories file
    ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
    List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(ModuleConfiguration.class, classLoader);
    List<Class> moduleConfigClasses = Lists.newArrayList();
    moduleConfigClasses.add(XspringConfiguration.class);
    if (CollectionUtils.isNotEmpty(factoryNames)) {
        for (String factoryName : factoryNames) {
            try {
                Class factoryClass = ClassUtils.forName(factoryName, classLoader);
                moduleConfigClasses.add(factoryClass);
            } catch (ClassNotFoundException e) {
                logger.warn("Can not find the matched class[{}] in classpath!", factoryName);
            }
        }
    }
    if (annotatedClass != null) {
        moduleConfigClasses.add(annotatedClass);
    }
    Class[] configurations = moduleConfigClasses.toArray(new Class[moduleConfigClasses.size()]);
    context = new AnnotationConfigApplicationContext(configurations);
    context.start();
    return context;
}
```

> xspring-core组件还提供了一个基于Google Guava库的事件总线模型，有兴趣的朋友可直接参考`org.xspring.core.eventbus`包下的相关源码。

### xspring-data组件
xspring-data组件的模块定义（启动）类为：`XspringDataConfiguration`，其会通过`@Import(DataSourceInitializer.class)`方式加载动态数据源初始化配置类。`DataSourceInitializer`也是一个`@Configurable`注解类，其通过`@Bean`的方式定义了`datasource`这个动态数据源。

在动态数据源bean创建过程中，组件会通过SPI方式加载DataSourceFactory这个接口的实现类来获取具体的数据库连接池提供方，框架默认提供了DruidDataSourceFactory。
您也可以自己提供数据库连接池的实现类（同样定义在各个组件的`classpath:/META-INF/spring.factories`文件中），并通过添加`org.springframework.core.annotation.Order`注解来指定加载优先级。

> **注意：xspring-data组件的动态多数据源目前只支持相同数据库连接池提供方，即只会使用order值最小的`DataSourceFactory`实现类来创建真正的数据源对象。**

源码如下：

```
@Bean
    public DataSource dataSource() {
        // 加载DataSourceFactory实现类列表，返回的实例已根据@order注解进行升序排序
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

        // 通过SPI方式加载DataSourceFactory这个接口的实现类来获取具体的数据库连接池提供方，框架默认提供了DruidDataSourceFactory。
        List<DataSourceFactory> dataSourceFactories = SpringFactoriesLoader.loadFactories(DataSourceFactory.class, classLoader);
        if (CollectionUtils.isEmpty(dataSourceFactories)) {
            throw new BeanCreationException("Not found any DataSourceFactory implementer in class path!");
        }

        DataSourceFactory dataSourceFactory = dataSourceFactories.get(0);
        Map<String, DataSource> dataSourceMap = dataSourceFactory.loadOriginalDataSources(environment);
        if (CollectionUtils.isEmpty(dataSourceMap)) {
            throw new BeanCreationException("Fail to load any original DataSource instance!");
        }

        Map<Object, Object> targetDataSourceMap = Maps.newHashMap();
        dataSourceMap.forEach((name, dataSource) -> {
            beanFactory.registerSingleton(name, dataSource); // 将具体的DataSource实例注册进ApplicationContext
            targetDataSourceMap.put(name, dataSource);
            DynamicDataSourceContextHolder.addDataSourceId(name);
        });
        DataSource defaultDataSource = dataSourceFactory.getDefaultDataSource(environment);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSourceMap);
        dataSource.setDefaultTargetDataSource(defaultDataSource);
        return dataSource;
    }
```

### xspring-tutorial组件
> 该组件用于放置各种开源框架的使用演示案例。


### 源码质量分析

```bash
# https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Maven
mvn sonar:sonar \
  -Dsonar.organization=monkeychen-github \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=296c5493d14d57c4eb1877f7a49dc2f0bd755434
```