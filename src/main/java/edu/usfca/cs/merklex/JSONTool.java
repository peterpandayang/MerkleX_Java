package edu.usfca.cs.merklex;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class JSONTool {

    static class Query{
        String[] queries;
        int p;
        int len;
        public Query(String[] queries){
            this.queries = queries;
            len = queries.length;
            p = 0;
        }
    }

    static class Status{
        int arrayElementSeen = 0;
    }

    public static void main(String[] args) throws IOException {
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
            String[] queries = query.split(".");

            queryWhileParsing(file, new Query(queries));

        }
    }

    private static void queryWhileParsing(File file, Query query) throws IOException {

        InputStream inputStream = new FileInputStream(file);
        // the buffer size is statically configured to 10 MB
        byte[] buffer = new byte[10 * 1024 * 1024];
        BufferedInputStream bin = new BufferedInputStream(inputStream);
        int byteread;
        String data = null;
        // level is ensured by the stack's size
        Stack<Integer> stack = new Stack<>();
        Status status = new Status();
        while((byteread = bin.read(buffer)) != -1){
            data = new String(buffer);
            queryFromData(data, query, stack, status);
        }
    }

    private static void queryFromData(String data, Query query, Stack<Integer> stack, Status status){
        long i = 0;
        long len = data.length();
        /*
        * My logic here is that firstly get the next query and keep checking
        * p in the query stands for the level of the query; if the stack's size
        * is the same as p, it means that the result could be found in the current chunk
        * of data; if the query could be found, add the point p in query by one, otherwise
        * just read the next data chunk.
        *
        * Also there could be some case that it is consuming the jsonarray and I use the
        * status to update how many elements have been seen so far.
        *
        * */
    }

}
