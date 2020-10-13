package ru.nik.connectionPackage;

import ru.nik.clientPackage.Request;

import java.io.*;
import java.net.Socket;

public class Connection {
    private ConnectionListener connectionListener;
    private Socket connectionSocket;
    private Thread connectionThread;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isDisconnect = false;
    public Connection(Socket socket, ConnectionListener listener){
        connectionSocket = socket;
        connectionListener = listener;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            objectOutputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException e){
            System.out.println(e.fillInStackTrace());
        }
        connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectionListener.connectionReady(Connection.this);
//                    String load = bufferedReader.readLine();
//                    connectionListener.loadSettings(load);
                    while (true) {
//                        String buffString = bufferedReader.readLine();
//                        if (buffString == null){
//                            throw new Exception();
//                        }
//                        connectionListener.getString(Connection.this, buffString);
                        TransferJsonObject jsonObject = (TransferJsonObject)objectInputStream.readObject();
                        if(jsonObject == null){
                            throw new Exception();
                        }
                        connectionListener.getTransferObject(Connection.this, jsonObject);
                    }
                } catch (Exception e) {
                    disconnect();
                    connectionListener.isDisconnect(Connection.this);
                } finally {
                    isDisconnect = true;
                }
            }
        });
        connectionThread.start();

    }
    public Connection(ConnectionListener cl, String IP , int port) throws IOException {
        this(new Socket(IP,port),cl);

    }
    public Connection(ConnectionListener cl , Socket s){
        this(s,cl);
    }
    public void sendMessage(String s) throws IOException{
            bufferedWriter.write(s + "\n");
            bufferedWriter.flush();

    }

    public void sendTransferObject(TransferJsonObject transferJsonObject) throws  IOException{
        objectOutputStream.writeObject(transferJsonObject);
        objectOutputStream.flush();
    }
    public synchronized void disconnect(){
        connectionThread.interrupt();
        try {
            connectionSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
            isDisconnect = true;
        } catch (IOException e) {
            System.out.println("ошибка прерывания потока");
        }
    }
    public boolean checkDisconnect(){
        return isDisconnect;
    }

    @Override
    public String toString() {
        return "IP = " + connectionSocket.getInetAddress();
    }
}
