package com.cc.hutool;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/4/23 10:14
 * @Version 1.0
 */
public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static List<List<Object>> lineList = new ArrayList<>();

    /**
     * excel 导出工具类
     *
     * @param response
     * @param fileName    文件名
     * @param projects    对象集合
     * @param columnNames 导出的excel中的列名
     * @param keys        对应的是对象中的字段名字
     * @throws IOException
     */
    public static void export(HttpServletResponse response, String fileName, List<?> projects, String[] columnNames, String[] keys) throws IOException {

        ExcelWriter bigWriter = ExcelUtil.getBigWriter();

        for (int i = 0; i < columnNames.length; i++) {
            bigWriter.addHeaderAlias(columnNames[i], keys[i]);
            bigWriter.setColumnWidth(i, 20);
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        bigWriter.write(projects, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        bigWriter.flush(out, true);
        // 关闭writer，释放内存
        bigWriter.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     * excel导入工具类
     *
     * @param file       文件
     * @param sheetName sheet名称
     * @return 返回数据集合
     * @throws IOException
     */
    public static List<Map<String, Object>> leading(MultipartFile file, String sheetName) throws IOException {
        String fileName = file.getOriginalFilename();
        // 上传文件为空
        if (StringUtils.isEmpty(fileName)) {
            throw new IOException( "没有导入文件");
        }
        //上传文件大小为1000条数据
        if (file.getSize() > 1024 * 1024 * 10) {
            logger.error("upload | 上传失败: 文件大小超过10M，文件大小为：{}", file.getSize());
            throw new IOException( "上传失败: 文件大小不能超过10M!");
        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new IOException("文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }

        if(StringUtils.isBlank(sheetName)){
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<Map<String,Object>> readAll = reader.read(0,1,Integer.MAX_VALUE);
            return readAll;
        }else{
            /**
             * 读取sheet内容
             */
            ExcelReader reader1 = ExcelUtil.getReader(file.getInputStream(), "教育经历");
            /**
             * headerRowIndex 标题所在行，如果标题行在读取的内容行中间，这行做为数据将忽略
             * @param startRowIndex 起始行（包含，从0开始计数）
             */
            List<Map<String,Object>> readAlls = reader1.read(0,1,Integer.MAX_VALUE);
            return readAlls;
        }

    }

}