package com.exttech.commons.ftp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * //TODO
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/8/12
 * Time: 2:47 PM
 */
public class FtpUtilTest {
    public static void main(String[] args) throws FileNotFoundException {
        FtpClientConfig ftpConfig = new FtpClientConfig();
        ftpConfig.setServerIp("127.0.0.1");
        ftpConfig.setPort(21);
        ftpConfig.setUsername("ftpuser1");
        ftpConfig.setPassword("ftpuser1");
        ftpConfig.setLocation(".");

        java.util.Properties config = new Properties();
        config.setProperty("server", ftpConfig.getServerIp());
        config.setProperty("username", ftpConfig.getUsername());
        config.setProperty("password", ftpConfig.getPassword());

        FtpUtil client = new FtpUtil();
        client.setConfig(config);
        client.upload(new FileInputStream("/home/zhangxingyu/person/me/photo.jpg"), "/public/photo.jpg", true);
    }
}
