package hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Exercise4 {
	public static void main(String args[]) throws Exception {
		Integer N = 10;
		Integer threadNumbers = 5;
		LockBasedQueue<Integer> queue = new LockBasedQueue(N);
		// List<Integer> queue = new ArrayList<Integer>();
		ArrayList<Thread> threadsList = new ArrayList<>();
		for (int i = 0; i < threadNumbers; i++) {
			Thread producer = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							Integer randomNum = ThreadLocalRandom.current().nextInt(0, N);
							queue.enq(randomNum);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			Thread consumer = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							queue.deq();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			threadsList.add(producer);
			threadsList.add(consumer);
			producer.start();
			consumer.start();
		}
		for (int i = 0; i < threadsList.size(); i++) {
			try {
				threadsList.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
