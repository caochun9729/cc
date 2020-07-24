package com.example.demo.vo;

import com.example.demo.annotation.ExcelColumn;
import lombok.Data;

/**
 * @Author cc
 * @Date 2020/7/24 10:15
 * @Version 1.0
 */
@Data
public class BusClick {
    @ExcelColumn(value = "地区编码", col = 1)
    private String cityCode;

    @ExcelColumn(value = "标志", col = 2)
    private String markId;

    @ExcelColumn(value = "toaluv", col = 3)
    private String toaluv;

    @ExcelColumn(value = "date", col = 4)
    private String date;

    @ExcelColumn(value = "clientVer", col = 5)
    private String clientVer;

    @Override
    public String toString() {
        return "BusClick [cityCode=" + cityCode + ", markId=" + markId + ", toaluv=" + toaluv + ", date=" + date
                + ", clientVer=" + clientVer + "]";
    }
}
