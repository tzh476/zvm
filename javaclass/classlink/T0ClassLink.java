package classlink;

/**
 * 类的生命周期
 *  - 加载
 *  - 链接
 *      - 验证
 *      - 准备
 *      - 解析
 * - 初始化
 * - 使用
 * - 卸载
 */
public class T0ClassLink {
    public static int value1 = 132;
    public final static int FINAL_VALUE = 168;
    public static int[] arr = {1,3,5};
    public final static int[] FINAL_ARR = {1,6,8};

    public static void main(String[] args){
        System.out.println(value1);
        System.out.println(FINAL_VALUE);
        System.out.println(arr[0]);
        System.out.println(FINAL_ARR[0]);

    }
}
