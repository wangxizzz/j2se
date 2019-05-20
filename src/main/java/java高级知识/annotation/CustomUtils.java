package java高级知识.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <Description>
 *
 * @author wangxi
 */
@Slf4j
public class CustomUtils {
    public static void getInfo(Class<?> clazz) {
        String name = "";
        String gender = "";
        String profile = "";
        // 利用反射获取class的字段
        Field fields[] = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 判断字段上是否存在Name注解
            if (field.isAnnotationPresent(Name.class)) {
                Name arg0 = field.getAnnotation(Name.class);
                name = arg0.value();
                log.info("name = {}" , name);
            }
            if (field.isAnnotationPresent(Gender.class)) {
                Gender arg0 = field.getAnnotation(Gender.class);
                gender = arg0.gender().toString();
                log.info("gender = {}", gender);
            }
        }
    }
}

