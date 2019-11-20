public class BufferMain {
    public static void main(String[] args) throws InterruptedException {
        BufferN<Integer> buffer = new BufferN<>(4);
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);
        buffer.print();
        System.out.println(buffer.take());
        System.out.println(buffer.take());
        buffer.print();
        System.out.println(buffer.take());
        System.out.println(buffer.take());
        buffer.print();
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);
        buffer.print();
    }
}
