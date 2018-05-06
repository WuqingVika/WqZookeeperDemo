package com.wq.curator.utils;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

/**
 * Created by wuqingvika on 2018/5/6.
 */
public class AclUtils {

    public static String getDigestUserPwd(String id) {
        String digest = "";
        try {
            digest = DigestAuthenticationProvider.generateDigest(id);//将明文密码转为密文
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest;
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, Exception {
        String id = "wq:wq";
        String idDigested = getDigestUserPwd(id);
        System.out.println(idDigested);
    }
}