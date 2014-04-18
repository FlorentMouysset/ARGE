package demo;
public class CalculatorImpl implements Calculator{
	@Override
    public int add(int i1, int i2) {
	return i1 + i2;
    }
    public int subtract(int i1, int i2) {
	return i1 - i2;
    }
}
