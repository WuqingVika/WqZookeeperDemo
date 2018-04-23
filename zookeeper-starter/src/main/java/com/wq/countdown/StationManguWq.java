package com.wq.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wuqingvika on 2018/4/23.
 */
public class StationManguWq extends DangerCenter {

    public StationManguWq(CountDownLatch countDown) {
        super(countDown, "泰国曼谷调度站");
    }

    @Override
    public void check() {
        System.out.println("正在检查 [" + this.getStation() + "]...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("检查 [" + this.getStation() + "] 完毕，可以发车~");
    }

}