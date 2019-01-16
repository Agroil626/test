public class Operations {
    private int result = 1;
    private int sum = 0;

    public void add(int value) {
        sum += value;
    }

    public void multiply(int value) {
        result *= value;
    }

    public void multiplyAndSum() {
        result += sum;
    }

    public int getResult() {
        return result;
    }

    public int getSum() {
        return sum;
    }

    public void clear() {
        result = 1;
        sum = 0;
    }

}
