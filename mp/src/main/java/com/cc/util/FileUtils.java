package com.cc.util;

import com.cc.constant.Constant;
import com.cc.constant.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class FileUtils {
    /**
     * 实现文件上传
     * */
    public static Response fileUpload(MultipartFile file){
        if(file.isEmpty()){
            return new Response(500,"上传失败");
        }
        String fileName = file.getOriginalFilename();
        //获取文件名称
        String originalFileName = fileName
                .substring(0, fileName.lastIndexOf("."));
        //获取文件名后缀
        String suffix = fileName
                .substring(fileName.lastIndexOf("."));
        String newFileName = originalFileName
                + "_" + UUID.randomUUID().toString() + suffix;
        int size = (int) file.getSize();
        System.out.println(newFileName + "-->" + size);

        String path = Constant.UPLOAD_DIRECTORY;

        File dest = new File(path + "/" + newFileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return new Response(200,"上传成功");
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new Response(500,"上传失败");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new Response(500,"上传失败");
        }

    }

    /**
     * 实现多文件上传
     * */
    /**public @ResponseBody String multifileUpload(@RequestParam("fileName")List<MultipartFile> files) */
    public static Response multifileUpload(HttpServletRequest request){

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");

        if(files.isEmpty()){
            return new Response(500,"上传失败");
        }

        String path = Constant.UPLOAD_DIRECTORY;

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            //获取文件名称
            String originalFileName = fileName
                    .substring(0, fileName.lastIndexOf("."));
            //获取文件名后缀
            String suffix = fileName
                    .substring(fileName.lastIndexOf("."));
            String newFileName = originalFileName
                    + "_" + UUID.randomUUID().toString() + suffix;
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return new Response(500,"上传失败");
            }else{
                File dest = new File(path + "/" + newFileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return new Response(500,"上传失败");
                }
            }
        }
        return new Response(200,"上传成功");
    }


    public static Response downLoad(HttpServletResponse response,String filename) throws UnsupportedEncodingException {
        String filePath = Constant.UPLOAD_DIRECTORY;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return  new Response(500,"下载失败");
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return  new Response(500,"下载失败");
            }
        }
        return new Response();
    }
}
