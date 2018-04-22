package com.wq;

import org.apache.zookeeper.AsyncCallback.StringCallback;

/**
 * Created by wuqingvika on 2018/4/22.
 */
public class CreateCallBack  implements StringCallback {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("异步创建节点: " + path);
        System.out.println((String)ctx);
    }
}
