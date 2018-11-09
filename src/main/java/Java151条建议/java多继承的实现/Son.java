package Java151条建议.java多继承的实现;

public class Son extends FatherImpl implements Mother{
    @Override
    public int strong() {
        return super.strong() + 2;  // 儿子比爸爸强壮指数多2
    }

    @Override
    public int kind() {
        return new MotherSpecial().kind();
    }
    // 内部类的特性体现
    private class MotherSpecial extends MotherImpl{
        @Override
        public int kind() {
            return super.kind() - 2; // 儿子的温柔指数降低2
        }
    }
}
