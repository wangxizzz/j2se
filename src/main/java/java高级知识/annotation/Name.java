package java高级知识.annotation;

import java.lang.annotation.*;

/**
 * <Description>
 *
 * @author wangxi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Name {
    String value() default "";
}
