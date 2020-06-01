package com.cc.util;

import java.io.*;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

/**
 * @author package com.genomics.ib.item.control
 * @title ItemFtp
 * @Description : FTP 上传下载工具类
 */
public class ItemFtp {

    private FTPClient ftp;

    private static Logger logger = LoggerFactory.getLogger(ItemFtp.class);

    /**
     *
     * @param path
     *            上传到ftp服务器哪个路径下
     * @param addr
     *            地址
     * @param port
     *            端口号
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return
     * @throws Exception
     */
    /*
     * private boolean connect(String path,String addr,int port,String
     * username,String password) throws Exception { boolean result = false; ftp
     * = new FTPClient(); int reply; ftp.connect(addr,port);
     * ftp.login(username,password);
     * ftp.setFileType(FTPClient.BINARY_FILE_TYPE); reply = ftp.getReplyCode();
     * if (!FTPReply.isPositiveCompletion(reply)) { ftp.disconnect(); return
     * result; } ftp.changeWorkingDirectory(path); result = true; return result;
     * }
     */

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    public static void main(String[] args) throws Exception {

        FTPClient ftp = getFTPClient("192.168.80.128", "ftpuser", "FtpQq.123456",
                21);
        ftp.setControlEncoding("GBK");//支持中文名文件下载
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory("/opt/ftp/");// 转移到FTP服务器目录
        FTPFile[] fs = ftp.listFiles();
        for (FTPFile ff : fs) {
            if (ff.getName().equals("ck1.xlsx")) {
                //下载文件
                OutputStream os = new FileOutputStream(new File("d:/test" + "/" + "ck1.xlsx"));
               /* try {
                    InputStream is = ftp.retrieveFileStream(new String(ff.getName().getBytes("GBK"), "ISO-8859-1"));
                    byte[] bytes = FileCopyUtils.copyToByteArray(is);
                    String s = new String(bytes, "ISO-8859-1");
                    System.out.println(s);
                }catch (Exception ex){
                    throw new RuntimeException("transform file into bin String 出错",ex);
                }*/

                InputStream is = ftp.retrieveFileStream(new String(ff.getName().getBytes("GBK"), "ISO-8859-1"));
                System.out.println(is);
                byte[] read = read(is);
                if (ftp.retrieveFile(new String(ff.getName().getBytes("GBK"), "iso-8859-1"), os)) {
                    //文件名编码 和上传的编码一致
                    System.out.println("下载成功");
                }
            }
        }
        ftp.logout();
        ftp.disconnect();

    }

    public static byte[] read(InputStream is) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = is.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = is.read(buffer);
            }
            baos.flush();
            byte[] bytes = baos.toByteArray();
            System.out.println(bytes);
            String s = new String(bytes, "ISO-8859-1");
            System.out.println(s);
            return bytes;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * <b>将一个IO流解析，转化数组形式的集合<b>
     * <p>
     * 文件inputStream流
     */
	/*public static ArrayList<String[]> csv(InputStream in) {
		ArrayList<String[]> csvList = new ArrayList<String[]>();
		if (null != in) {
			CsvReader reader = new CsvReader(in, ',', Charset.forName("UTF-8"));
			try {
				// 遍历每一行，若有#注释部分，则不处理，若没有，则加入csvList
				while (reader.readRecord()) {
					if (!reader.getValues()[0].contains("#"))// 清除注释部分
					{
						// 获取的为每一行的信息，以数组的形式
						csvList.add(reader.getValues());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			reader.close();
		}
		return csvList;
	}*/

    /*
     * 从FTP服务器下载文件
     *
     * @param ftpHost FTP IP地址
     *
     * @param ftpUserName FTP 用户名
     *
     * @param ftpPassword FTP用户名密码
     *
     * @param ftpPort FTP端口
     *
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     *
     * @param localPath 下载到本地的位置 格式：H:/download
     *
     * @param fileName 文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                       String ftpPath, String localPath, String fileName) {

        FTPClient ftpClient = null;

        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            //设置文件编码类型为二进制文件
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

            File localFile = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(localFile);
            //boolean b = ftpClient.retrieveFile(fileName, os);
            boolean b = ftpClient.retrieveFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), os);

            System.out.println(b);
            os.close();
            ftpClient.logout();

        } catch (FileNotFoundException e) {
            logger.info("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            logger.info("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件读取错误。");
            e.printStackTrace();
        }

    }

    /*
     * 从FTP服务器下载文件(网页)
     *
     * @param ftpHost FTP IP地址
     *
     * @param ftpUserName FTP 用户名
     *
     * @param ftpPassword FTP用户名密码
     *
     * @param ftpPort FTP端口
     *
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     *
     * @param fileName 文件名称
     */
    public static Boolean downloadFtpFileForWeb(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                                String ftpPath, String saveAsFileName, HttpServletResponse response) {
        FTPClient ftpClient = null;
        Boolean flag = false;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);

            ftpClient.setControlEncoding("GBK");//支持中文名文件下载
            //ftpClient.sendCommand("OPTS UTF8", "ON");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
				/*byte[] bytes = ff.getName().getBytes("iso-8859-1");
				ff.setName(new String(bytes, "UTF-8"));*/
                //解决中文名称
                if (ff.getName().equals(saveAsFileName)) {
                    response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(saveAsFileName, "UTF-8"));
                    //下载文件
                    OutputStream os = response.getOutputStream();

                    if (ftpClient.retrieveFile(new String(ff.getName().getBytes("GBK"), "iso-8859-1"), os)) {
                        //文件名编码 和上传的编码一致
                        flag = true;
                        logger.info("ftp下载成功");
                    } else {
                        logger.info("ftp下载失败");
                    }
                    os.flush();
                    os.close();
                }
            }
            ftpClient.logout();
            ftpClient.disconnect();

        } catch (FileNotFoundException e) {
            logger.info("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            logger.info("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件读取错误。");
            e.printStackTrace();
        }
        return flag;
    }

    public static Boolean downloadFtpZipFileForWeb(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                                   String ftpPath, List<String> saveAsFileNames, HttpServletResponse response, List<String> list1) {
        FTPClient ftpClient = null;
        Boolean flag = true;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);

            ftpClient.setControlEncoding("GBK");//支持中文名文件下载
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            ZipOutputStream zip = null;
            try {
                String zipName = "new.zip";
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
                //获取到zip输出流
                zip = new ZipOutputStream(response.getOutputStream());
                FTPFile[] fs = ftpClient.listFiles();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                    ftpClient = null;
                }
                for (FTPFile ff : fs) {
                    for (int i = 0; i < saveAsFileNames.size(); i++) {
                        if (ff.getName().equals(saveAsFileNames.get(i))) {
                            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
                            ftpClient.setControlEncoding("GBK");//支持中文名文件下载
                            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                            ftpClient.enterLocalPassiveMode();
                            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
                            InputStream is = ftpClient.retrieveFileStream(new String(saveAsFileNames.get(i).getBytes("GBK"), "ISO-8859-1"));
                            //添加进压缩包时重命名
                            doCompress(is, list1.get(i), zip);
                        }
                    }
                }
                response.flushBuffer();
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            } finally {
                if (zip != null) {
                    try {
                        zip.close();
                    } catch (IOException e) {
                    }
                }
            }
            ftpClient.logout();
            ftpClient.disconnect();

        } catch (FileNotFoundException e) {
            flag = false;
            logger.info("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            flag = false;
            logger.info("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            logger.info("文件读取错误。");
            e.printStackTrace();
        }
        return flag;
    }

    public static void doCompress(InputStream fis, String fileName, ZipOutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        out.putNextEntry(new ZipEntry(fileName));
        int len = 0;
        // 读取文件的内容,打包到zip文件
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param ftpHost     FTP服务器hostname
     * @param ftpUserName 账号
     * @param ftpPassword 密码
     * @param ftpPort     端口
     * @param ftpPath     FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName    ftp文件名称
     * @param input       文件流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                     String ftpPath, String fileName, InputStream input, String newFile) {
        boolean result = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return result;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //创建文件夹
            mkDir(ftpClient, newFile);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            //解决中文名称
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
            boolean b = ftpClient.storeFile(fileName, input);
            System.out.println(b);
            if (b) {
                result = true;
            } else {
                result = false;
            }
            input.close();
            ftpClient.logout();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }


    public static boolean uploadFiles(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                      String ftpPath, List<String> fileNames, List<InputStream> inputs, String newFile) {
        boolean result = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return result;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            //ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            //创建文件夹
            mkDir(ftpClient, newFile);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            for (int i = 0; i < fileNames.size(); i++) {
                String fileName = fileNames.get(i);
                InputStream input = inputs.get(i);
                //解决中文名称
                fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
                boolean b = ftpClient.storeFile(fileName, input);
                System.out.println(b);
                if (b) {
                    result = true;
                } else {
                    result = false;
                }
                input.close();
            }

            ftpClient.logout();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param ftpHost
     * @param ftpUserName
     * @param ftpPassword
     * @param ftpPort
     * @param ftpPath
     * @param saveAsFileName
     * @return
     * @throws IOException
     */
    public static Boolean deleteFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                     String ftpPath, String saveAsFileName) throws IOException {
        boolean result = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return result;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            boolean b = ftpClient.deleteFile(saveAsFileName);
            if (!b) {
                result = false;
            } else {
                result = true;
            }
            ftpClient.logout();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * 选择上传的目录，没有创建目录
     *
     * @param ftpPath 需要上传、创建的目录
     * @return
     */
    public static boolean mkDir(FTPClient ftpClient, String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            // System.out.println("ftpPath:" + ftpPath);
            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                ftpClient.makeDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
