import com.utils.Timer;
import com.utils.TimerTask;

import java.util.concurrent.CountDownLatch;

public class TimerWheelTest {

    static int inCount = 0;

    static int runCount = 0;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        Timer timer = new Timer();
        int i = 0;
        while (i < 30){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TimerTask timerTask = new TimerTask(1000 * 100,()->{
                countDownLatch.countDown();
                int index = addRun();
                System.out.println(index+"----------执行");
            }, i);
            timer.addTask(timerTask);
            System.out.println(i+"++++++++++加入");
            inCount++;
            i++;
        }
        //TimerTask timerTask = new TimerTask(5000 * 10,()->{
        //    countDownLatch.countDown();
        //    int index = addRun();
        //    System.out.println(index+"----------执行");
        //});
        //timer.addTask(timerTask);
        try {
            countDownLatch.await();
            System.out.println("inCount" + inCount);
            System.out.println("runCount" + runCount);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static int addRun(){
        runCount++;
        return runCount;
    }
}
