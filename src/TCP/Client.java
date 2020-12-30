package TCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


/**
 * @author ：summerain
 * @date ：Created in 2020/12/29 7:55 下午
 */
public class Client {
    public static void main(String[] args)throws Exception{
        System.out.println("计算机网络实验10");
        Scanner input=new Scanner(System.in);
        String[] namelist;

        OutputStream outputStream ;
        byte[] bytes = new byte[1024];
        FileInputStream[] finlist;
        while(true){
            System.out.println("请输入1上传文件，输入2下载文件，输入0退出系统:");
            int content=input.nextInt();
            if(content == 1){
                Socket socket = new Socket("127.0.0.1", 8888);
                outputStream = socket.getOutputStream();
                // 第一次上传
                outputStream.write("1".getBytes());
//                socket.shutdownOutput();
                System.out.println("可上传的文件为：");

                // 获取可上传文件名称，并建立对应的输入流（可改进）
                namelist=new File("Client/").list();
                finlist = new FileInputStream[namelist.length];
                for (int i = namelist.length-1;i>=0;i--){
                    System.out.print(namelist[i]+'\t');
                    finlist[i] = new FileInputStream("Client/"+namelist[i]);
                }

                // 选择上传文件
                System.out.println("\n请输入要上传的文件名称:");
                String filename=input.next();
                System.out.println(filename+"正在上传中");

//                outputStream = socket.getOutputStream();
                // 第二次上传
                outputStream.write(filename.getBytes());
                // 第三次上传
                for(int i=0;i < namelist.length;i++){
                    if(namelist[i].equals(filename)){
                        int len = 0;
                        while ((len = finlist[i].read(bytes))!=-1){
                            outputStream.write(bytes);
                        }
                    }
                }
                socket.shutdownOutput();
                System.out.println(filename+"上传成功");
            }
            if(content == 2){
                Socket socket = new Socket("127.0.0.1", 8888);
                System.out.println("可下载的文件为：");
                outputStream = socket.getOutputStream();
                outputStream.write("2".getBytes());
                InputStream inputStream = socket.getInputStream();
                int len;
                len = inputStream.read(bytes);
                System.out.println(new String(bytes,0,len));
                System.out.println("请输入需要下载的文件:");
                String fileName=input.next();
                System.out.println(fileName);
                outputStream.write(fileName.getBytes());
                FileOutputStream fileOutputStream = new FileOutputStream("Client/" + fileName);
//                FileOutputStream fileOutputStream = new FileOutputStream("Svever/" + filename);
                while ((len=inputStream.read(bytes)) != -1){
                    fileOutputStream.write(bytes);
                }
                System.out.println(fileName+"下载完毕！\n");
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
