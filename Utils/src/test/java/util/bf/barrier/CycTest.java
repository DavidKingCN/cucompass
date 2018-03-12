package util.bf.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CycTest {

	public static void main(String[] args) {
		ExecutorService executorpool = Executors.newFixedThreadPool(3);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

		CycWork work1 = new CycWork(cyclicBarrier, "张三");
		CycWork work2 = new CycWork(cyclicBarrier, "李四");
		CycWork work3 = new CycWork(cyclicBarrier, "王五");

		executorpool.execute(work1);
		executorpool.execute(work2);
		executorpool.execute(work3);

		executorpool.shutdown();

	}

}

class CycWork implements Runnable {

	private CyclicBarrier cyclicBarrier;
	private String name;

	public CycWork(CyclicBarrier cyclicBarrier, String name) {
		this.name = name;
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println(name + "正在打桩，毕竟不轻松。。。。。");

		try {
			Thread.sleep(5000);
			System.out.println(name + "不容易，终于把桩打完了。。。。");
			cyclicBarrier.await();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println(name + "：其他逗b把桩都打完了，又得忙活了。。。");

	}

}