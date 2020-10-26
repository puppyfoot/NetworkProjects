package com.thread;

// java�� thread ���!
class T extends Thread{

	String name;
	
	public T() {}
	public T(String name) {
		this.name = name;
	}
	
	// run �Լ��� ����Ѵ�
	@Override
	public void run() {
		for(int i=0;i<=100;i++) {
			System.out.println(name+":"+i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}


// ����
public class Test {

	public static void main(String[] args) {
		// ���ÿ� ���۵ȴ�!!!!!
		T t1 = new T("T1");
		t1.start();        // T1�� run�Լ��� ����ȴ�!
		t1.setPriority(10); // �������� ���� ���μ����� �����Ѵ�
		T t2 = new T("T2");
		t2.start();
		t2.setPriority(1);
	}

}