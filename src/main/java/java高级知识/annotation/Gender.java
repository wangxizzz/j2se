package java高级知识.annotation;

import java.lang.annotation.*;

/**
 * <Description>
 *
 * @author wangxi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Gender {
    public enum GenderType {

        Male("男"),
        Female("女"),
        Other("中性");

        private String genderStr;

        private GenderType(String arg0) {
            this.genderStr = arg0;
        }

        @Override
        public String toString() {
            return genderStr;
        }
    }

    GenderType gender() default GenderType.Male;
}
