package com.cc.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:汉字转拼音
 * @author: cc
 * @time: 2019/12/18 17:03
 */
public class KanjiToPinyin {

    private KanjiToPinyin() {
    }


    /**
     * @param users 人员信息
     * @return 类似通讯录形式人员信息
     * @Author: wzw
     * @Date: 2019/12/19 9:17
     */
    public static Map<String, Object> getAddressBookStyle(List<Map<String, Object>> users) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //获得所有人员包含不显示的人
        Map<String, Object> userMap = new HashMap<>(27);

        if (!CollectionUtils.isEmpty(users))
            for (int i = 0; i < users.size(); i++) {
                String realName = (String) users.get(i).get("REALNAME");
                if (!"".equals(realName)) {
                    char c = KanjiToPinyin.getFirstSpell(realName).charAt(0);//获得首字母
                    String first = String.valueOf(c).toUpperCase();
                    boolean contains = str.contains(first);
                    Object o = userMap.get(first);
                    if (!contains && userMap.get("#") == null) {
                        List<Map<String, Object>> Map1 = new ArrayList<>();
                        userMap.put("#", Map1);
                    }
                    if (o != null && contains) {
                        //获得当前这条数据
                        List<Map<String, Object>> userMapObject = (List<Map<String, Object>>) userMap
                            .get(first);
                        Map<String, Object> map = users.get(i);
                        userMapObject.add(map);
                    }
                    if (!contains) {
                        List<Map<String, Object>> userMapObject = (List<Map<String, Object>>) userMap
                            .get("#");
                        Map<String, Object> map = users.get(i);
                        userMapObject.add(map);
                    }
                    if (o == null && contains) {
                        List<Map<String, Object>> userMapObject = new ArrayList<>();
                        Map<String, Object> map = users.get(i);
                        userMapObject.add(map);
                        userMap.put(first, userMapObject);
                    }
                }

            }
        return userMap;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++)
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

}
