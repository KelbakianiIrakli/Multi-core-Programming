package hw3;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class SavagesFair {
	public static int numberOfSavages = 5;
	public static int numberOfMaximumServings = 3;
	protected static  AtomicIntegerArray fairnessBuffer = new AtomicIntegerArray(numberOfSavages);

	public static void main(String[] args) {

		PotManager pot = new PotManager();
		System.out.println("Created Pot...");
		SavageFair savages[] = new SavageFair[numberOfSavages];
		System.out.println("Maximum Serving number = " + numberOfMaximumServings + " ,Number off Savages = " + numberOfSavages);
		for (int i = 1; i <= numberOfSavages; i++) {
			System.out.println("Creating Savage " + (i) + " thread.");
			savages[i-1] = new SavageFair(pot, i);
			savages[i-1].start();
		}

		Cook cook = new Cook(pot);
		System.out.println("Cook thread was created...");
		cook.start();

	}
}

class SavageFair extends Thread implements Runnable {
	PotManager pot;
	int id;

	public SavageFair(PotManager pot, int savageID) {
		this.pot = pot;
		this.id = savageID;
	}

	public void run() {
		while (true) {

			try {
				if(SavagesFair.fairnessBuffer.get(SavagesFair.numberOfSavages-1) == 0 || SavagesFair.fairnessBuffer.get(SavagesFair.numberOfSavages-1) == id) {
				pot.getserving(id);
				controlFairnessBuffer(id);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized static boolean isItemInBuffer(int val) {
		for(int i =0; i < SavagesFair.fairnessBuffer.length(); i++) {
			if(SavagesFair.fairnessBuffer.get(i) == val) {
				return true;
			}
		}
		return false;
	}
	public  static void controlFairnessBuffer(int val) {
		if(!isItemInBuffer( val)) {
			if(SavagesFair.fairnessBuffer.get(SavagesFair.numberOfSavages-1) == 0) {
				rotate();
				SavagesFair.fairnessBuffer.set(0, val);
			}
			else {
				rotate();
			}
	}
	}
	public synchronized static void rotate() {
	       int i = 0; 
	       int j = SavagesFair.numberOfSavages-1;
	       while(i != j)
	       {
	         int temp = SavagesFair.fairnessBuffer.get(i);
	         SavagesFair.fairnessBuffer.set(i, SavagesFair.fairnessBuffer.get(j));
	         SavagesFair.fairnessBuffer.set(j, temp);
	         i++;
	       }
	    }
}

