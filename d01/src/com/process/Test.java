package com.process;

public class Test {

	// 한 프로세스
	public static void main(String[] args) throws InterruptedException {
		int data1 = 0;
		int data2 = 0;
		// 이게 실행된 후
		for(int i = 1; i <= 10; i++) {
			data1 += i;
			System.out.println("data1:"+data1);
			Thread.sleep(300);
		}
		// 이게 실행 된다.. 
		for(int i = 1; i <= 10; i++) {
			data2 += i;
			System.out.println("data2:"+data2);
			Thread.sleep(300);
		}
		
		System.out.println(data1+ " " + data2);
		
		
		

	}

}
