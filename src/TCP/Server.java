package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author summerain
 * @date Created in 2020/12/29 8:05
 */
public class Server {
    public static void main(String[] args)throws Exception {

//        new Thread().start(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

        ServerSocket server = new ServerSocket(8888);
        byte[] bytes = new byte[1024];
        while (true) {
            Socket accept = server.accept();
            InputStream inputStream = accept.getInputStream();
            int len = inputStream.read(bytes);
            String string = new String(bytes,0,len);
            if("1".equals(string)){
                len = inputStream.read(bytes);
                String fileName = new String(bytes,0,len);
                File file = new File("Server/" + fileName);
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream("Server/" + fileName);
                while ((len = inputStream.read(bytes))!=-1){
                    fileOutputStream.write(bytes);
                }
                fileOutputStream.close();
                accept.close();
            }else if ("2".equals(string)){
                String[] namelist=new File("Server/").list();
                OutputStream outputStream = accept.getOutputStream();
                outputStream.write(Arrays.toString(namelist).getBytes());
                len = inputStream.read(bytes);
                String name = new String(bytes,0,len);
                System.out.println("name  "+name);
                for (int i = 0;i< namelist.length;i++){
                    if(name.equals(namelist[i])){
                        FileInputStream fileInputStream = new FileInputStream("Server/" + name);
                        while ((len = fileInputStream.read(bytes))!=-1){
                            outputStream.write(bytes);
                        }
                    }
                }
                accept.close();
            }else{
                break;
            }
        }
        server.close();
    }
}


