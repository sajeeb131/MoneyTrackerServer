package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientData implements Runnable{
    ArrayList<ClientData> clients=new ArrayList<>();

    String username,fullname,balance,currency;
    String loan="00";
    double bill,grocery,transport,restaurant,shopping;
    BufferedReader reader;
    BufferedWriter writer;

    public void readData(BufferedReader input){
        try{
            username=input.readLine();
            fullname=input.readLine();
            balance=input.readLine();
            currency=input.readLine();
            loan=input.readLine();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public ClientData(String username,BufferedWriter writer, BufferedReader reader){
        this.writer=writer;
        this.reader=reader;
        try{
            File file=new File("Files/Accounts/"+username+".txt");
            BufferedReader input=new BufferedReader(new FileReader(file));
            readData(input);
            writer.write(username+"\n");
            writer.write(fullname+"\n");
            writer.write(balance+"\n");
            writer.write(loan+"\n");
            writer.write(currency+"\n");
            writer.flush();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try{
                String category=reader.readLine();
                String amount=reader.readLine();
                String date=reader.readLine();
                String Description=reader.readLine();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ClientData(String username, String fullname, String balance, String currency, BufferedWriter writer, BufferedReader reader) throws FileNotFoundException,IOException {
        this.username = username;
        this.fullname = fullname;
        this.balance = balance;
        this.currency=currency;
        this.reader=reader;
        this.writer=writer;

        File file=new File("Files/Accounts/"+username+".txt");
        PrintWriter out=new PrintWriter(file);
        out.println("start\n"+username+"\n"+fullname+"\n"+balance+"\n"+currency+"\n"+loan+"\nend");
        System.out.println(username+"\n"+fullname+"\n"+balance+"\n"+currency+"\nend");
        out.close();

    }
}
