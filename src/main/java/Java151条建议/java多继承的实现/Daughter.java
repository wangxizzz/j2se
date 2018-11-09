package Java151条建议.java多继承的实现;

public class Daughter extends MotherImpl implements Father{
    @Override
    public int kind() {
        return super.kind() + 2;
    }

    @Override
    public int strong() {
        // 采用匿名内部类
        return new FatherImpl(){
            @Override
            public int strong() {
                return super.strong() - 2;  // 女儿的强壮指数减2
            }
        }.strong();
    }
}
