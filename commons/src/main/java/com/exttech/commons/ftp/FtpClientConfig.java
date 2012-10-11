package com.exttech.commons.ftp;

/**
 * //TODO
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/8/12
 * Time: 2:45 PM
 */
public class FtpClientConfig {
    private String serverIp;
    private int port;
    private String username;
    private String password;
    private int poolSize;
    private String location;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
