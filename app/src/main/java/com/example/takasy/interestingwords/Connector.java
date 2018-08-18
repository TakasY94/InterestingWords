package com.example.takasy.interestingwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Connector {

    public static final String URL = "jdbc:mysql://35.228.95.53:3306/test?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String USER = "takasy94";
    public static final String PASSWORD = "1234";


    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement();) {

            if (connection != null)
                System.out.println ("Приложение подключилось к БД !");
            else
                System.out.println ("Приложение НЕ подключилось к БД ?");

            statement.execute(parser());
            System.out.println("Скрипт выполнен");

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public static String parser() {
        ArrayList<String> raw_array = new ArrayList<String>();
        HashMap<String, String> fin_array = new HashMap<String, String>();
        String a = "";
        try (FileInputStream reader = new FileInputStream("C:\\Users\\TakasY\\PycharmProjects\\HelloWorld\\a.txt");BufferedReader in = new BufferedReader(new InputStreamReader(reader, "utf-8"))) {
            while ((a = in.readLine()) != null)
            raw_array.add(a);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tit_start = 0,tit_finish = 0;
        int desc_start = 0,desc_finish = 0;
        for (int i=0; i<raw_array.size()-1; i++){
            String key="", value="";
            tit_start = raw_array.get(i).indexOf("'");
            tit_finish = raw_array.get(i).indexOf("'", tit_start + 1);
            desc_start = raw_array.get(i).indexOf("'", tit_finish + 1);
            desc_finish = raw_array.get(i).indexOf("'", desc_start + 1);
            fin_array.put(raw_array.get(i).substring(tit_start +1 , tit_finish), raw_array.get(i).substring(desc_start + 3, desc_finish) );

        }


        String query = "INSERT INTO test.words(title, description) VALUES ";
        Iterator itr = fin_array.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();

            if (!itr.hasNext()){
                query = query.concat("( '" + entry.getKey() + "', '" + entry.getValue()+ "')");
                continue;
            }

            query = query.concat("( '" + entry.getKey() + "', '" + entry.getValue()+ "'),");

        }

/*        for (Map.Entry entry : fin_array.entrySet()){
            query.concat("( '" + entry.getKey() + "', '" + entry.getValue()+ "')");
            if (entry.)
        }

*/
        System.out.println(fin_array.size());
        System.out.println(query);
        return query;
    }
}
