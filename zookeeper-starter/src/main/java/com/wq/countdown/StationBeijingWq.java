package com.wq.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wuqingvika on 2018/4/23.
 */
public class StationBeijingWq extends DangerCenter {

    public StationBeijingWq(CountDownLatch countDown) {
        super(countDown, "北京吴庆调度站");
    }

    @Override
    public void check() {
        System.out.println("正在检查 [" + this.getStation() + "]...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("检查 [" + this.getStation() + "] 完毕，可以发车~");
    }

}
