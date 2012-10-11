package com.exttech.commons.ftp.impl;

import com.exttech.commons.ftp.FtpClientConfig;
import com.exttech.commons.ftp.FtpClientInf;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;

/**
 * //TODO
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/9/12
 * Time: 3:36 PM
 */
public class SimpleFtpClient implements FtpClientInf {
    private final Logger log = LoggerFactory.getLogger(SimpleFtpClient.class);
    private boolean isIdle = Boolean.TRUE;
    private FtpClientConfig config;
    private FTPClient client = new FTPClient();

    public SimpleFtpClient(FtpClientConfig config) {
        this.config = config;
        connect();
    }

    private boolean connect() {
        log.info("connect to ftp server.hostname:[" + config.getServerIp() + "],port:[" + config.getPort() + "],username:[" + config.getUsername() + "],password:[" + config.getPassword() + "]");
        try {
            try {
                client.configure(getFTPClientConfig());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // 连接服务器
            client.connect(config.getServerIp(), config.getPort());
            // 设置传输编码
            client.setControlEncoding("UTF-8");
            client.enterLocalPassiveMode();
            /** 日志输出 */
            client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
//            client.setFileType(FTPClient.BINARY_FILE_TYPE);
//            client.setControlEncoding("GBK");

            // 设置客户端操作系统类型，为windows 其实就是"WINDOWS" 虽然没用到
//            FTPClientConfig conf = new FTPClientConfig(ConfigInfo.getSystem());
            // 设置服务器端语言 中文 "zh"
//            conf.setServerLanguageCode(ConfigInfo.getServerLanguageCode());

            System.out.println(client.getReplyCode());
            // 判断服务器返回值，验证是否已经连接上
            if (FTPReply.isPositiveCompletion(client.getReplyCode())) {
                // 验证用户名密码
                if (client.login(config.getUsername(), config.getPassword())) {
                    client.changeWorkingDirectory("/public");
                    System.out.println("----a" + client.printWorkingDirectory());
                    log.info("ftp server is connected......");
                    System.out.println("已经连接到ftp......");
                    return true;
                }
                log.error("连接ftp的用户名或者密码错误......");
                // 取消连接
//                disconnect();

            } else {
                System.out.println("else.......");
            }
        } catch (SocketException e) {
            log.error("连接不上ftp....", e);
        } catch (IOException e) {
            log.error("出现io异常....", e);
        }
        return false;
    }

    /**
     * 配置FTP连接参数
     *
     * @return
     * @throws Exception
     */
    public FTPClientConfig getFTPClientConfig() throws Exception {

        String systemKey = FTPClientConfig.SYST_UNIX;
        String serverLanguageCode = "zh";
        FTPClientConfig conf = new FTPClientConfig(systemKey);
        conf.setServerLanguageCode(serverLanguageCode);
        conf.setDefaultDateFormatStr("yyyy-MM-dd");

        return conf;
    }


    @Override
    public boolean upload(String localPath, String localFileName, String remotePath) {
        try {
            return this.upload(new FileInputStream(localPath + localFileName), remotePath, localFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean upload(String localPath, String localFileName, String remotePath, String newFileName) {
        try {
            return this.upload(new FileInputStream(localPath + localFileName), remotePath, newFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean upload(InputStream in, String remotePath, String newFileName) {
        try {
            return client.storeFile(remotePath + newFileName, in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean download(String file, String localPath) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }
}
