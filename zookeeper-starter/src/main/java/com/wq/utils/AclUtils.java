package com.wq.utils;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
/**
 * Created by wuqingvika on 2018/5/5.
 */
import java.io.IOException;

public class AclUtils {

    public static String getDigestUserPwd(String id) throws Exception {
        return DigestAuthenticationProvider.generateDigest(id);//生成密钥
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, Exception {
        String id = "wq:wq";
        String idDigested = getDigestUserPwd(id);
        System.out.println(idDigested);
    }
}
