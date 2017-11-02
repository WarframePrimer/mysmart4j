
# 集合知识梳理(网上整理)

## 一、List: 有顺序已线性方式存储，可以存放重复对象
  线程安全方法：List list = Collections.synchronizedList(new LinkedList(...));  
  
  |List|存储方式|效率|是否线程安全|
  |---|---|---|---|
  |LinkedList|双向链表实现存储|索引数据慢插入数据快|线程不安全|
  |ArrayList|数组方式存储数据|索引数据快插入数据慢|线程不安全|
  |Vector|数组方式存储数据|索引数据快插入数据慢|线程安全|
 
  
  
