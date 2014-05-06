package com.ctrip.gs.common.thrift.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Pool<T> {
	private Integer maxSize;
	private BlockingQueue<T> queue;

	public Pool(Integer maxSize) {
		this.maxSize = maxSize;
		queue = new ArrayBlockingQueue<T>(maxSize);
	}
	
	public void LazyInit(T[] values) throws InterruptedException {
		for(T v: values) {
			queue.put(v);
		}
	}
	
	public T get() throws InterruptedException {
		return queue.take();
	}
	
	public void put(T v) throws InterruptedException {
		queue.put(v);
	}
	
	public T getWithTimeout(Integer integer) throws InterruptedException{
		return queue.poll(integer.longValue(), TimeUnit.MILLISECONDS);
	}
}