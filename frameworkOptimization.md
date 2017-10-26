## 框架优化与功能扩展

### 优化Action参数
```
对于某些Action而言，不需要Param参数，所以需要在对`actionMethod`进行`invoke`之前进行一个判断:
如果`Param`为空，调用时就不传入Param;否则，就传入。
``` 
### 功能扩展:文件上传
```
要实现文件上传，原来框架中`Param`中的`paramMap`就不能满足这个需求了，所以需要对`Param`进行重新设计;
原来：Param-->List<String,Object> paramMap 只保存键值对  参数名--参数值
重新设计后：Param-->1.List<FormParam> formParamList;2.List<FileParam> fileParamList. 提供了两个属性，
A.formParamList用来保存表单键值对(FormParam-->fielName:fieldValue，类似于原来的paramMap);
B.fileParamList则用来保存文件相关参数(FileParam-->fieldName,fileName,fileSize,contentType,InputStream).
```
