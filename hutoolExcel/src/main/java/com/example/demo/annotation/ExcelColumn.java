package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @Author cc
 * @Date 2020/7/24 10:08
 * @Version 1.0
 * 自定义实体类所需要的bean(Excel属性标题、位置等)
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    /**
     * Excel标题
     *
     * @return
     * @author cc
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     *
     * @return
     * @author cc
     */
    int col() default 0;
}
