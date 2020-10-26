package com.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//����Ʈ��� ��Ʈ���� InputStream�� OutputStream�� ����غ���! (����� ������ 1����Ʈ)
public class Test1 {
	
	public static void main(String[] args) {
		String file = "c:\\network\\d01\\src\\text.txt";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis); // fis�� �־ ���Ȯ��, �ӵ�����
			fos = new FileOutputStream("text2.txt");
			bos = new BufferedOutputStream(fos); // fos�� �־ ���Ȯ��, �ӵ�����
			int data = 0;
			// �� �ڸ��� �ƴҶ�����
			while((data=fis.read()) != -1) {
				bos.write(data); // �о�帰�� �����Ѵ�
				System.out.println((char)data);  // ����Ʈ������ �о���
			}
			System.out.println(fis.available());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// fis�� ���� close��!
			if(bis != null) {
				try {
					bis.close();   // ** �� close�� ���־�� �Ѵ�!!! **
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// bos�� ���� close��!
			if(bos != null) {
				try {
					bos.close();   // ** �� close�� ���־�� �Ѵ�!!! **
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	
	

}


