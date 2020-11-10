package com.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.can.SendAndReceiveSerial;
import com.msg.Msg;

public class Client {

	int port;
	String address;
	String id;
	Socket socket;
	Sender sender;
	
	static String ip;
	
	public Client() {}
	public Client(String address, int port, String id) {
		this.address = address;
		this.port = port;
		this.id = id;
	}
	
	public void connect() throws IOException {
		// ������ ��������� ����
		try {
			socket = new Socket(address,port);
		} catch (Exception e) {
			while(true) {
				try {
					Thread.sleep(2000);
					socket = new Socket(address,port);

					break;
				} catch (Exception e1) {
					System.out.println("Retry...");
				}
			}
		}
		
		ip = (socket.getInetAddress().toString());
		
		System.out.println("Connected Server:"+address);
		
		sender = new Sender(socket);
		new Receiver(socket).start();
		
		//sendMsg();
	
	}
	
	public void sendMsg() throws IOException {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input msg");
			String ms = sc.nextLine();
			Msg msg = null;
			// 1�� ������ ���������� ����� ����Ʈ�� ������.
			if(ms.equals("1")){
				msg = new Msg(id,ms);
			}else {
				ArrayList<String> ips = new ArrayList<>();
				ips.add("/192.168.0.61");
				ips.add("/192.168.0.9");
				ips.add("/192.168.0.72");
//�׷� ������		msg = new Msg(ips,id,ms);   
				msg = new Msg(null,id,ms);  // ��ü ������
			}

			
			sender.setMsg(msg);
			new Thread(sender).start();
			
			if(ms.equals("q")){
				break;
			}
		}
		//sc.close();
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bye....");
	}
	
	class Sender implements Runnable{
		Socket socket;
		ObjectOutputStream oo;
		Msg msg;
		
		public Sender(Socket socket) throws IOException {
			this.socket = socket;
			oo = new ObjectOutputStream(socket.getOutputStream());
		}
		
		public void setMsg(Msg msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			if(oo != null) {
				try {
					oo.writeObject(msg);
				} catch (IOException e) {
					//e.printStackTrace();
					try {
						if(socket != null) {
							socket.close();	
						}
					}catch(Exception e1) {
						e1.printStackTrace();

					}
					// ������ ����� connect�� �Ѵ�!
					try {
						Thread.sleep(2000);
						connect();
						//sendMsg();
						System.out.println("test1");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
		}
		
	}
	
	class Receiver extends Thread{
		ObjectInputStream oi;
		public Receiver(Socket socket) throws IOException {
			oi = new ObjectInputStream(socket.getInputStream());
		}
		@Override
		public void run() {
			while(oi != null) {
				Msg msg = null;
				try {
					msg = (Msg) oi.readObject();
					if(msg.getMaps() != null) {
						HashMap<String,Msg> hm = msg.getMaps();
						Set<String> keys = hm.keySet();
						for(String k : keys) {
							System.out.println(k);
						}
						continue;
					}
					System.out.println(msg.getId()+msg.getMsg());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}

			} // end while
			try {
				if(oi != null) {
					oi.close();
				}
				if(socket != null) {
					socket.close();
				}
			}catch(Exception e){
			
			}
			// ������ ����� connect�� �Ѵ�!
			try {
				Thread.sleep(2000);
				System.out.println("test2");
				connect();
				sendMsg();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		}
		
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		Client client = new Client("192.168.0.28",5556,"[����]");
		
		try {
			client.connect();
			//client.sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM9", true);
		
		
		// ���⼭���� HttpConnection
		String urlstr = "http://192.168.0.28/tcpip1/car.mc";
		URL url = null;
		// HttpURLConnection ���!
		HttpURLConnection con = null;
		
		//System.out.println("-----test-----");
		
		// 5�ʿ� �� ���� ���� ��ǥ�� �����ϴ� �۾�
		while(true) {
			//�� �ȿ� ��ü�� �־�� �Ѵ�!
			try {
				Random rd = new Random();
				//double sensor = rd.nextDouble()*100;
				String sensor = ss.getSen();
				url = new URL(urlstr+"?ip="+ip+"&sensor="+sensor);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(5000); // 10�ʵ��� ������ ������ Ÿ�Ӿƿ�
				con.setRequestMethod("POST"); // � ������� ������
				con.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con.disconnect();
			}
			Thread.sleep(5000);

		}


		
	}
		

}