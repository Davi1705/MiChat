package com.example.michat;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(Socket Socket){
        try{
            this.socket = Socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public void sendMsgServer(String msgServer){
        try{
            writer.write(msgServer);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    public void receiveMsg(VBox vBox){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (socket.isConnected()){
                    try{
                        String msg = reader.readLine();
                        HelloController.addLabel(msg, vBox);
                    } catch (IOException e) {
                        close();
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    private void close(){
        try{
            if(reader!=null){
                reader.close();
            }
            if (writer!=null){
                writer.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
