public class BufferN<T> {
    T value;
    int n;
    T[] bufferInternal;
    int size;
    int rear,front;



    public BufferN(int n) {
        if (n <= 0) throw new IllegalArgumentException("size should not be zero or negative");
        this.n = n;
        bufferInternal = createArray(n);
        initPointers();
    }

    private void initPointers() {
        size = 0;
        front = 0;
        rear = -1;
    }

    private T[] createArray(int n) {
        var array = (T[]) new Object[n];
        return array;
    }

    public synchronized T take() throws InterruptedException{
        while(size == 0){
            this.wait();
        }
        T temp = bufferInternal[front];
        bufferInternal[front++]=null;
        size--;
        if(size == 0) initPointers();
        if (size > 0) this.notifyAll();
        return temp;
    }

    public synchronized void put(T elem) throws InterruptedException {
        while(size == n){
            this.wait();
        }
        rear = rear==n-1?0:rear+1;
        bufferInternal[rear] = elem;
        size++;
        if (size == 0) this.notifyAll();

    }

    public synchronized boolean isEmpty(){
        return size == 0;
    }

    public void print(){
        for (T value :
                bufferInternal) {
            System.out.print(value+" ");
        }
        System.out.println();
    }
}
