package com.cc.controller;

import com.cc.constant.Response;
import com.cc.util.ItemFtp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/file")
@Api(value = "文件上传下载",description = "文件上传下载")
public class FtpController {
    @Value("${ftp.address}")
    private String FTP_ADDRESS;
    @Value("${ftp.port}")
    private int FTP_PORT;
    @Value("${ftp.username}")
    private String FTP_USERNAME;
    @Value("${ftp.password}")
    private String FTP_PASSWORD;
    @Value("${ftp.filepath}")
    private String FTP_FILEPATH;

    @ApiOperation(value = "上传文件" ,notes="上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "file", required = true, paramType = "query",dataType = "MultipartFile"),
            @ApiImplicitParam(name = "userName", value = "userName", required = true, paramType = "query",dataType = "String")
    })
    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userName") String userName, HttpServletRequest request) throws IOException {
        //创建文件上传路径
        String savePath=FTP_FILEPATH+userName+"/";
        //每个用户有自己的文件夹
        String newFIle="/"+userName;
        File folder = new File(savePath);
        String fileName = file.getOriginalFilename();//获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取文件的后缀名
        fileName= UUID.randomUUID().toString().replace("-","")+suffixName;
        //上传的文件名加上后缀
        InputStream inputStream = file.getInputStream();
        ItemFtp.uploadFile(FTP_ADDRESS,FTP_USERNAME,FTP_PASSWORD,FTP_PORT, savePath,fileName,inputStream,newFIle);
        Map<String,Object> map=new HashMap<>();
        map.put("fileName",fileName);
        map.put("path",userName+"/");
        return new Response(200,"上传成功",map);
    }

    @ApiOperation(value = "上传文件" ,notes="上传文件")
    @PostMapping("/uploadFiles")
    public Response uploadFiles(@RequestParam("file") List<MultipartFile> files, @RequestParam("userName") String userName, HttpServletRequest request) throws IOException {
        //创建文件上传路径
        String savePath=FTP_FILEPATH+userName+"/";
        //每个用户有自己的文件夹
        String newFIle="/"+userName;
        File folder = new File(savePath);
        List<InputStream> inputs=new ArrayList<>();
        List<String> fileNames=new ArrayList<>();
        for(MultipartFile m:files){
            String fileName = m.getOriginalFilename();//获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取文件的后缀名
            fileName= UUID.randomUUID().toString().replace("-","")+suffixName;
            //上传的文件名加上后缀
            InputStream inputStream = m.getInputStream();
            inputs.add(inputStream);
            fileNames.add(fileName);
        }

        ItemFtp.uploadFiles(FTP_ADDRESS,FTP_USERNAME,FTP_PASSWORD,FTP_PORT, savePath,fileNames,inputs,newFIle);
        List<Map<String,Object>> maps=new ArrayList<>();
        for(String s:fileNames){
            Map<String,Object> map=new HashMap<>();
            map.put("fileName",s);
            map.put("path",userName+"/");
            maps.add(map);
        }

        return new Response(200,"上传成功",maps);
    }

    @ApiOperation(value = "下载文件" ,notes="下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "fileName", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "path", value = "path", required = true, paramType = "query",dataType = "String")
    })
    @GetMapping("/downloadFile")
    public Response downloadFile(@RequestParam("fileName") String fileName, @RequestParam("path") String path, HttpServletResponse response) throws IOException {

        Boolean aBoolean = ItemFtp.downloadFtpFileForWeb(FTP_ADDRESS, FTP_USERNAME, FTP_PASSWORD, FTP_PORT, path, fileName, response);
        if(aBoolean){
            return  new Response(200,"下载成功");
        }else{
            return  new Response(500,"下载失败");
        }

    }

    @ApiOperation(value = "删除文件" ,notes="删除文件")
    @ApiImplicitParam(name = "fileName", value = "fileName", required = true, paramType = "query",dataType = "String")
    @PostMapping("/deleteFile")
    public Response deleteFile(@RequestParam("fileName") String fileName, @RequestParam("path") String path,HttpServletResponse response) throws IOException {

        Boolean aBoolean = ItemFtp.deleteFile(FTP_ADDRESS, FTP_USERNAME, FTP_PASSWORD, FTP_PORT, path, fileName);
        if(aBoolean){
            return  new Response(200,"删除成功");
        }else{
            return  new Response(500,"删除失败");
        }

    }

    /**
     * 页面显示ftp图片
     * @param request
     * @param response
     */
    @GetMapping("/showPicture")
    public void showPicture(HttpServletRequest request, HttpServletResponse response){
        String resObjectId = "admin/";
        String fileName = "79d97090ccc449359ee076142504acd7.jpg";

        String ftppicturesavepath = "c:/alm/";
        if(ftppicturesavepath.startsWith("/")){
            ftppicturesavepath = ftppicturesavepath.replaceFirst("/", "");
        }
        if(!ftppicturesavepath.endsWith("/")){
            ftppicturesavepath = ftppicturesavepath+"/";
        }
        String picture_ftp_save_path = resObjectId;

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect("192.168.31.209", Integer.valueOf("21"));
            if (!ftpClient.login("ftp", "qq.123")) {
                System.out.println("ftp连接不上，请检查ftp服务器状态或连接ftp配置");
            }
            ftpClient.enterLocalPassiveMode();
            changWorkingDirectory(picture_ftp_save_path, ftpClient, true);
            ftpClient.setControlEncoding("GBK");
            FTPFile[] files = ftpClient.listFiles();
            FTPFile[] arr$ = files;
            int len$ = files.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                FTPFile file = arr$[i$];
                if (fileName.equalsIgnoreCase(file.getName())) {
                    ServletOutputStream output = null;
                    InputStream input = ftpClient.retrieveFileStream(file.getName());
                    output = response.getOutputStream();
                    response.setContentType("image/jpeg");
                    byte imageArray[] = new byte[4064];
                    int len = 0;
                    while((len = input.read(imageArray)) != -1){
                        output.write(imageArray, 0, len);
                    }
                    input.close();
                    output.flush();
                }
            }
        }catch (Exception e){
        } finally {
            if (ftpClient != null) {
                try {
                    ftpClient.disconnect();
                } catch (IOException var30) {
                }
            }

        }

    }


    private void changWorkingDirectory(String remotePath, FTPClient ftpClient, boolean isMakeDir) throws IOException {
        if (remotePath != null) {
            String[] rps = remotePath.split("/");

            for(int i = 0; i < rps.length; ++i) {
                if (!ftpClient.changeWorkingDirectory(rps[i])) {
                    if (!isMakeDir) {
                        throw new RuntimeException("找不到该目录:" + rps[i]);
                    }

                    ftpClient.makeDirectory(rps[i]);
                    ftpClient.changeWorkingDirectory(rps[i]);
                }
            }
        }

    }


    @ApiOperation(value = "下载文件" ,notes="下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "fileName", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "path", value = "path", required = true, paramType = "query",dataType = "String")
    })
    @GetMapping("/downloadZipFile")
    public Response downloadZipFile() throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response=servletRequestAttributes.getResponse();
        List<String> list=new ArrayList<>();
        list.add("fe2e9b5e8f884ed393df43b05ff4faf0.jpg");
        list.add("ck1.xlsx");
        list.add("alm.doc");
        list.add("alm要的.doc");
        list.add("test.jpg");
        List<String> list1=new ArrayList<>();
        list1.add("小米图片.jpg");
        list1.add("表格ck.xlsx");
        list1.add("滴滴滴.doc");
        list1.add("滴滴滴1.doc");
        list1.add("测试.jpg");
        Boolean aBoolean = ItemFtp.downloadFtpZipFileForWeb(FTP_ADDRESS, FTP_USERNAME, FTP_PASSWORD, FTP_PORT, "wuz22/", list, response,list1);
        if(aBoolean){
            return  null;
        }else{
            return  new Response(500,"下载失败");
        }
    }
}
