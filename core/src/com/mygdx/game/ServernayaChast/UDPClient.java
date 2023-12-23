package com.mygdx.game.ServernayaChast;


import com.mygdx.game.entity.Player;

import java.io.IOException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


public class UDPClient {
    private final int serverPort;
    private final String SERVER_NAME;

    private final DatagramSocket udpSocket;

    private static int Zaprosi;


    public UDPClient(int serverPort, String IP, DatagramSocket udpSocket) {
        Zaprosi = 0;
        this.serverPort = serverPort;
        this.SERVER_NAME = IP;
        this.udpSocket = udpSocket;

    }
    public String NewMessage(String Message){
        Message = GetTimeStamp()+" "+ Message+" "+Zaprosi;

        return Message;
    }
    public void SendConnectMessage(String Message) throws UnknownHostException {
        byte[] msgBuffer;
        msgBuffer = Message.getBytes();
        DatagramPacket packet = new DatagramPacket(msgBuffer,msgBuffer.length, InetAddress.getByName(SERVER_NAME), serverPort);
        try {
            udpSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SendMessage(String Message) throws IOException {
        byte[] msgBuffer = new byte[1024];
        Message = NewMessage(Message);
        String sum = null;
        sum = calculateChecksum(Message);
        Message =Message+" "+(sum);
        msgBuffer = Message.getBytes();
        DatagramPacket packet = new DatagramPacket(msgBuffer,msgBuffer.length, InetAddress.getByName(SERVER_NAME), serverPort);
        udpSocket.send(packet);

    }
    public void IncZap(){
        if(Zaprosi == 2147483647){
            Zaprosi = 0;
        }
        Zaprosi++;
    }

    public int getZaprosi() {
        return Zaprosi;
    }
    public static String calculateChecksum(String message) {
        try {
            // Создаем объект MessageDigest с использованием алгоритма SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Преобразуем сообщение в байтовый массив
            byte[] messageBytes = message.getBytes();

            // Вычисляем контрольную сумму для сообщения
            byte[] checksumBytes = digest.digest(messageBytes);

            // Преобразуем байтовый массив в строку шестнадцатеричного формата
            StringBuilder checksumBuilder = new StringBuilder();
            for (byte b : checksumBytes) {
                checksumBuilder.append(String.format("%02x", b));
            }

            // Возвращаем контрольную сумму в виде строки
            return checksumBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ReceiveMessage() throws IOException {
        byte[] Receive = new byte[512];
        DatagramPacket receive = new DatagramPacket(Receive, Receive.length);
        udpSocket.receive(receive);
        String Message = new String(receive.getData(), receive.getOffset(), receive.getLength());
        String[] words = Message.split(" "); // Разбиваем строку на массив слов
        StringBuilder result = new StringBuilder();

// Проходимся по всем словам, кроме последнего
        for (int i = 0; i < words.length - 1; i++) {
            result.append(words[i]).append(" ");
        }

        return result.toString().trim();
    }
    public void SocketClose(){
        udpSocket.close();
    }
    public String GetTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp);
    }
}