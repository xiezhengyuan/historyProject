package com.hxy.isw.util;



import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类，所有线程均应扔给此线程池运行，不允许游离态线程
 * @author Administrator
 *
 */
public class ThreadPoolManager {
	private static ExecutorService pool = new ThreadPoolExecutor(10, 50,
            20L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
	/**
	 * 执行线程，不关心返回值
	 * @param thread
	 */
	public static void exec(Runnable thread){
		pool.execute(thread);
	}
	
	/**
	 * 执行线程，关心返回值，调用get方法可获取返回值
	 * @param task
	 */
	public static void exec(FutureTask<Object> task){
		pool.submit(task);
	}
	
	public static void main(String[] args){
		ThreadPoolManager.exec(new Runnable(){

			@Override
			public void run() {
				Sys.out("runnable");
			}
			
		});
		FutureTask<Integer> task = new FutureTask<Integer>(new Callable() {

			@Override
			public Object call() throws Exception {
				Sys.out("FutureTask");
				Thread.sleep(1000);
				return 12;
			}
		});
		ThreadPoolManager.exec(task);
		try {
			Integer a = task.get();
			Sys.out("result:"+a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
