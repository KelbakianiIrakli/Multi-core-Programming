package hw2;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class Exercise2_1 {
	static volatile long counter;
	static Filter lock;

	static class Filter {
		AtomicIntegerArray level;
		AtomicIntegerArray victim;
		int n;

		public Filter(int n) {
			this.n = n;
			level = new AtomicIntegerArray(n);
			victim = new AtomicIntegerArray(n);

		}

		public void lock() {
			int me = Integer.parseInt(Thread.currentThread().getName().replaceAll("[^0-9]", ""))%this.n;
			for (int i = 1; i < n; i++) {
				level.set(me, i);
				victim.set(i, me);
				for (int k = 0; k < n; k++) {
					while ((k != me) && (level.get(k) >= i && victim.get(i) == me)) {
						// spin and wait
					}
				}
			}
		}

		public void unlock() {
			int me = Integer.parseInt(Thread.currentThread().getName().replaceAll("[^0-9]", ""))%this.n;
			level.set(me, 0);
		}
	}

	static class CounterThread implements Runnable {
		int id;
		long n;

		public CounterThread(int id, long n) {
			this.id = id;
			this.n = n;
		}

		@Override
		public void run() {
			if (id % 2 == 0) {
				for (long l = 0; l < n; l++) {
					lock.lock();
					counter++;
					//System.out.println(counter);
					lock.unlock();
				}
			} else {
				for (long l = 0; l < n; l++) {
					lock.lock();
					counter--;
					//System.out.println(counter);
					lock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] NumberOfthreads = { 2, 4, 8, 16};
		long n = (args.length >= 2 ? Long.parseLong(args[1]) : 10000000);


		
		
		
		for (int k = 0; k < NumberOfthreads.length; k++) {
		
			int t = NumberOfthreads[k];
		
		// Create t threads
		lock = new Filter(t);
		Thread[] threads = new Thread[t];
		for (int i = 0; i < t; i++) {
			threads[i] = new Thread(new CounterThread(i, n));
		}
		long time = System.currentTimeMillis();
		// Start threads
		for (int i = 0; i < t; i++) {
			threads[i].start();
		}
		// Wait for threads completion
		for (int i = 0; i < t; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("# of Threads: " + t + " - Finished with total of " + counter + " in " + time + " ms");
	}
	}

}
