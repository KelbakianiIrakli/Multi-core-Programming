package hw1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockBasedQueue<Integer> {
	int head, tail;
	Integer[] items;
	Lock lock;

	public LockBasedQueue(int size) {
		head = 0;
		tail = 0;
		lock = new ReentrantLock();
		items = (Integer[]) new Object[size];
	}

	public void add(Integer x) {
		lock.lock();
		try {
			if (tail - head == items.length)
				throw new InterruptedException();
			items[tail % items.length] = x;
			System.out.println("enqued: " + x);
			tail++;
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	public void remove() {
		lock.lock();
		try {
			if (tail == head)
				throw new InterruptedException();
			Integer x = items[head % items.length];
			System.out.println("dequeued: " + x);
			head++;
		} catch (Exception e) {

		} finally {
			lock.unlock();
		}
	}
}
