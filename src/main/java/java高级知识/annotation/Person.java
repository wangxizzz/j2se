package java高级知识.annotation;

import lombok.Data;

/**
 * <Description>
 *
 * @author wangxi
 */
@Data
public class Person {
    @Name("阿特罗伯斯")
    private String name;

    @Gender(gender = Gender.GenderType.Male)
    private String gender;
}

