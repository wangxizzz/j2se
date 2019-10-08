package java高级知识.枚举;

/**
 * Created by wxi.wang
 * <p>
 * 2019/8/5 20:30
 * Decription:
 */
public enum ProductTag {
    DYNAMIC("动态"),
    SOLID("固态"),
    DIJIE("地接");

    private String desc;

    ProductTag(String desc) {
        this.desc = desc;
    }

    public static void main(String[] args) {
        System.out.println(DIJIE.name().equals("DIJIE"));
    }
}
