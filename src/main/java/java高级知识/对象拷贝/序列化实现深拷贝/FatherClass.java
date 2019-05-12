package java高级知识.对象拷贝.序列化实现深拷贝;


import java.io.*;

/**
 * <Description>
 *
 * @author wangxi
 */
public class FatherClass implements Serializable {
    public String name;
    public int age;
    public ChildClass childClass;

    public Object deepClone() throws IOException, OptionalDataException, ClassNotFoundException {
        // 将对象写到流里
        OutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);

        // 从流里读出来
        InputStream bi = new ByteArrayInputStream(((ByteArrayOutputStream) bo).toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (oi.readObject());
    }
}

