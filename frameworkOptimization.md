## 框架优化与功能扩展

### 优化Action参数
    对于某些Action而言，不需要Param参数，所以需要在对`actionMethod`进行`invoke`之前进行一个判断:
    如果`Param`为空，调用时就不传入Param;否则，就传入。
    
