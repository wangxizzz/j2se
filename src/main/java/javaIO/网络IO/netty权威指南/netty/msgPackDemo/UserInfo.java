package javaIO.网络IO.netty权威指南.netty.msgPackDemo;

import org.msgpack.annotation.Message;

/**
 * Created by wangxi on 09/06/2018.
 */
@Message
public class UserInfo {
    private String age;
    private String name;

    public UserInfo() {
    }

    public UserInfo(String age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
