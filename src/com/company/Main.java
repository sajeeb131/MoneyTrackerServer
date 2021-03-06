package com.company;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Scanner;

public class Main{
    private static HashMap<String,String> map;
    static BufferedWriter writer;
    static BufferedReader reader;
    private static String username;
    private static String password;

    private static void readHashmap(HashMap<String,String> map) throws IOException{
        Scanner sc=new Scanner(new File("Files/Hashamp/hashmap.txt"));
        while(sc.hasNext()){
            String key=sc.next();
            String value=sc.next();
            map.put(key,value);
        }
        sc.close();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Server is waiting for client.");
            ServerSocket src=new ServerSocket(2021);

            map=new HashMap<>();
            readHashmap(map);

            while(true){
                System.out.println("Users: "+map.size());
                Socket sc=src.accept();
                System.out.println("server is connected to client");

                InputStreamReader i=new InputStreamReader(sc.getInputStream());
                reader=new BufferedReader(i);
                OutputStreamWriter o=new OutputStreamWriter(sc.getOutputStream());
                writer=new BufferedWriter(o);

                String accountType=reader.readLine();

                if(accountType.equals("new")){
                    //create new account
                    username=reader.readLine();
                    password=reader.readLine();
                    String fullname=reader.readLine();
                    String balance=reader.readLine();
                    String currency=reader.readLine();

                    FileWriter fileWriter=new FileWriter("Files/Hashamp/hashmap.txt",true);
                    fileWriter.write(username+" "+password+"\n");
                    fileWriter.flush();
                    readHashmap(map);
                    fileWriter.close();

                    ClientData data=new ClientData(username,fullname,balance,currency,writer,reader);
                }
                else if(accountType.equals("old")){
                    //log in to account
                    username=reader.readLine();
                    password=reader.readLine();
                    String pass=map.get(username);
                    if(pass.equals(password)){
                        writer.write("True"+"\n");
                        writer.flush();
                    }
                    else{
                        writer.write("False"+"\n");
                        writer.flush();
                    }
                    ClientData data=new ClientData(username,writer,reader);
                    Thread thread=new Thread(data);
                    thread.start();
                }
            }

        }
        catch (ConnectException e){
            e.getSuppressed();
        }
        catch (SocketException e){
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
