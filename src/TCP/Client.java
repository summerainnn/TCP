package TCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


/**
 * @author ��summerain
 * @date ��Created in 2020/12/29 7:55 ����
 */
public class Client {
    public static void main(String[] args)throws Exception{
        System.out.println("���������ʵ��10");
        Scanner input=new Scanner(System.in);
        String[] namelist;

        OutputStream outputStream ;
        byte[] bytes = new byte[1024];
        FileInputStream[] finlist;
        while(true){
            System.out.println("������1�ϴ��ļ�������2�����ļ�������0�˳�ϵͳ:");
            int content=input.nextInt();
            if(content == 1){
                Socket socket = new Socket("127.0.0.1", 8888);
                outputStream = socket.getOutputStream();
                // ��һ���ϴ�
                outputStream.write("1".getBytes());
//                socket.shutdownOutput();
                System.out.println("���ϴ����ļ�Ϊ��");

                // ��ȡ���ϴ��ļ����ƣ���������Ӧ�����������ɸĽ���
                namelist=new File("Client/").list();
                finlist = new FileInputStream[namelist.length];
                for (int i = namelist.length-1;i>=0;i--){
                    System.out.print(namelist[i]+'\t');
                    finlist[i] = new FileInputStream("Client/"+namelist[i]);
                }

                // ѡ���ϴ��ļ�
                System.out.println("\n������Ҫ�ϴ����ļ�����:");
                String filename=input.next();
                System.out.println(filename+"�����ϴ���");

//                outputStream = socket.getOutputStream();
                // �ڶ����ϴ�
                outputStream.write(filename.getBytes());
                // �������ϴ�
                for(int i=0;i < namelist.length;i++){
                    if(namelist[i].equals(filename)){
                        int len = 0;
                        while ((len = finlist[i].read(bytes))!=-1){
                            outputStream.write(bytes);
                        }
                    }
                }
                socket.shutdownOutput();
                System.out.println(filename+"�ϴ��ɹ�");
            }
            if(content == 2){
                Socket socket = new Socket("127.0.0.1", 8888);
                System.out.println("�����ص��ļ�Ϊ��");
                outputStream = socket.getOutputStream();
                outputStream.write("2".getBytes());
                InputStream inputStream = socket.getInputStream();
                int len;
                len = inputStream.read(bytes);
                System.out.println(new String(bytes,0,len));
                System.out.println("��������Ҫ���ص��ļ�:");
                String fileName=input.next();
                System.out.println(fileName);
                outputStream.write(fileName.getBytes());
                FileOutputStream fileOutputStream = new FileOutputStream("Client/" + fileName);
//                FileOutputStream fileOutputStream = new FileOutputStream("Svever/" + filename);
                while ((len=inputStream.read(bytes)) != -1){
                    fileOutputStream.write(bytes);
                }
                System.out.println(fileName+"������ϣ�\n");
                socket.shutdownOutput();
            }
            if(content == 0){
                Socket socket = new Socket("127.0.0.1", 8888);
                outputStream = socket.getOutputStream();
                outputStream.write("0".getBytes());
                break;
            }
        }
    }
}
