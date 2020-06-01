package com.cc.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.cc.pojo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author cc
 * @Date 2020/4/23 9:27
 * @Version 1.0
 */
@Api(tags = {"excel导入导出"})
@RestController
public class HutoolController {

    @ApiOperation(value = "导出excel")
    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws UnsupportedEncodingException {

        List<Student> list = new ArrayList<>();
        list.add(new Student(21,"1231","男"));
        list.add(new Student(22,"1231","男"));
        list.add(new Student(23,"1231","男"));
        list.add(new Student(24,"1231","男"));

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("birthDay", "生日");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(2, "申请人员信息");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String name = "申请学院";
        name = java.net.URLEncoder.encode(name, "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+name+".xls");
        ServletOutputStream out= null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭writer，释放内存
            writer.close();
        }
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    @ApiOperation(value = "导出excel")
    @RequestMapping("/export1")
    public void export1(HttpServletResponse response) throws IOException {

        List<Student> list = new ArrayList<>();
        list.add(new Student(21,"1231","男"));
        list.add(new Student(22,"1231","男"));
        list.add(new Student(23,"1231","男"));
        list.add(new Student(24,"1231","男"));

        String[] columnNames={"id","name","sex"};
        String[] keys={"编号","姓名","性别"};
        String name = "申请学院";
        ExcelUtils.export(response,name,list,columnNames,keys);

    }

    @ApiOperation(value = "导入excel")
    @RequestMapping("/leading")
    public void leading(MultipartFile file) throws IOException {
        List<Map<String, Object>> readAll = ExcelUtils.leading(file, null);
        System.out.println(readAll);
        for (Map<String,Object> s:readAll) {
            for (String k : s.keySet())
            {
                System.out.println("基本信息："+k + " : " + s.get(k));
            }
        }

        List<Map<String, Object>> readAlls = ExcelUtils.leading(file, "教育经历");
        System.out.println(readAlls);
        for (Map<String,Object> s:readAlls) {
            for (String k : s.keySet())
            {
                System.out.println("教育经历："+k + " : " + s.get(k));
            }
        }
    }
}
