package edu.usfca.cs.merklex;

import java.io.File;
import java.util.Scanner;

public class JSONTool {

    public static void main(String[] args){
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

        }
    }

}
