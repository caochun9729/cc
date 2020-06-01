package com.cc.util;

import javax.sound.midi.Soundbank;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * @Author cc
 * @Date 2020/4/15 9:15
 * @Version 1.0
 * set集合排序
 */
public class SetSortDemo {
    public static void main(String[] args) {


        /*
            实现Comparator<T>接口
            可以通过创建匿名内部类的方式  (如果排序功能只用一次可以通过此方法)
            还可以通过创建实现类的方式
         */
        TreeSet<Integer> treeSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;   //改写排序规则，降序排序
            }
        });
        treeSet.add(2);
        treeSet.add(1);
        treeSet.add(-9);
        treeSet.add(12);
        treeSet.add(8);

        System.out.println(treeSet);
        for(Integer sort:treeSet){
            System.out.println(sort);
        }
    }

}
