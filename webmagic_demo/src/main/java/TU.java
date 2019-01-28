/**
 * @Author李胖胖
 * @Date 2019/1/28 11:28
 * @Description:
 **/
public class TU {
    private static long start = System.currentTimeMillis() ;
    private static String msg = "鬼知道你干了什么...";
    public static void start(String event){
        System.out.println("msg"+"  start........");
        msg = event;
        start = System.currentTimeMillis();
    }
    public static void end(){
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println(msg + "  结束........耗时: 约"+time+" 秒");
    }
}
