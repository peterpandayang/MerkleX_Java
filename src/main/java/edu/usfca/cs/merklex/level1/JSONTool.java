package edu.usfca.cs.merklex.level1;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JSONTool {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String line = "";

        while(true){
            System.out.println("enter your command like: ./json_tool <json_file_path> <query_string>");
            System.out.println("enter EOF to exit");
            line = scanner.nextLine();

            if(line.equals("EOF")){
                break;
            }

            String[] strings = line.split(" ");
            String run = strings[0];
            String filename = strings[1];
            String query = strings[2];

            if(!run.equals("./json_tool")){
                System.out.println("No executable found");
                continue;
            }
            File file = new File("files/" + filename);
            if(!file.exists()){
                System.out.println("No json file found");
                continue;
            }

            // try to query...
            queryData(file, query);
        }
    }

    private static void queryData(File file, String query) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        while (input.hasNextLine()) {
            sb.append(input.nextLine());
        }

        System.out.println(sb.toString());

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
    }

}
