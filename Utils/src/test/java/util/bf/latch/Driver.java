package util.bf.latch;

import java.util.concurrent.CountDownLatch;

public class Driver { // ...
	public static void main(String args[]) {
		try {
			new Driver().main();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   void main() throws InterruptedException {
	   int N = 10;
     CountDownLatch startSignal = new CountDownLatch(1);
     CountDownLatch doneSignal = new CountDownLatch(N);

     for (int i = 0; i < N; ++i) // create and start threads
       new Thread(new Worker1(startSignal, doneSignal)).start();

     doSomethingElse("before");            // don't let run yet
     startSignal.countDown();      // let all threads proceed
     doSomethingElse("after");
     doneSignal.await();           // wait for all to finish/
   }

	private void doSomethingElse(String msg) {
		System.out.println("execute " +  msg);
	}
   
 }

 class Worker1 implements Runnable {
   private final CountDownLatch startSignal;
   private final CountDownLatch doneSignal;
   Worker1(CountDownLatch startSignal, CountDownLatch doneSignal) {
     this.startSignal = startSignal;
     this.doneSignal = doneSignal;
   }
   public void run() {
     try {
       startSignal.await();
       doWork();
       doneSignal.countDown();
     } catch (InterruptedException ex) {} // return;
   }

   void doWork() { System.out.println(this.getClass().getName());}
 }