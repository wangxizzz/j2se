package java高级知识.序列化和transient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author wangxi created on 2020/6/1 22:35
 * @version v1.0
 */
public class SerializableTest {
    private static void serialize(SerializeUserEntity user) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("111.txt")));
        oos.writeObject(user);
        oos.close();
    }

    private static SerializeUserEntity deserialize() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("111.txt")));
        return (SerializeUserEntity) ois.readObject();
    }


    public static void main(String[] args) throws Exception {
        SerializeUserEntity user = new SerializeUserEntity();
        user.setName("tyshawn");
        user.setAge(18);
        user.setSex("man");
        System.out.println("序列化前的结果: " + user);

        serialize(user);



        SerializeUserEntity dUser = deserialize();
        System.out.println("反序列化后的结果: "+ dUser);
        /**
         * transient修饰的字段不参与序列化与反序列化。
         *
         * static属性不参与序列化反序列化。它的值时当前JVM中的值。
         * 原因：因为序列化是针对对象而言的, 而static属性优先于对象存在, 随着类的加载而加载, 所以不会被序列化.
         *
         * serialVersionUID也被static修饰, 为什么serialVersionUID会被序列化?
         *  其实serialVersionUID属性并没有被序列化, JVM在序列化对象时会自动生成一个serialVersionUID,
         *  然后将我们显示指定的serialVersionUID属性值赋给自动生成的serialVersionUID.
         */
    }
}
