package com.wq.curator;
import java.util.ArrayList;
import java.util.List;
import com.wq.curator.utils.AclUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
/**
 * Created by wuqingvika on 2018/5/6.
 */
public class CuratorAcl {

    public CuratorFramework client = null;
    public static final String zkServerPath = "192.168.1.124:2181";

    public CuratorAcl() {
        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
        client = CuratorFrameworkFactory.builder().authorization("digest", "wq1:123456".getBytes())
                .connectString(zkServerPath)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .namespace("workspace").build();
        client.start();
    }

    public void closeZKClient() {
        if (client != null) {
            this.client.close();
        }
    }

    public static void main(String[] args) throws Exception {
        // 实例化
        CuratorAcl cto = new CuratorAcl();
        boolean isZkCuratorStarted = cto.client.isStarted();
        System.out.println("当前客户的状态：" + (isZkCuratorStarted ? "连接中" : "已关闭"));

        String nodePath = "/acl/father/child/sub";

        List<ACL> acls = new ArrayList<ACL>();
        Id wq1 = new Id("digest", AclUtils.getDigestUserPwd("wq1:123456"));
        Id wq2 = new Id("digest", AclUtils.getDigestUserPwd("wq2:123456"));
        acls.add(new ACL(Perms.ALL, wq1));
        acls.add(new ACL(Perms.READ, wq2));
        acls.add(new ACL(Perms.DELETE | Perms.CREATE, wq2));

        // 创建节点
 		/*byte[] data = "spiderman".getBytes();
 		cto.client.create().creatingParentsIfNeeded()
 			.withMode(CreateMode.PERSISTENT)
 			.withACL(acls, true)//true就相当于递归给多级目录添加同样的权限 。
                // 不建议使用 因为一般公司大多会针对一个节点进行特殊权限设定
 			.forPath(nodePath, data);*/

       //为某一节点修改权限
       cto.client.setACL().withACL(acls).forPath("/super/testModify");

        // 更新节点数据
//		byte[] newData = "batman".getBytes();
//		cto.client.setData().withVersion(0).forPath(nodePath, newData);

        // 删除节点
//		cto.client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(0).forPath(nodePath);

        // 读取节点数据
//		Stat stat = new Stat();
//		byte[] data = cto.client.getData().storingStatIn(stat).forPath(nodePath);
//		System.out.println("节点" + nodePath + "的数据为: " + new String(data));
//		System.out.println("该节点的版本号为: " + stat.getVersion());


        cto.closeZKClient();
        boolean isZkCuratorStarted2 = cto.client.isStarted();
        System.out.println("当前客户的状态：" + (isZkCuratorStarted2 ? "连接中" : "已关闭"));
    }

}
