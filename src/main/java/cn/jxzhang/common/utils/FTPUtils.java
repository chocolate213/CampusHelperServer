package cn.jxzhang.common.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * Created on 2017-01-12 10:41
 * <p>Title:       FTPMain</p>
 * <p>Description: [Description]</p>
 * <p>Company:     Ultrapower Co. Ltd.</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author <a href=zhangjiaxing@ultrapower.com.cn>J.X.Zhang</a>
 * @version 1.0
 */
public class FTPUtils {

    private FTPUtils() { /* cannot be instantiated */ }

    /**
     * Log
     */
    private static Logger LOG = LoggerFactory.getLogger(FTPUtils.class);

    /**
     * Upload file to FTP server
     *
     * @param host     The name of the remote host.
     * @param port     The port to connect to on the remote host.
     * @param username The username to login under.
     * @param password The password to use.
     * @param basePath The path already exist on the remote server, must start with '/'
     * @param filePath file path. e.g. filepath = '/2015/01/01', the real path on server = basePath + filePath, must
     *                 start with '/'
     * @param filename file name on remote server
     * @param input    file input stream
     * @return true if success
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath, String filePath, String filename, InputStream input) {

        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            //attempt to connect and login.
            ftp.connect(host, port);
            ftp.login(username, password);
            int reply = ftp.getReplyCode();

            //login success?
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                LOG.error("login remote server filed, error code : " + reply);
                return result;
            }

            //Create directory if not exist.
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            LOG.error("make directory filed : " + tempPath);
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }

            // Sets the file type to be transferred.
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // store file.
            if (!ftp.storeFile(filename, input)) {
                LOG.error("store file filed.");
                return result;
            }

            input.close();
            ftp.logout();

            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Download file from FTP server.
     *
     * @param host       The name of the remote host.
     * @param port       The port to connect to on the remote host.
     * @param username   The username to login under.
     * @param password   The password to use.
     * @param remotePath The remote file working directory.
     * @param fileName   download file name
     * @param localPath  local file path
     * @return true if success
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath, String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            //attempt to connect and login.
            ftp.connect(host, port);
            ftp.login(username, password);

            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                LOG.error("login remote server filed, error code : " + reply);
                ftp.disconnect();
                return result;
            }

            //Change the current working directory of the FTP session.
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "\\" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                    LOG.info("Download file success. local path : " + localPath + "\\" + ff.getName());
                    break;
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}