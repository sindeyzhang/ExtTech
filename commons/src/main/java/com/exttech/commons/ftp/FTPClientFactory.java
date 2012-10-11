package com.exttech.commons.ftp;


import com.exttech.commons.ftp.impl.SimpleFtpClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * //TODO
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/9/12
 * Time: 3:34 PM
 */
public abstract class FTPClientFactory {
    private FtpClientPool clientPool;

    private String serverIP;

    private int port;

    private String username;

    private String password;

    private int poolSize;

    FTPClientFactory() {
        FtpClientConfig config = new FtpClientConfig();
        config.setServerIp(serverIP);
        config.setPort(port);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolSize(poolSize);
        clientPool = new FtpClientPool(config);
    }

    public FtpClientInf getFtpClient() {
        return clientPool.getFtpClient();
    }


    private static class FtpClientPool {
        private List<SimpleFtpClient> ftpClients;
        private FtpClientConfig config;
        private int idleCount = 0;
        private final Lock lock = new ReentrantLock();
        private static final int reconnectCount = 3;

        public FtpClientPool(FtpClientConfig config) {
            this.config = config;
            idleCount = config.getPoolSize();
            this.ftpClients = new LinkedList<SimpleFtpClient>();
        }

        public FtpClientInf getFtpClient() {
            lock.lock();
            try {
                if (idleCount > 0) {
                    return getIdleClient();
                } else {
                    while (Boolean.TRUE) {
                        int connectCount = 0;
                        if (connectCount > reconnectCount) {
                            throw new RuntimeException("Cannot connect to ftp server.");
                        }
                        FtpClientInf client = getIdleClient();
                        if (client == null) {
                            connectCount++;
                            sleep(1000L);

                        } else {
                            return client;
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
            return null;
        }

        private void sleep(long time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
            }
        }

        private FtpClientInf getIdleClient() {
            if (ftpClients.size() == 0) {
                SimpleFtpClient client = createSimpleFtpClient();
                client.setIdle(Boolean.FALSE);
                ftpClients.add(client);
                return client;
            } else if (ftpClients.size() > 0) {
                for (SimpleFtpClient client : ftpClients) {
                    if (client.isIdle()) {
                        client.setIdle(Boolean.FALSE);
                        return client;
                    }
                }
            }

            return null;
        }

        private SimpleFtpClient createSimpleFtpClient() {
            return new SimpleFtpClient(config);
        }


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

    public String getServerIP() {

        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
