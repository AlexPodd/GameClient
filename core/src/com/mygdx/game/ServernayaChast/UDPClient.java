package com.mygdx.game.ServernayaChast;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try {
            // Создаем UDP сокет
            DatagramSocket clientSocket = new DatagramSocket();

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            // Получаем IP адрес и порт сервера
            //InetAddress serverAddress = InetAddress.getByName("192.168.0.103");
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 8089;

            // Отправляем данные серверу
            while (true) {
                DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, serverAddress, serverPort);
                Scanner sc = new Scanner(System.in);
                String clientMessage = sc.nextLine();
                sendData = clientMessage.getBytes();

                sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);

                // Получаем ответ от сервера
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                // Извлекаем данные из пакета
                String serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(serverMessage);




                // Закрываем сокет
                clientSocket.close();

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}