package nestedclass;

/**
 1. 嵌套类：
    - 静态嵌套类；
    - 非静态嵌套类(non-static nested class 又名内部类，inner Classes)
        - 普通内部类(成员内部类)
        - 局部内部类
        - 匿名内部类
 https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
 */
public class T0NestedClass {
    static class StaticClass{
        public String staticClassKey = "staticClassVale";
        public void test(){
            System.out.println(staticClassKey);
        }
    }

    /**
     * 普通内部类
     */
    class GenaralClass{
        public String genaralClassKey = "genaralClassValue";
        public void test(){
            System.out.println(genaralClassKey);
        }
    }

    public static void main(String[] args){
        class LocalClass{
            public String localClassKey = "LocalClassValue";
            public void test(){
                System.out.println(localClassKey);
            }
        }

        AnonymousClass anonymousClass = new AnonymousClass(){
            public String anonymousClassKey = "anonymousClassValue";
            @Override
            public void test(){
                System.out.println(anonymousClassKey);
            }
        };

        /*静态类测试*/
        StaticClass staticClass = new StaticClass();
        staticClass.test();

        /*普通内部类测试*/
        new T0NestedClass().generalClassTest();

        /*局部内部类测试*/
        LocalClass localClass = new LocalClass();
        localClass.test();

        /*匿名内部类测试*/
        anonymousClass.test();

    }

    public void generalClassTest(){
        GenaralClass genaralClass = new GenaralClass();
        genaralClass.test();
    }
}
class AnonymousClass{
    public void test() {
    }
}