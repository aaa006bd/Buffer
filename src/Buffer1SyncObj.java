class Buffer1SyncObj<T> {

    boolean empty;
    T content;

    private Object r = new Object();
    private Object w = new Object();

    Buffer1SyncObj() {
        empty = true;
        content = null;
    }

    Buffer1SyncObj(T v) {
        empty = false;
        content = v;
    }


    public T take() throws InterruptedException {
        synchronized (r) {
            while (empty) {
                r.wait();
            }
            synchronized (w) {
                empty = true;
                T help = content;
                content = null;
                w.notify();
                return help;
            }
        }
    }

    public void put(T v) throws InterruptedException {
        synchronized (w) {
            while (!empty) {
                w.wait();
            }
            synchronized (r) {
                empty = false;
                content = v;
                r.notify();
            }
        }
    }

    public boolean tryPut(T content){
        if(empty){
            this.content = content;
            empty = false;
            return true;
        }
        return false;
    }

    public void override(T content){
        this.content = content;
    }

    public T take(long timeOut) throws InterruptedException {
        synchronized (r) {
            while (empty) {
                r.wait(timeOut);
            }
            synchronized (w) {
                empty = true;
                T help = content;
                content = null;
                w.notify();
                return help;
            }
        }
    }


}







