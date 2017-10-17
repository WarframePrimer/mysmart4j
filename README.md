# mysmart4j
看《架构探险 从零开始写JavaWeb框架》，自己实现一个轻量级的JavaWeb框架^_^

## DispatcherServlt
  总结：搭建了一个简单的MVC框架，
  - @Controller来定义Controller类;
  - @Inject来实现依赖注入;
  - @Service来定义服务类;
  - @Action来定义Controller中的Action方法;
  通过一系列的的Helper类来初始化MVC框架，通过DispatcherServlet处理所有的请求,根据请求方法(requestMethod)和请求路径(requestPath)来调用Controller中具体的action方法,根据方法的返回值,如果为View类型,就跳转到JSP页面,如果为Data类型,直接返回JSON数据。
  
