package com.company;

import jdk.jfr.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ClientData implements Runnable {
    ArrayList<ClientData> clients = new ArrayList();
    String username;
    String fullname;
    String balance;
    String currency;
    String loan = "00";
    double amount = 0.0D;
    double bill = 0.0D;
    double grocery = 0.0D;
    double transport = 0.0D;
    double restaurant = 0.0D;
    double shopping = 0.0D;

    String infoHistory;

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
    private static void writeInfo(String info, File f) throws IOException{
        FileWriter fw = new FileWriter(f, true);
        fw.write("\n"+info);
        fw.close();
    }
    public void run() {
        while(true) {
            try {
                String category = this.reader.readLine();
                String amountStr = this.reader.readLine();
                String date = this.reader.readLine();
                String description = this.reader.readLine();
                this.amount = Double.parseDouble(amountStr);
                this.writer.write(category + "\n");
                if (category.equals("Bills")) {
                    this.bill += this.amount;
                    this.writer.write(this.bill + "\n");
                }

                if (category.equals("Grocery")) {
                    this.grocery += this.amount;
                    this.writer.write(this.grocery + "\n");
                }

                if (category.equals("Restaurant")) {
                    this.restaurant += this.amount;
                    this.writer.write(this.restaurant + "\n");
                }

                if (category.equals("Transport")) {
                    this.transport += this.amount;
                    this.writer.write(this.transport + "\n");
                }

                if (category.equals("Shopping")) {
                    this.shopping += this.amount;
                    this.writer.write(this.shopping + "\n");
                }
                this.writer.flush();
                infoHistory=date+" "+category+": "+ amountStr+", Description: "+description+" ";
                System.out.println(infoHistory);
                File f = new File("Files/Accounts/" + username + ".txt");
                writeInfo(infoHistory,f);
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
