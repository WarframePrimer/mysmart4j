# mysmart4j
看《架构探险 从零开始写JavaWeb框架》，自己实现一个轻量级的JavaWeb框架^_^
# mysmart4j流程
- ConfigHelper 读取smart.properties配置(包括数据库记载驱动、数据库连接、用户名、密码、应用包名、jsp文件路径、静态资源路径);
- ClassHelper 加载应用包名下的所有类，并放入CLASS_SET中(Set<Class<?>> CLASS_SET);
- BeanHelper 获取mysmart4j框架管理的Bean(Controller,Service以及之后的代理类等)(Map<Class<?>,Object> BEAN_MAP(beanClass,beanInstance));
- AopHelper 获取目标类(委托类)和代理对象(targetClass,Proxy)之后放入BEAN_MAP中进行管理
- IocHelper 获取BEAN_MAP后，对于类中属性带有注解@Inject的进行依赖注入
- ControllerHelper 建立一个ACTION_MAP(Request,Handler)关于请求和处理的映射关系,实现MVC的过程(SpringMVC)

## DispatcherServlt
- @Controller注解来定义Controller类;
- @Inject注解来实现依赖注入;
- @Service注解来定义服务类;
- @Action注解来定义Controller中的Action方法;
- 通过一系列的的Helper类来初始化MVC框架，通过DispatcherServlet处理所有的请求,根据请求方法(requestMethod)和请求路径(requestPath)来调用Controller中具体的action方法,根据方法的返回值,如果为View类型,就跳转到JSP页面,如果为Data类型,直接返回JSON数据。

## 关于JDK动态代理和CGlib动态代理
- JDK的动态代理要求目标对象必须实现接口，因为它创建代理对象的时候是根据接口创建的，如果不实现接口，JDK无法给目标对象创建代理对象。被代理对象可以实现多个接口，创建代理时指定创建某个接口的代理对象就可以调用接口定义的方法。
- JDK给目标类提供动态要求目标类必须实现接口，当一个目标类不实现接口时，JDK是无法为其提供动态代理的。而CGlib就能弥补这一缺陷。Spring框架在给某个类提供动态代理时会自动在JDK动态代理和CGlib动态代理中进行动态的选择。(注意：如果一个类继承了某个类，在子类中没有一个方法，用CGlib生成该子类的动态代理类中将没有一个方法。)
### JDK和CGLib动态代理实现的区别
- JDK动态代理生成的代理类和委托类实现了相同的接口;
- CGLib动态代理中生成的字节码更加复杂，生成的代理类是委托类的子类，且不能处理被final关键字修饰的方法;
- JDK采用反射机制调用委托类的方法，CGLib采用类似索引的方式直接调用委托类方法;

## AOP
![img text](https://raw.githubusercontent.com/WarframePrimer/mysmart4j/master/img/AOP.jpg)




  
