
public class Test implements TestInterface{
    final static Integer I = 10;
    final Integer j = 10;
    final String k = "kkkk";
    String l = "hellolll";

    @Override
    public void interfaceMethod(){
        String d =  "22";
    }

    public static void main(String[] args){
        try {
            System.out.println("hello zvm!");
        }catch (Exception e){

        }finally {
            System.out.println("finally hello zvm!");
        }
        Test test = null;
//        test.l = "2";

        int u = 0;
        int v = 0;
        if(u > 0){
            int x = 0;
        }

        int y = 0;
        System.out.println(new Test());
    }


    @Override
    public String toString() {
        return"This is String Representation of current object.";
    }
}