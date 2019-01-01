package com.me.coreJava;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reference {
    List<Integer> list = new ArrayList<>();

    @Test public void test() {
        list.add(1);
        setList(list); // 将 list 的内存地址传给 setList 方法

        Assert.assertEquals(list, Arrays.asList(1, 2));
    }

    private void setList(List<Integer> list) { //本地变量接收对象指针
        list.add(2); //操作指针所指向的对象
        list = Arrays.asList(1, 2, 3); //这个只是将 list 的指针切换到了新构造的 ArrayList 在堆上的地址
        list = null; //这个只是将 list 的指针置为空
    }

    class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test public void testUser() {
        User user = new User("Sally");
        setUser(user);
        Assert.assertEquals(user.getName(), "Bob");
    }

    private void setUser(User user) {
        user.setName("Bob");
        user = null;
    }

    @Test public void size() {
        System.out.println(Long.toBinaryString(1L << 10));
    }
}
