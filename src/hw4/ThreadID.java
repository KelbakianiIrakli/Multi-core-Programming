package hw4;

class ThreadID {
	static int myID;
	static int counter;

public static int get()
 {
 if (myID == 0)
 {
 //myID = Interlocked.Increment(ref counter);
}
 return myID-1;
 }
}
