package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientData implements Runnable {
    ArrayList<ClientData> clients = new ArrayList();
    String username, fullname, balance, currency, loan = "00";
    double amount=0, bill=0, grocery=0, transport=0, restaurant=0, shopping=0;
    BufferedReader reader;
    BufferedWriter writer;
    public void readData(BufferedReader input) {
        try {
            this.username = input.readLine();
            this.fullname = input.readLine();
            this.balance = input.readLine();
            this.currency = input.readLine();
            this.loan = input.readLine();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public ClientData(String username, BufferedWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;

        try {
            File file = new File("Files/Accounts/" + username + ".txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            this.readData(input);
            writer.write(username + "\n");
            writer.write(this.fullname + "\n");
            writer.write(this.balance + "\n");
            writer.write(this.loan + "\n");
            writer.write(this.currency + "\n");
            writer.flush();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void run() {
        while(true) {
            try {
                String category = this.reader.readLine();
                String amountStr = this.reader.readLine();

                String date = this.reader.readLine();
                String description = this.reader.readLine();
                System.out.println(category+" "+amountStr+" "+date+" "+description);
                amount = Double.parseDouble(amountStr);
                writer.write(category+"\n");
                if(category.equals("Bills")){
                    bill+=amount;
                    writer.write(bill+"\n");
                }
                if(category.equals("Grocery")){
                    grocery+=amount;
                    writer.write(grocery+"\n");
                }
                if(category.equals("Restaurant")){
                    restaurant+=amount;
                    writer.write(restaurant+"\n");
                }
                if(category.equals("Transport")){
                    transport+=amount;
                    writer.write(transport+"\n");
                }
                if(category.equals("Shopping")){
                    shopping+=amount;
                    writer.write(shopping+"\n");
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ClientData(String username, String fullname, String balance, String currency, BufferedWriter writer, BufferedReader reader) throws FileNotFoundException, IOException {
        this.username = username;
        this.fullname = fullname;
        this.balance = balance;
        this.currency = currency;
        this.reader = reader;
        this.writer = writer;
        File file = new File("Files/Accounts/" + username + ".txt");
        PrintWriter out = new PrintWriter(file);
        out.println(username + "\n" + fullname + "\n" + balance + "\n" + currency + "\n" + this.loan + "\nend");
        System.out.println(username + "\n" + fullname + "\n" + balance + "\n" + currency + "\nend");
        out.close();
    }
}