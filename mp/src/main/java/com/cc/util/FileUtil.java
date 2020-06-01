package com.cc.util;

import java.io.*;
import java.sql.Clob;

public class FileUtil {

    /**
     * 读取文件内容返回成字符串
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getContentByFile(File file) throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
		  // 使用readLine方法，一次读一行
		  while ((s = br.readLine()) != null)
			  result.append(System.lineSeparator() + s);
            br.close();
        } catch (IOException e) {
            throw new Exception("文件读取异常！", e);
        }
        return result.toString();
    }

    /**
     * Clob类型 转String
     *
     * @param clob
     * @return
     */
    public static String ClobToString(Clob clob) {
        String ret = "";
        Reader read;
        try {
            read = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(read);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
            ret = sb.toString();
            if (br != null)
			br.close();
            if (read != null)
			read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 将字符串存储为一个文件，当文件不存在时候，自动创建该文件，当文件已存在时候，重写文件的内容，特定情况下，还与操作系统的权限有关。
     *
     * @param text     字符串
     * @param fileName 文件名称
     * @return
     */
    public static File getFileByContent(String text, String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new StringReader(text));
            bw = new BufferedWriter(new FileWriter(file));
            char buf[] = new char[1024 * 64]; // 字符缓冲区
            int len;
            while ((len = br.read(buf)) != -1)
			bw.write(buf, 0, len);
            bw.flush();
            br.close();
            bw.close();
        } catch (IOException e) {
            throw new Exception("写入文件错误！", e);
        }
        return file;
    }
}
