# 回调
 A类中调用B类中的某个方法C,然后B类中反过来调用A类中的方法D,D这个方法就是回调方法。
- Class A实现接口CallBack callback
- Class A中包含一个Class B的引用b
- Class B有一个参数为callback的方法f
- A的对象a调用b的方法f
- b再调用A的方法

- - - 
 public class A {
     private List<String> wordList = loadList();

     public <T> T execute(CallBackInterface callBackInterface){
         //可以先执行一些execute的逻辑
         //直接将相关的操作权交给callBackInterface
         T result = (T) callBackInterface.process(wordList);
         return result;
     }


     public List<String> loadList() {
         List<String> wordList = new ArrayList<>();
         for (int i = 0; i < 10; i++) {
             wordList.add(Integer.toString(i));
         }
         return wordList;
     }

 }


 public interface CallBackInterface <T>{

     T process(List<Object> param);
 }

 public class Test {
     public static void main(String[] args){
         A a = new A();

         a.execute(new CallBackInterface() {
             @Override
             public Object process(List param) {
                 List<String> wordList = param;
                 wordList.remove("1");
                 return true;
             }
         });

         a.execute(new CallBackInterface() {
             @Override
             public Object process(List param) {
                 List<String> wordList = param;
                 wordList.add("24");
                 return true;
             }
         });

     }
 }
- - -
