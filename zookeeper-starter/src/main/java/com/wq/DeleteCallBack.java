package com.wq;

import org.apache.zookeeper.AsyncCallback;

/**
 * Created by wuqingvika on 2018/4/22.
 */
public class DeleteCallBack implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int rc, String path, Object ctx) {
        System.out.println("删除节点" + path);
        System.out.println((String)ctx);
    }
}
