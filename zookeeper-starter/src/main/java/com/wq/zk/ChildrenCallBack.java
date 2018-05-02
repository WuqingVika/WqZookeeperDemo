package com.wq.zk;
import java.util.List;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;

/**
 * Created by wuqingvika on 2018/5/2.
 */
public class ChildrenCallBack implements ChildrenCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {
        System.out.println(children.size()+"........testA......");
        if(children!=null&&!children.isEmpty()){
            for (String s : children) {
                System.out.println(s);
            }
        }
        System.out.println("ChildrenCallback:" + path);
        System.out.println((String)ctx);
    }

}