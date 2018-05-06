package com.wq.curator;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
/**
 * Created by wuqingvika on 2018/5/6.
 */

public class MyCuratorWatcher implements CuratorWatcher {

    @Override
    public void process(WatchedEvent event) throws Exception {
        System.out.println(" MyCuratorWatcher--触发watcher，节点路径为：" + event.getPath());
    }

}
