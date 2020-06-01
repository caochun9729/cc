package com.cc.controller;

import com.cc.constant.Constant;
import com.cc.constant.Response;
import com.cc.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
public class FileController {
    /**
     * 实现文件上传
     * */
    @RequestMapping("fileUpload")
    @ResponseBody
    public Response fileUpload(@RequestParam("fileName") MultipartFile file){
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
     * 实现文件上传
     * */
    @RequestMapping("fileUpload1")
    @ResponseBody
    public Response fileUpload1(@RequestParam("fileName") MultipartFile file){
        return FileUtils.fileUpload(file);
    }

    /**
     * 实现多文件上传
     * */
    @RequestMapping(value="multifileUpload1",method= RequestMethod.POST)
    @ResponseBody
    /**public @ResponseBody String multifileUpload(@RequestParam("fileName")List<MultipartFile> files) */
    public Response multifileUpload1(HttpServletRequest request){
        return  FileUtils.multifileUpload(request);
    }


    @GetMapping("/download1")
    @ResponseBody
    public Response downLoad1(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "97d79c1ceb2b469797458fad53ed61df.jpeg";
        return FileUtils.downLoad(response,filename);
    }
}
