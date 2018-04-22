package com.wq;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuqingvika on 2018/4/22.
 */
public class ZKNodeOperator implements Watcher {

    private ZooKeeper zookeeper = null;
    public static final String zkServerPath = "192.168.1.124:2181";
   // public static final String zkServerPath = "192.168.1.124:2181";
    public static final Integer timeout = 50000;///这个时间真的有毒 设大点就不会报错了

    public ZKNodeOperator() {}

    public ZKNodeOperator(String connectString) {
        try {
            zookeeper = new ZooKeeper(connectString, timeout, new ZKNodeOperator());
        } catch (IOException e) {
            e.printStackTrace();
            if (zookeeper != null) {
                try {
                    zookeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @Title: ZKOperatorDemo.java
     * @Description: 创建zk节点
     */
    public void createZKNode(String path, byte[] data, List<ACL> acls) {

        String result = "";
        try {
            /**
             * 同步或者异步创建节点，都不支持子节点的递归创建，异步有一个callback函数
             * 参数：
             * path：创建的路径
             * data：存储的数据的byte[]
             * acl：控制权限策略
             * 			Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
             * 			CREATOR_ALL_ACL --> auth:user:password:cdrwa
             * createMode：节点类型, 是一个枚举
             * 			PERSISTENT：持久节点
             * 			PERSISTENT_SEQUENTIAL：持久顺序节点
             * 			EPHEMERAL：临时节点
             * 			EPHEMERAL_SEQUENTIAL：临时顺序节点
             */
            //result = zookeeper.create(path, data, acls, CreateMode.PERSISTENT);//同步创建

            String ctx = "{'create':'success'}";
            zookeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(), ctx);

            System.out.println("创建节点：\t" + result + "\t成功...");
            new Thread().sleep(50000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ZKNodeOperator zkServer = new ZKNodeOperator(zkServerPath);
        // 创建zk节点
     	/*zkServer.createZKNode("/testnodewq", "testnodewq".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);*/

        /**
         * 参数：
         * path：节点路径
         * data：数据
         * version：数据状态
         */
//		Stat status  = zkServer.getZookeeper().setData("/testnode", "xyz".getBytes(), 2);
//		System.out.println(status.getVersion());

        /**
         * 参数：
         * path：节点路径
         * version：数据状态
         */
        /*zkServer.createZKNode("/test-delete-node", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);//同步
    	zkServer.getZookeeper().delete("/test-delete-node", 0);//0代表版本号*/

        String ctx = "{'delete':'success'}";
        //异步删除
        zkServer.getZookeeper().delete("/test-delete-node", 0, new DeleteCallBack(), ctx);
        Thread.sleep(30000);
    }

    public ZooKeeper getZookeeper() {
        return zookeeper;
    }
    public void setZookeeper(ZooKeeper zookeeper) {
        this.zookeeper = zookeeper;
    }

    @Override
    public void process(WatchedEvent event) {
    }
}