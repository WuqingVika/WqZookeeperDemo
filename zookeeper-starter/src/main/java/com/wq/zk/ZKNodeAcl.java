package com.wq.zk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.wq.utils.AclUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
/**
 * Created by wuqingvika on 2018/5/5.
 */
public class ZKNodeAcl implements Watcher {

    private ZooKeeper zookeeper = null;

    public static final String zkServerPath = "192.168.1.124:2181";
    public static final Integer timeout = 30000;
    private Stat stat;

    public ZKNodeAcl() {}

    public ZKNodeAcl(String connectString) {
        try {
            zookeeper = new ZooKeeper(connectString, timeout, new ZKNodeAcl());
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
            result = zookeeper.create(path, data, acls, CreateMode.PERSISTENT);
            System.out.println("创建节点：\t" + result + "\t成功...");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        ZKNodeAcl zkServer = new ZKNodeAcl(zkServerPath);

        /**
         * ======================  创建node start  ======================
         */
        // 1.acl 任何人都可以访问  默认匿名权限
		//zkServer.createZKNode("/aclimooc", "datawq".getBytes(), Ids.OPEN_ACL_UNSAFE);

        // 2.自定义用户认证访问
       /* List<ACL> acls = new ArrayList<ACL>();
        Id wq1 = new Id("digest", AclUtils.getDigestUserPwd("wq1:123456"));
        Id wq2 = new Id("digest", AclUtils.getDigestUserPwd("wq2:123456"));
        acls.add(new ACL(Perms.ALL, wq1));
        acls.add(new ACL(Perms.READ, wq2));
        acls.add(new ACL(Perms.DELETE | Perms.CREATE, wq2));
        zkServer.createZKNode("/aclimooc/testdigest", "testdigest".getBytes(), acls);*/

        // 注册过的用户必须通过addAuthInfo才能操作节点，参考命令行 addauth
        /*zkServer.getZookeeper().addAuthInfo("digest", "wq1:123456".getBytes());
       // zkServer.createZKNode("/aclimooc/testdigest/childtest", "childtest".getBytes(), Ids.CREATOR_ALL_ACL);
       *//* Stat stat = new Stat();
        byte[] data = zkServer.getZookeeper().getData("/aclimooc/testdigest", false, stat);
        System.out.println(new String(data));*//*
        zkServer.getZookeeper().setData("/aclimooc/testdigest", "now2".getBytes(), 1);*/

        // ip方式的acl
		/*List<ACL> aclsIP = new ArrayList<ACL>();
    	Id ipId1 = new Id("ip", "192.168.1.107");
		aclsIP.add(new ACL(Perms.ALL, ipId1));
    	zkServer.createZKNode("/aclimooc/iptest2", "iptest".getBytes(), aclsIP);*/

        // 验证ip是否有权限
        zkServer.getZookeeper().setData("/aclimooc/iptest2", "now".getBytes(), 0);
        Stat stat = new Stat();
        byte[] data = zkServer.getZookeeper().getData("/aclimooc/iptest2", false, stat);
        System.out.println(new String(data));
        System.out.println(stat.getVersion());
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


