package com.company;

import jdk.jfr.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ClientData implements Runnable {
    HashMap<String,String> map=new HashMap<>();
    ArrayList<ClientData> clients = new ArrayList();
    String username,fullname,balance,currency;
    String bill,grocery,transport,restaurant,shopping,loan = "00";
    double amount;

    String infoHistory;
    int historyLength=0;

    BufferedReader reader;
    BufferedWriter writer;

    //function to read user data from file
    public void readData(BufferedReader input) {
        try {
            this.username = input.readLine();
            this.fullname = input.readLine();
            this.balance = input.readLine();
            this.currency = input.readLine();
            this.loan = input.readLine();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //to initialize categories and and balance from user hashmap file
    private void readHashdata(){
        balance=map.get("Balance");
        bill=map.get("Bill");
        transport=map.get("Transportation");
        grocery=map.get("Grocery");
        restaurant=map.get("Restaurant");
        shopping=map.get("Shopping");
        loan=map.get("Loan");
    }

    public ClientData(String username, BufferedWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
        this.username=username;

        try {
            //reading hashmap
            readHashmap(map);
            readHashdata();

            //getting basic user data from file
            //and writing them to client
            File file = new File("Files/Accounts/" + username + ".txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            this.readData(input);
            writer.write(username + "\n");
            writer.write(this.fullname + "\n");
            writer.write(this.balance + "\n");
            writer.write(this.loan + "\n");
            writer.write(this.currency + "\n");
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
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
                String category = reader.readLine();
                String amountStr = reader.readLine();
                String date = reader.readLine();
                String description = reader.readLine();
                amount = Double.parseDouble(amountStr);
                writer.write(category + "\n");
                if (category.equals("Bills")) {
                    bill=(Double.parseDouble(bill)+amount)+"";
                    System.out.println(bill);
                    writer.write(bill + "\n");
                }

                if (category.equals("Grocery")) {
                    grocery=(Double.parseDouble(grocery)+amount)+"";
                    writer.write(grocery + "\n");
                }

                if (category.equals("Restaurant")) {
                    restaurant=(Double.parseDouble(restaurant)+amount)+"";
                    writer.write(restaurant + "\n");
                }

                if (category.equals("Transport")) {
                    transport=(Double.parseDouble(transport)+amount)+"";
                    this.writer.write(transport + "\n");
                }

                if (category.equals("Shopping")) {
                    shopping=(Double.parseDouble(shopping)+amount)+"";
                    writer.write(shopping + "\n");
                }

                writer.flush();
                infoHistory=date+" "+category+": "+ amountStr+", Description: "+description+" ";
                System.out.println(infoHistory);
                File f = new File("Files/Accounts/" + this.username + ".txt");
                writeInfo(infoHistory,f);
                historyLength++;
                updateHashmapFile();
            }
            catch (IOException e) {
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

        //creating a new file to store basic user data taken from sign up page
        File file = new File("Files/Accounts/" + username + ".txt");
        PrintWriter out = new PrintWriter(file);
        out.println(username + "\n" + fullname + "\n" + balance + "\n" + currency + "\n" + this.loan + "\nend");
        System.out.println(username + "\n" + fullname + "\n" + balance + "\n" + currency + "\nend");
        out.close();

        //Creating a file and initializing users balance, and other categories
        FileWriter fileWriter=new FileWriter("Files/Hashamp/"+username+"MAP"+".txt",true);
        fileWriter.write("Balance"+" "+balance+"\n"+
                "Bills 0.0\n"+"Grocery 0.0\n"+"Transportation 0.0\n"+"Restaurant 0.0\n"+"Shopping 0.0\n"+"Loan 0.0\n");
        fileWriter.flush();
        fileWriter.close();
    }

    private void updateHashmapFile() throws IOException{
        //Creating a file and initializing users balance, and other categories
        FileWriter fileWriter=new FileWriter("Files/Hashamp/"+username+"MAP"+".txt");
        fileWriter.write("Balance"+" "+balance+"\n"+
                "Bill"+" "+bill+"\n"+"Grocery"+" "+grocery+"\n"+"Transportation"+" "+transport+"\n"+
                "Restaurant"+" "+restaurant+"\n"+"Shopping"+" "+shopping+"\n"+"Loan"+" "+loan+"\n");
        fileWriter.flush();
        fileWriter.close();
    }

    //function to read individual users balance
    //and other spending from file and put it in individual user's hashmap
    private void readHashmap(HashMap<String,String> map) throws IOException{
        Scanner sc=new Scanner(new File("Files/Hashamp/"+username+"MAP"+".txt"));
        while(sc.hasNext()){
            String key=sc.next();
            String value=sc.next();
            map.put(key,value);
        }
        sc.close();
    }
}
