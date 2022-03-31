package hw2;

public class Exercise2_2_1 {
	public class ReadWriteLock{

		  private int readers       = 0;
		  private int writers       = 0;

		  public synchronized void lockRead() throws InterruptedException{
		    while(writers > 0){
		      wait();
		    }
		    readers++;
		  }

		  public synchronized void unlockRead(){
		    readers--;
		    notifyAll();
		  }

		  public synchronized void lockWrite() throws InterruptedException{

		    while(readers > 0 || writers > 0){
		      wait();
		    }
		    writers++;
		  }

		  public synchronized void unlockWrite() throws InterruptedException{
		    writers--;
		    notifyAll();
		  }
		}
}
