
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


----------

1.ArrayList  
     ArrayList基于数组方式实现，默认构造器通过调用ArrayList(int)来完成创建，传入的值为10，实例化了一个Object数组，并将此数组赋给了当前实例的elementData属性，此Object数组的大小即为传入的initialCapacity,因此调用空构造器的情况下会创建一个大小为10的Object数组。  
     插入对象：add(E)    
     基于已有元素数量加1作为名为minCapacity的变量，比较此值和Object数组的大小，若大于数组值，那么先将当前Object数组值赋给一个数组对象，接着产生一个新的的数组容量值。此值的计算为当前数组值*1.5+1，如得到的容量值仍然小于minCapacity,那么就以minCapacity作为新的容量值，调用Array.copyOf来生成新的数组对象。  
     删除对象：remove(E)  
     调用faseRemove方法将index后的对象往前复制一位，并将数组中的最后一个元素的值设置为null，即释放了对此对象的引用。还提供了remove(int)方法来删除指定位置的对象，remove(int)的实现比remove(E)多了一个数组范围的检测，但少了对象位置的查找，因此性能会更好。(用到了System.arraycopy(src,srcPos,dest,destPos,int length))  
2.LinkedList  
     LinkedList基于双向链表机制，LinkedList在插入元素时，需要创建一个新的Node对象，并切换相应元素的前后元素的引用；在查找元素时，需遍历链表；在删除元素时，要遍历链表，找到要删除的元素，然后从链表上将此元素删除即可，此时原有的前后元素改变引用连在一起；  
3.Vector  
     add,remove,get(int)方法都加了synchronized关键字，默认创建一个大小为10的Object数组，并将capacityIncrement设置为0。  
     容量扩充策略：如果capacityIncrement大于0，则将Object数组的大小扩大为现有size加上capacityIncrement的值；如果capacity等于或小于0，则将Object数组的大小扩大为现有size的两倍，这种容量的控制策略比ArrayList更为可控。  
     Vector是基于synchronized实现的线程安全的ArrayList，但在插入元素时容量扩充的机制和ArrayList稍有不同，并可通过传入capacityIncrement(增量因子)来控制容量的扩充。  
4.Stack  
     Stack继承于Vector，在其基础上实现了stack所要求的LIFO的pop和push操作  
     push操作通过调用Vector中的addElement来完成；  
     pop操作通过调用peek来获取元素，并同时删除数组中的最后一个元素；  
     peek操作通过获取当前Object数组的大小，并获取数组上的最后一个元素；  
5.HashSet  
     默认构造一个HashMap对象  
     add(E):调用HashMap的put方法来完成此操作，将需要增加的元素作为Map中的key，value则传入一个之前已创建的Object对象。  
     remove(E):调用HashMap的remove(E)方法完成此操作。
     contains(E):HashMap的containsKey  
     HashSet不支持通过get(int)来获取指定位置的元素，只能通过iterator()方法来获取。  
6.TreeSet  
     TreeSet和HashSet的主要不同在于TreeSet对于排序的支持，TreeSet基于TreeMap实现。  
7.HashMap  
     HashMap空构造，将loadFactor设为默认的0.75，threshold设置为12，并创建一个大小为16的Entry对象数组。  
     基于数组+链表(散列表)实现，将key-value看成一个整体，存放于Entity[]数组中，put的时候根据key hash后的hashcode和数组length-1按位与的结果判断放在数组的哪个位置，如果该数组位置上以存放其他元素，则在这个位置上的元素以链表的形式存放，否则就直接存放。  
     当系统决定存储HashMap中的key-value对时，完全没有考虑Entry中的value，仅仅根据key来计算并决定每个Entry的存储位置。完全可以把Map集合中的value当成key的附属，当系统决定了key的存储位置之后，value随之保存。get取值也是根据key的hashCode确定在数组的位置，在根据key的equals确定在链表处的位置。  
  

  
 
  
  
