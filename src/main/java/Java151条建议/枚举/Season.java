package Java151条建议.枚举;

/**
 * 季节的枚举,枚举可以替代在接口中定义的常亮
 * 注意：枚举是不能被继承，但是接口可以。
 */
public enum Season {
    Spring("春"),
    Summer("夏"),
    Autumn("秋"),
    Winter("冬");
    private String desc;   // 字段

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    Season(String desc) {
        this.desc = desc;
    }
    public static void main(String[] args) {
        //获取所有枚举
        for (Season s : Season.values()) {
            System.out.println(s.getDesc());
            // 得到每个枚举的编号
            System.out.println(s.ordinal());
        }
        // 枚举的强转
        try {
            Season s1 = Season.valueOf("Name");  // 注意，valueOf()是JVM内置方法。
        } catch (Exception e) {
            System.err.println("不能强转");
        }
    }
    // 枚举中自定义方法
    public static Season getComfortableSeason() {
        return Spring;
    }
}
