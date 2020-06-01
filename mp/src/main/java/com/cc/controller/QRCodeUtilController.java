package com.cc.controller;

import com.cc.constant.Response;
import com.cc.util.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;

@RequestMapping("/qrCode")
@Controller
public class QRCodeUtilController {
    @GetMapping("/qrCodeTest")
    public Response qrCodeTest(HttpServletResponse response) throws Exception {
        String text="帅纯";
        int width=500;
        int height=500;
        String format="png";
        QRCodeUtil.generateQRCode(text,width,height,format,response);
        return  new Response();
    }


    public static void main(String[] args) {
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        System.out.println(df.format((float)(3)/(10)));
        System.out.println(Float.parseFloat(df.format((float)(3)/(10))));
    }
}
