package 多线程.Java并发编程.day14无锁CAS操作以及Java中Atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:王喜
 * @Description :
 * @Date: 2018/4/29 0029 21:34
 */
public class AtomicReferenceDemo {
    public static AtomicReference<User> atomicReference =
            new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("xuliugen", "123456");
        atomicReference.set(user);

        User updateUser = new User("Allen", "654321");
        atomicReference.compareAndSet(user, updateUser);
        System.out.println(atomicReference.get().getUserName());
        System.out.println(atomicReference.get().getUserPwd());
    }

    static class User {
        private String userName;
        private String userPwd;
        //省略get、set、构造方法

        public User(String userName, String userPwd) {
            this.userName = userName;
            this.userPwd = userPwd;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }
    }
}
