package hw1;

import java.util.ArrayList;
import java.util.List;

public class Exercise3 {
	public static class Producer implements Runnable {
		private List<Integer> queue;
		private volatile Integer next = 0;

		public Producer(List<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (queue) {
					while (queue.size() ==5) {
						try {
							System.out.println("Producer is waiting to consume object by comsumer..");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					queue.add(next);
					System.out.println("Added:" + next);
					queue.notifyAll();
				}
				next++;
			}
		}
	}

	public static class Consumer implements Runnable {
		private List<Integer> queue;

		public Consumer(List<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (queue) {
					if (queue.size() > 0) {
						Integer number = queue.remove(queue.size()-1);
						System.out.println("Removed:" + number);
					} else {
						try {
							// The thread must own queue’s monitor to call wait
							queue.wait();
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		List<Integer> queue = new ArrayList<Integer>();
		Thread producer1 = new Thread(new Producer(queue));
		Thread producer2 = new Thread(new Producer(queue));
		Thread consumer1 = new Thread(new Consumer(queue));
		Thread consumer2 = new Thread(new Consumer(queue));
		producer1.start();
		producer2.start();
		consumer1.start();
		consumer2.start();
	}
}