package java高级知识.jdk动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author wangxi
 * @Time 2020/3/26 17:33
 */
public class WorkHandler implements InvocationHandler {

    private Object target;

    WorkHandler(){}

    WorkHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("之前做事,,,,");

        Object invoke = method.invoke(target);

        return invoke;
    }
}
