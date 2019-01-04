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


    @Test public void auto_boxing() {
        Integer A = 128; //Auto boxing uses valueOf of the object type
        Integer B = 128;

        // These are two different objects
        Assert.assertEquals(true, A != B);

        Integer C = 2;
        Integer D = 2;

        //In java, all the Integer objects that value between -128 to 127
        //will be cached. When a new Integer object which value belong to this
        //range and if there is an object has the same value. The object will
        //return. This can avoid creating a lot of un-useful objects and result
        //in GC.
        Assert.assertEquals(true, C == D);
    }
}
