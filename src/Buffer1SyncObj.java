import java.util.concurrent.TimeoutException;

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
        synchronized (w){
            if(!empty){
                 return false;
            }

            synchronized (r){
                this.content = content;
                empty = false;
                r.notify();
                return true;
            }
        }
    }

    public void overwrite(T content){
       synchronized (w){
           this.content = content;
       }
       if (empty) {
           synchronized (r) {
               this.empty = false;
               r.notify();
           }
       }
    }

    public T read() throws InterruptedException{
        synchronized (r) {
            while (empty) {
                r.wait();
            }
            synchronized (w) {
                r.notify();
                return content;
            }
        }
    }

    public T take(long timeOut) throws InterruptedException, TimeoutException {
        synchronized (r) {
            if (empty){
                r.wait();
            }
            if(empty){
                throw new TimeoutException("timeout");
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
