
#### 事务的四大特性(ACID)
- A Atomicity 原子性
- C Consistency 一致性
- I Isolation 隔离性
- D Durability 持久性
#### 这四个特性是事务管理的基石：原子性是基础，隔离性是手段，持久性是目的，一致性是核心。

#### 事务隔离级别(Transaction Isolation Level) 从上往下，级别越来越高，并发性越来越差，安全性越来越高
- READ_UNCOMMITTED
- READ_COMMITTED
- REPEATABLE_READ
- SERIALIZABLE

#### 数据在高并发下产生的问题：
- Dirty Read(脏读);
- Unrepeatable Read(不可重复读);
- Phantom Read(幻读)。
---------
 `脏读数据` 
 
 |时间|事务A(存款)|事务B(取款)|
  |---|---|---|
  |T1|开始事务|——|
  |T2|——|开始事务|
  |T3|——|查询余额(1000元)|
  |T4|——|取出1000元(余额0元)|
  |T5|查询余额(0元)|——|
  |T6|——|撤销事务(余额恢复为1000元)|
  |T7|存入500元(余额500元)|——|
  |T8|提交事务|——|  
  
  `不可重复读`
  
|时间|事务A(存款)|事务B(取款)|
  |---|---|---|
 

