package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ClientData {
    String username,fullname,balance,currency;
    double bill,grocery,transport=0;

    public ClientData(){

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

    }
}
