package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.ExcelUtils;
import com.example.demo.vo.BusClick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author cc
 * @Date 2020/7/24 9:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel()  throws IOException {
        List<BusClick> resultList = new ArrayList<BusClick>();
        BusClick busClick = new BusClick();
        busClick.setCityCode("a1");
        busClick.setClientVer("a2");
        busClick.setDate("a3");
        busClick.setMarkId("a4");
        busClick.setToaluv("a5");
        resultList.add(busClick);

        busClick = new BusClick();
        busClick.setCityCode("b1");
        busClick.setClientVer("b2");
        busClick.setDate("b3");
        busClick.setMarkId("b4");
        busClick.setToaluv("b5");
        resultList.add(busClick);

        long t1 = System.currentTimeMillis();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        ExcelUtils.writeExcel(response, resultList, BusClick.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    public void readExcel(@RequestParam(value="uploadFile", required = false) MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<BusClick> list = ExcelUtils.readExcel("", BusClick.class, file);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        list.forEach(
                b -> System.out.println(JSON.toJSONString(b))
        );
    }

}
