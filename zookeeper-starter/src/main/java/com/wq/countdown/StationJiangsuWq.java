package com.wq.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wuqingvika on 2018/4/23.
 */
public class StationJiangsuWq extends DangerCenter {

    public StationJiangsuWq(CountDownLatch countDown) {
        super(countDown, "江苏启东调度站");
    }

    @Override
    public void check() {
        System.out.println("正在检查 [" + this.getStation() + "]...");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("检查 [" + this.getStation() + "] 完毕，可以发车~");
    }

}