package com.exttech.commons.ftp;

import java.io.InputStream;

/**
 * //TODO
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/9/12
 * Time: 3:35 PM
 */
public interface FtpClientInf {
    boolean upload(String localPath, String localFileName, String remotePath);

    boolean upload(String localPath, String localFileName, String remotePath, String newFileName);

    boolean upload(InputStream in, String remotePath, String newFileName);

    boolean download(String file, String localPath);
}
