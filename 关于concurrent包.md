# Java并发工具包---java.util.concurrent(网上整理)
`并发包众多实现的基础：Java内存模型、volatile变量、AQS同步器`

- Java内存模型  
    描述了线程内存和主存间的通讯关系。定义了线程内的内存改变将怎样传递到其他线程的规则，同样也定义了线程内存和主存进行同步的细节，也描述了哪些操作属于原子操作以及操作间的顺序。  
    `代码顺序规则`：一个线程内的每个动作happens-before同一个线程内在代码顺序上在其后的所有动作。  
    `volatile变量规则`：对一个volatile变量的读，总是能看到(任意线程)对这个volatile变量最后的写入。  
    `传递性`：如果A happens-before B,B happens-before C,那么A happens-before C。   
- volatile  
    当声明共享变量为volatile后，对这个变量的读/写将会很特别。理解volatile:把对volatile变量的单个读/写，看成是使用同一个监视器锁对这些单个读/写操作进行了同步。  
    监视器锁的happens-before规则保证释放监视器和获取监视器的两个线程之间的内存可见性，这意味着对一个volatile变量的读，总是能看到对这个变量最后的写入。   
    volatile变量自身具有下列特性：  
        可见性：对一个volatile变量的读，总是能看到对这个变量最后的写入。   
        原子性：对任意单个volatile变量的读/写具有原子性，但类似volatile++这种复合操作不具有原子性。    
    volatile写和volatile读：  
        线程A写一个volatile变量，实质上是线程A向接下来将要读这个volatile变量的某个线程发出了(其对共享变量所在修改的)消息。  
        线程B读一个volatile变量，实质上是线程B接收了之前某个线程发出的(在写这个volatile变量之前对共享变量所做修改的) 消息。  
        线程A写一个volatile变量，随后线程B读这个volatile变量，这个过程实质上就是线程A通过主内存向线程B发送消息。  
    锁释放和锁获取：  
        线程A释放一个锁，实质上是线程A向接下来将要获取这个锁的线程发出了(线程A对共享变量所做修改的)消息。   
        线程B获取一个锁，实质上是线程B接受了之前某个线程发出的(在释放锁之前对共享变量所做修改的)消息。  
        线程A释放锁，随后线程B获取这个锁，实质上是线程A通过主内存向线程B发送消息。  
    由于volatile仅仅保证对单个volatile变量的读/写具有原子性，而监视器的互斥执行的特性可以确保对整个临界区代码的执行具有原子性。在功能上，监视器锁比volatile更强大；在可伸缩性和执行性能上，volatile更具有优势。    
    
- AbstractQueueSynchronizer(AQS)

    AQS使用一个整型的volatile变量(state)来维护同步状态。

