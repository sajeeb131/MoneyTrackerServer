package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientData {
    ArrayList<ClientData> clients=new ArrayList<>();
    String username,fullname,balance,currency;
    double bill,grocery,transport,loan=0;
    BufferedReader reader;
    BufferedWriter writer;

    public ClientData(String username,BufferedWriter writer, BufferedReader reader){
        this.writer=writer;
        this.reader=reader;
        try{
            File file=new File("Files/Accounts/"+username+".txt");
            BufferedReader input=new BufferedReader(new FileReader(file));
            String user=input.readLine();
            writer.write(user+"\n");
            writer.flush();
            System.out.println(user);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public ClientData(String username, String fullname, String balance,String currency) throws FileNotFoundException {
        this.username = username;
        this.fullname = fullname;
        this.balance = balance;
        this.currency=currency;

        File file=new File("Files/Accounts/"+username+".txt");
        PrintWriter out=new PrintWriter(file);
        out.println(username+"\n"+fullname+"\n"+balance+"\n"+currency+"\nend");
        System.out.println(username+"\n"+fullname+"\n"+balance+"\n"+currency+"\nend");
        out.close();
        clients.add(this);
    }
}
