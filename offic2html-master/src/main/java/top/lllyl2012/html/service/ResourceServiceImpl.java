package top.lllyl2012.html.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import top.lllyl2012.html.utils.Byte2InputStream;
import top.lllyl2012.html.utils.File2HtmlUtil;
import top.lllyl2012.html.utils.File2byte;
import top.lllyl2012.html.utils.LogicUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.nio.file.Paths;

/**
 * @Author: volume
 * @CreateDate: 2019/6/15 11:27
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private final String DOC = ".doc";
    private final String DOCX = ".docx";
    private final String PDF = ".pdf";
    private final String PNG = ".png";
    private final String JPG = ".jpg";
    private final String JPEG = ".jpeg";
    private final String GIF = ".gif";
    private final String XLS = ".xls";
    private final String XLSX = ".xlsx";
    private final String MP4 = ".mp4";
    private final String OGV = ".ogv";
    private final String WEBM = ".webm";


    private final ResourceLoader resourceLoader;
    private static Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    public ResourceServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void toHtml(HttpServletResponse res) {
        res.setContentType("text/html");

        FileInputStream fis = null;
        try {
            //获得本地的文件流
            //String fileName = "1.mp4";
            //String fileName = "1.xlsx";
            //String fileName = "a.docx";
            //File file = ResourceUtils.getFile("classpath:static/"+fileName);
            //byte[] data = File2byte.getBytes(file);
            //预览ftp上的文件
            //String fileName = "b1437e6c60844574a9c0a8bcee5f8dc5.xlsx";
            //String fileName="136a6b7256b14e268456f4526e1cc431.png";
            String fileName = "b1437e6c60844574a9c0a8bcee5f8dc5.xlsx";
            byte[] data = getByte();


            int suffixIndex = fileName.lastIndexOf(".");

            if (DOC.equals(fileName.substring(suffixIndex))) {
                File2HtmlUtil.word2003ToHtml(data, res);
            } else if (DOCX.equals(fileName.substring(suffixIndex))) {
                File2HtmlUtil.word2007ToHtml(data, res);
            } else if (PDF.equals(fileName.substring(suffixIndex))) {
                File2HtmlUtil.pdf2Html(data, res);
            } else if (PNG.equals(fileName.substring(suffixIndex)) || JPG.equals(fileName.substring(suffixIndex))
                    || JPEG.equals(fileName.substring(suffixIndex)) || GIF.equals(fileName.substring(suffixIndex))) {
                File2HtmlUtil.image2Html(data, res);
            } else if (XLS.equals(fileName.substring(suffixIndex))) {
                InputStream in = new ByteArrayInputStream(data);
                File2HtmlUtil.PoiExcelToHtml(in, res);
            } else if (XLSX.equals(fileName.substring(suffixIndex))) {
                InputStream in = new ByteArrayInputStream(data);
                File2HtmlUtil.ExcelToHtml(in, res);
            } else if (MP4.equals(fileName.substring(suffixIndex)) || OGV.equals(fileName.substring(suffixIndex)) || WEBM.equals(fileName.substring(suffixIndex))) {
                //视频页面预览和上面有些不一样，上面都是把文件转换成二进制或者流，而这个因为用了html5的<video/>标签，所以参数filePath得是能直接获取到视频的链接
                String filePath = "http://localhost:8989/video?file=" + fileName;
                File2HtmlUtil.mp42Html(res, filePath);
            } else {//如果文件类型上面没有，就直接下载
                res.setHeader("content-type", "multipart/form-data");
                res.setHeader("Content-Disposition", "attachment;filename=" +
                        new String(fileName.getBytes("UTF-8"), "ISO8859-1"));

                try (OutputStream os = res.getOutputStream()) {
                    if (LogicUtil.isNotEmpty(data)) {
                        os.write(data);
                    }
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<?> showPhotoDoc(HttpServletResponse response, String photo) {
        String root = File2HtmlUtil.getRoot() + "/image/word/media";
        Resource resource = resourceLoader.getResource("file:" + Paths.get(root, photo));
        return ResponseEntity.ok(resource);
    }


    public static void main(String[] args) throws Exception {

        FTPClient ftp = getFTPClient("192.168.31.209", "ftp", "qq.123",
                21);
        ftp.setControlEncoding("GBK");//支持中文名文件下载
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory("admin/");// 转移到FTP服务器目录
        FTPFile[] fs = ftp.listFiles();
        for (FTPFile ff : fs) {
            if (ff.getName().equals("b1437e6c60844574a9c0a8bcee5f8dc5.xlsx")) {
                //下载文件
                OutputStream os = new FileOutputStream(new File("d:/test" + "/" + "ck1.xlsx"));
                if (ftp.retrieveFile(new String(ff.getName().getBytes("GBK"), "iso-8859-1"), os)) {
                    //文件名编码 和上传的编码一致
                    System.out.println("下载成功");
                }
            }
        }
        ftp.logout();
        ftp.disconnect();

    }

    public byte[] getByte() throws IOException {
        FTPClient ftp = getFTPClient("192.168.31.209", "ftp", "qq.123",
                21);
        ftp.setControlEncoding("GBK");//支持中文名文件下载
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.changeWorkingDirectory("admin/");// 转移到FTP服务器目录
        FTPFile[] fs = ftp.listFiles();
        InputStream gbk = ftp.retrieveFileStream(new String("b1437e6c60844574a9c0a8bcee5f8dc5.xlsx".getBytes("GBK"), "iso-8859-1"));
        byte[] bytes = Byte2InputStream.inputStream2byte(gbk);
        ftp.logout();
        ftp.disconnect();
        return bytes;
    }


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

}
