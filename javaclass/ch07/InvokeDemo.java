package ch07;

public class InvokeDemo implements Runnable {
    public InvokeDemo(){
        System.out.println("InvokeDemo() -- invokespecial");
    }

    public static void main(String[] args) {
        new InvokeDemo().test();
    }

    public void test() {
        InvokeDemo.staticMethod();          // invokestatic
        InvokeDemo demo = new InvokeDemo(); // invokespecial
        demo.instanceMethod();              // invokespecial
        super.equals(null);                 // invokespecial
        this.run();                         // invokevirtual
        ((Runnable) demo).run();            // invokeinterface
    }

    public static void staticMethod() {
        System.out.println("staticMethod -- invokestatic");
    }
    private void instanceMethod() {
        System.out.println("instanceMethod -- instanceMethod");
    }
    @Override public void run() {
        System.out.println("run() -- ");
    }

}