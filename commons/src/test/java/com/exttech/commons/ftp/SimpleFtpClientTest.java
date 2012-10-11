package com.exttech.commons.ftp;

import com.exttech.commons.ftp.impl.SimpleFtpClient;

/**
 * //TODO
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/9/12
 * Time: 4:45 PM
 */
public class SimpleFtpClientTest {

    public static void main(String[] args) {
        FtpClientConfig config = new FtpClientConfig();
        config.setServerIp("127.0.0.1");
        config.setPort(21);
        config.setUsername("ftpuser1");
        config.setPassword("ftpuser1");
        SimpleFtpClient client = new SimpleFtpClient(config);
        client.upload("/home/zhangxingyu/person/me/", "photo.jpg", "/public/", "test.jpg");
    }
}
