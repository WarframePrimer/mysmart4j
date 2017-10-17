# mysmart4j
看《架构探险 从零开始写JavaWeb框架》，自己实现一个轻量级的JavaWeb框架^_^

## DispatcherServlt
- 处理所有的request请求，从HttpServletRequest对象中获取requestMethod和requestPath，通过ControllerHelper#getHandler方法获取Handler对象


