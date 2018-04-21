package edu.usfca.cs.merklex.level1;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
* https://stackoverflow.com/questions/9063296/how-can-i-write-maven-build-to-add-resources-to-classpath
* source: https://stackoverflow.com/questions/35884429/why-dependencies-do-not-accompany-the-made-package-by-maven
* */

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
            if(strings.length != 3){
                System.out.println("Please enter with 3 arguments");
                continue;
            }
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
            Map<String, String> map = new HashMap<>();
            buildMap(file, map);

            // print result
            String val = map.get(query);
            System.out.println(val);
        }
    }

    private static void buildMap(File file, Map<String, String> map) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        while (input.hasNextLine()) {
            sb.append(input.nextLine());
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) parser.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Set<String> set = jsonObject.keySet();
        Iterator<String> iter = set.iterator();

        while(iter.hasNext()){
            String key = iter.next();
            Object value = jsonObject.get(key);
            if(value instanceof JSONObject){
                helper(map, key, value);
            }
            else if(value instanceof JSONArray){
                int size = ((JSONArray) value).size();
                List<Object> list = new LinkedList<Object>((Collection<?>) value);
                for(int i = 0; i <= size - 1; i++){
                    if(list.get(i) instanceof String || list.get(i) instanceof Long){
                        map.put(key + '.' + i, String.valueOf(list.get(i)));
                    }
                    else{
                        helper(map, key + '.' + i, list.get(i));
                    }
                }
            }
            else{
                map.put(key, String.valueOf(value));
            }
        }

    }

    private static void helper(Map<String, String> map, String prev, Object obj){
        JSONObject jsonObject = ((JSONObject)obj);
        Set<String> set = jsonObject.keySet();
        Iterator<String> iter = set.iterator();

        while(iter.hasNext()){
            String key = iter.next();
            Object value = jsonObject.get(key);
            if(value instanceof JSONObject){
                helper(map, prev + "." + key, value);
            }
            else if(value instanceof JSONArray) {
                int size = ((JSONArray) value).size();
                List<Object> list = new LinkedList<Object>((Collection<?>) value);
                for(int i = 0; i <= size - 1; i++){
                    if(list.get(i) instanceof String || list.get(i) instanceof Long){
                        map.put(prev + '.' + key + '.' + i, String.valueOf(list.get(i)));
                    }
                    else{
                        helper(map, prev + '.' + key + '.' + i, list.get(i));
                    }
                }
            }
            else{
                map.put(prev + "." + key, String.valueOf(value));
            }
        }
    }

}
