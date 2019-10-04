package java高级知识.java反射.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

/**
 * 反射的本质就是把一个类解剖了，类中所有的东西都可以拿到
 *
 * @author 王喜
 */
public class MyReflect {
    public String className = null;
    @SuppressWarnings("rawtypes")
    public Class personClass = null;

    /**
     * 反射Person类
     *
     * @throws Exception
     */
    @Before
    public void init() throws Exception {
        className = "java高级知识.java反射.reflect.Person";
        personClass = Class.forName(className);
    }

    /**
     * 获取某个class文件对象
     */
    @Test
    public void getClassName() throws Exception {
        System.out.println(personClass);
    }

    /**
     * 获取某个class文件对象的另一种方式
     */
    @Test
    public void getClassName2() throws Exception {
        System.out.println(Person.class);
    }

    /**
     * 创建一个class文件表示的实例对象，底层会调用空参数的构造方法
     */
    @Test
    public void getNewInstance() throws Exception {
        System.out.println(personClass.newInstance());
    }

    /**
     * 获取非私有的构造函数
     * <p>
     * <p>
     * getDeclaredConstructor()与getConstructor的区别
     * 首先看getDeclaredConstructor(Class<?>... parameterTypes) 
     * 这个方法会返回制定参数类型的所有构造器，包括public的和非public的，当然也包括private的。
     * getDeclaredConstructors()的返回结果就没有参数类型的过滤了。
     * <p>
     * 再来看getConstructor(Class<?>... parameterTypes)
     * 这个方法返回的是上面那个方法返回结果的子集，只返回制定参数类型访问权限是public的构造器。
     * getConstructors()的返回结果同样也没有参数类型的过滤。
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void getPublicConstructor() throws Exception {
        //Long.class,String.class 表示参数的类型
        Constructor constructor2 = Person.class.getConstructor();
        System.out.println(((Person) constructor2.newInstance()).getId());
        Constructor constructor = personClass.getConstructor(Long.class, String.class);
        //利用构造函数来创建对象
        Person person = (Person) constructor.newInstance(100L, "zhangsan");
        System.out.println(person.getId());
        System.out.println(person.getName());
    }

    /**
     * 获得根据参数获取搜索的构造函数（包括私有、公有）
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void getAllConstructor() throws Exception {
        Constructor con = personClass.getDeclaredConstructor(String.class);
        con.setAccessible(true);//强制取消Java的权限检测
        Person person2 = (Person) con.newInstance("zhangsan");
        System.out.println("**" + person2.getName());
        // 获取public构造函数
        Constructor constructor = personClass.getDeclaredConstructor();
        System.out.println(constructor.newInstance());
    }

    /**
     * 访问public的成员变量
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void getNotPrivateField() throws Exception {
        Constructor constructor = personClass.getConstructor(Long.class, String.class);
        Object obj = constructor.newInstance(100L, "zhangsan");

        Field field = personClass.getField("name");
        field.set(obj, "lisi");   //把obj这个对象的field字段设置成lisi
        System.out.println(field.get(obj));
    }

    /**
     * 访问all成员变量
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void getPrivateField() throws Exception {
        Constructor constructor = personClass.getConstructor(Long.class);
        Object obj = constructor.newInstance(100L);

        Field field2 = personClass.getDeclaredField("id");  //获取私有成员
        field2.setAccessible(true);//强制取消Java的权限检测
        field2.set(obj, 10000L);
        System.out.println(field2.get(obj));
    }

    /**
     * 获取非私有的成员函数
     */
    @SuppressWarnings({"unchecked"})
    @Test
    public void getNotPrivateMethod() throws Exception {
        System.out.println(personClass.getMethod("toString"));

        Object obj = personClass.newInstance();//获取空参的构造函数
        Method toStringMethod = personClass.getMethod("toString");
        //invoke()实现动态代理，用obj来调用toString方法
        Object object = toStringMethod.invoke(obj);
        System.out.println(object);
    }

    /**
     * 获取all成员函数,根据参数来筛选
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getPrivateMethod() throws Exception {
        Object obj = personClass.newInstance();//获取空参的构造函数
        Method method = personClass.getDeclaredMethod("getSomeThing");
        method.setAccessible(true);
        Object value = method.invoke(obj);
        System.out.println(value);

    }

    /**
     * 下面是反射机制的其他API
     */
    @Test
    public void otherMethod() throws Exception {
        //当前加载这个class文件的那个类加载器对象
        System.out.println(personClass.getClassLoader());
        //获取某个类实现的所有接口
        Class[] interfaces = personClass.getInterfaces();
        for (Class class1 : interfaces) {
            System.out.println(class1);
        }
        //反射当前这个类的直接父类
        System.out.println(personClass.getGenericSuperclass());
        /**
         * getResourceAsStream这个方法可以获取到一个输入流，这个输入流会关联到name所表示的那个文件上。
         */
        //path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。
        //与src相同的包下都是classPath根
        System.out.println(personClass.getResourceAsStream("/log4j.properties"));
        System.out.println(personClass.getResourceAsStream("log4j.properties"));

        //判断当前的Class对象表示是否是数组
        System.out.println(personClass.isArray());
        System.out.println(new String[3].getClass().isArray());

        //判断当前的Class对象表示是否是枚举类
        System.out.println(personClass.isEnum());
        System.out.println(Class.forName("cn.itcast_04_reflect.City").isEnum());

        //判断当前的Class对象表示是否是接口
        System.out.println(personClass.isInterface());
        System.out.println(Class.forName("cn.itcast_04_reflect.TestInterface").isInterface());


    }

}
