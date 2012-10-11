package com.exttech.commons.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.InputStream;
import java.util.Properties;

/**
 * 基于apache组织的commonNet开源组件实现ftp客户端<br>
 * 本程序运行依赖于一个config成员变量属性,其是一个属性(Properties)对象<br>
 * 系统要求这个config对象,必需有如下属性key:<br>
 * server(ftp服务器ip地址或域名)<br>
 * username(登录用户名)<br>
 * password(登录密码)<br>
 * 如下属性key是可选的:<br>
 * systemKey(ftp服务器的操作系统key,如window)<br>
 * serverLanguageCode(ftp服务器使用的语言,如zh)<br>
 * <br>
 * 本程序是线程安全,在每一个上传时都将创建ftp连接,上传结束后再关闭ftp连接<br>
 * <p/>
 * 例子:<br>
 * InputStream localIn = new FileInputStream("c:\\计算机.txt");<br>
 * <p/>
 * java.util.Properties config = new Properties();<br>
 * config.setProperty("server","192.168.5.120");<br>
 * config.setProperty("username","upload");<br>
 * config.setProperty("password","upload");<br>
 * <p/>
 * FtpClientCommonNetImpl client = new FtpClientCommonNetImpl();<br>
 * client.setConfig(config);<br>
 * client.upload(localIn,"/aaa/计算机.txt", true);<br>
 *
 * @author zhangdb
 */
public class FtpUtil {
    //  ftp配置
    private Properties config;

    /**
     * 连接到ftp服务器
     *
     * @param server
     * @param userName
     * @param password
     * @return
     */
    protected FTPClient connectFtpServer(String server, String userName,
                                         String password) {
//  创建ftp客户端对象
        FTPClient ftp = new FTPClient();
        try {
            ftp.configure(this.getFTPClientConfig());
//  连接ftp服务器
            ftp.connect("127.0.0.1", 21);
//  登录
            ftp.login("ftpuser1", "ftpuser1");

//  返回值
            int reply = ftp.getReplyCode();
            System.out.println("reply==" + reply);
            if ((!FTPReply.isPositiveCompletion(reply))) {
                ftp.disconnect();
                throw new RuntimeException("登录ftp服务器失败,请检查server[" + server
                        + "]、username[" + userName + "]、password[" + password
                        + "]是否正确!");
            }

            return ftp;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 关闭连接
     *
     * @param ftp
     */
    protected void disconnectFtpServer(FTPClient ftp) {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 上传
     */
    public void upload(InputStream localIn, String remoteFilePath) {
        String server = this.config.getProperty("server");
        String userName = this.config.getProperty("username");
        String password = this.config.getProperty("password");
        FTPClient ftp = this.connectFtpServer(server, userName, password);

        try {
            boolean result = ftp.storeFile(this
                    .enCodingRemoteFilePath(remoteFilePath), localIn);
            if (!result) {
                throw new RuntimeException("文件上传失败!");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            this.disconnectFtpServer(ftp);
        }
    }

    /**
     * 上传结束以后关闭输入流
     *
     * @param localIn
     * @param remoteFilePath
     * @param afterUploadCloseInputStream
     */
    public void upload(InputStream localIn, String remoteFilePath,
                       boolean afterUploadCloseInputStream) {
        try {
//  上传
            this.upload(localIn, remoteFilePath);
        } finally {
            if (afterUploadCloseInputStream) {
                if (localIn != null) {
                    try {
                        localIn.close();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    /**
     * 得到配置
     *
     * @return
     */
    protected FTPClientConfig getFTPClientConfig() {
//  创建配置对象
        FTPClientConfig conf = new FTPClientConfig(this.config.getProperty(
                "systemKey", FTPClientConfig.SYST_NT));
        conf.setServerLanguageCode(this.config.getProperty(
                "serverLanguageCode", "zh"));
        return conf;
    }

    /**
     * 远程文件路径编码(上传到ftp上的文件路径)
     *
     * @param remoteFilePath
     * @return
     */
    protected String enCodingRemoteFilePath(String remoteFilePath) {
//        return  StringUtils.gbkToIso8859EnCoding(remoteFilePath);
        return remoteFilePath;
    }

    public Properties getConfig() {
        return config;
    }

    public void setConfig(Properties config) {
        this.config = config;
    }

}