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
public class T1ClassLink {
    public static String value1 = "abc";
    public final static String FINAL_VALUE = "zvm";
    public static String[] arr = {"arr0","arr1","dsafasfsdafd"};
    public final static String[] FINAL_ARR = {"final-arr0","final-arr1","final-dsafasfsdafd"};

    public String generalStr = "generalStr";

    public static void main(String[] args){
        System.out.println(value1);
        System.out.println(FINAL_VALUE);
        System.out.println(arr[0]);
        System.out.println(FINAL_ARR[0]);
        System.out.println(new T1ClassLink().generalStr);
    }
}
