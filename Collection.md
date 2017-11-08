
# 集合知识梳理(网上整理)

![image](https://github.com/WarframePrimer/mysmart4j/blob/master/img/Collection.jpg)

## 一、List: 有顺序已线性方式存储，可以存放重复对象
  线程安全方法：List list = Collections.synchronizedList(new LinkedList(...));  
  
  |List|存储方式|效率|是否线程安全|
  |---|---|---|---|
  |LinkedList|双向链表实现存储|索引数据慢插入数据快|线程不安全|
  |ArrayList|数组方式存储数据|索引数据快插入数据慢|线程不安全|
  |Vector|数组方式存储数据|索引数据快插入数据慢|线程安全|
  |Stack|继承自Vector，实现一个LIFO的堆栈|----|----|
  
## 二、Set: 无顺序，不包含重复的元素
- HashSet:为快速查找设计的Set。存入HashSet的对象必须定义hashCode()。
- TreeSet:保存次序的Set，底层为树结构。使用它可以从Set中提取有序的序列。
- LinkedHashSet:具有HashSet的查询速度，且内部使用链表维护元素的顺序(插入的次序)。于是在使用迭代器遍历Set时，结果会按元素插入的次序显示。

## 三、Map:键必须唯一
 线程同步方法：Map map = Collections.synchroniezdMap(new TreeMap(...));
 
 |Map|实现方式|是否允许空键空值|是否线程安全|
  |---|---|---|---|
  |HashTable|基于散列表的实现|允许空键空值|线程安全|
  |HashMap|基于散列表的实现|允许空键空值|线程不安全|
  |TreeMap|基于红黑树的实现|不允许空键空值|线程不安全|
  |WeakHashMap|改进的HashMap，对key实行`弱引用`|---|---|
  
  在除需要排序时使用TreeSet，TreeMap外，都应使用HashMap，HashSet，因为他们的效率更高。
  
```
 Collection
  |-List
   |-ArrayList
   |-LinkedList
   |-Vector
    |-Stack
  |-Set
   |-HashSet
   |-TreeSet
   |-LinkedHashSet
  Map
   |-Hashtable
   |-HashMap
   |-TreeMap
   |-WeakHashMap

```
# 补充:类之间的关系

- 范化:表示类与类之间的继承关系、接口与接口之间的继承关系;
- 实现:表示类对接口的实现;
- 依赖:当类与类之间有使用关系时就属于依赖关系，不同于关联关系，依赖不具有"拥有关系"，而是一种"相识关系";
- 关联:表示类与类或类与接口之间的依赖关系，表现为"拥有关系"；具体到代码可以用实例变量来表示;
- 聚合:属于关联的特殊情况，体现部分-整体关系，是一种弱拥有关系；整体和部分可以有不一样的生命周期；是一种弱关联；
- 组合:属于关联的特殊情况，也体现了部分-整体关系系，是一种强拥有关系；整体和部分有相同的生命周期。是一种强关联;



  
 
  
  
