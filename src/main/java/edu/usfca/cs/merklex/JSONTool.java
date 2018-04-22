package edu.usfca.cs.merklex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

            Map<String, Object> record = new HashMap<>();
            buildRecord(file, record);

            // print the query result from the record
            printQueryResult(record.get("root"), query);

        }
    }

    /*
    * Build the record map in the format of json
    * */
    private static void buildRecord(File file, Map<String, Object> record) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        while (input.hasNextLine()) {
            sb.append(input.nextLine());
        }

        String data = sb.toString();
        int i = 0;
        int len = data.length();
        int j = len - 1;
        while(i <= len - 1 && (data.charAt(i) != '{' && data.charAt(i) != '[')){
            i += 1;
        }
        while(j >= 0 && (data.charAt(j) != '}' && data.charAt(j) != ']')){
            j -= 1;
        }
        if(i != j){
            char c = data.charAt(i);
            String s = data.substring(i + 1, j).trim();
            if(c == '{'){
                record.put("root", processJSONObject(s));
            }
            else if(c == '['){
                record.put("root", processJSONArray(s));
            }
        }
    }

    /*
    * Parse the data in the JSONObject format
    * */
    private static Object processJSONObject(String data){
        if(data.length() == 0){
            return "";
        }
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        int len = data.length();
        // stack will store the index of the first { or first [ from the data, which ever comes first
        Stack<Integer> stack = new Stack<>();
        char parenth = ' ';
        String key = "";
        while(i <= len - 1){
            if(stack.size() == 0){
                if(key.length() == 0){
                    // should process key
                    if(data.charAt(i) == ' ' || data.charAt(i) == ':' || data.charAt(i) == ','){
                        i += 1;
                    }
                    else if(data.charAt(i) == '\"'){
                        int start = i + 1;
                        i += 1;
                        while(i <= data.length() - 1 && data.charAt(i) != '\"'){
                            i += 1;
                        }
                        key = data.substring(start, i);
                        i += 1;
                    }

                }
                else{
                    // should process value
                    if(data.charAt(i) == ' ' || data.charAt(i) == ',' || data.charAt(i) == ':'){
                        i += 1;
                    }
                    else if(data.charAt(i) == '{' || data.charAt(i) == '['){
                        parenth = data.charAt(i);
                        stack.push(i);
                        i += 1;
                    }
                    else{
                        int start = i;
                        if(data.charAt(i) == '\"'){
                            start += 1;
                        }
                        i += 1;
                        while(i <= data.length() - 1 && (data.charAt(i) != '\"' && data.charAt(i) != ' ' && data.charAt(i) != ',')){
                            i += 1;
                        }
                        String val = data.substring(start, i);
                        map.put(key, val);
                        key = "";
                        i += 1;
                    }
                }
            }
            else {
                int start = i;
                while(i <= data.length() - 1){
                    if(data.charAt(i) == '{' || data.charAt(i) == '['){
                        stack.push(i);
                        i += 1;
                    }
                    else if(data.charAt(i) == ']' || data.charAt(i) == '}'){
                        stack.pop();
                        i += 1;
                        if(stack.size() == 0){
                            break;
                        }
                    }
                    else {
                        i += 1;
                    }
                }
                if(parenth == '{'){
                    map.put(key, processJSONObject(data.substring(start, i - 1).trim()));
                    key = "";
                }else if(parenth == '['){
                    map.put(key, processJSONArray(data.substring(start, i - 1).trim()));
                    key = "";
                }
            }
        }
        return map;
    }

    /*
    * Parse the data in the format of JSONArray
    * */
    private static Object processJSONArray(String data){
        List<Object> list = new ArrayList<>();
        int i = 0;
        int len = data.length();
        // if there is { or [ in the array
        Object element = null;
        Stack<Integer> stack = new Stack<>();
        while(i <= len - 1){
            if(stack.size() == 0){
                if(data.charAt(i) == ' '){
                    i += 1;
                }
                else if(data.charAt(i) == '{'){
                    stack.push(i);
                    i += 1;
                }
                else if(data.charAt(i) == ','){
                    list.add(element);
                    element = null;
                    i += 1;
                }
                else {
                    // it has to be "apple" or 1 ->string or number
                    int start = i;
                    if(data.charAt(i) == '\"'){
                        start += 1;
                    }
                    i += 1;
                    while(i <= len - 1 && (data.charAt(i) != '\"' && data.charAt(i) != ',')){
                        i += 1;
                    }
                    element = data.substring(start, i);
                    if(i <= len - 1 && data.charAt(i) == '\"'){
                        i += 1;
                    }
                }
            }
            else{
                // there is json object in the array
                int start = i;
                while(i <= data.length() - 1){
                    if(data.charAt(i) == '{' || data.charAt(i) == '['){
                        stack.push(i);
                        i += 1;
                    }
                    else if(data.charAt(i) == ']' || data.charAt(i) == '}'){
                        stack.pop();
                        i += 1;
                        if(stack.size() == 0){
                            break;
                        }
                    }
                    else {
                        i += 1;
                    }
                }
                element = processJSONObject(data.substring(start, i - 1).trim());
            }
        }
        if(element != null){
            list.add(element);
        }
        return list;
    }

    /*
    * Process the query
    * */
    private static void printQueryResult(Object record, String query){
        if(record == null){
            return;
        }
        if(record instanceof Map){
            Map map = (Map) record;
            if(query.indexOf('.') == -1){
                if(map.containsKey(query)){
                    String val = (String) map.get(query);
                    System.out.println(val);
                }
                return;
            }
            int index = query.indexOf('.');
            String target = query.substring(0, index);
            if(map.containsKey(target)){
                printQueryResult(map.get(target), query.substring(index + 1));
            }
            return;
        }
        else if(record instanceof ArrayList){
            List<Object> list = (List<Object>) record;
            if(query.indexOf('.') == -1){
                int i = Integer.parseInt(query);
                if(i <= list.size() - 1){
                    String val = (String) list.get(i);
                    System.out.println(val);
                }
                return;
            }
            int end = query.indexOf('.');
            int i = Integer.parseInt(query.substring(0, end));
            if(i <= list.size() - 1){
                printQueryResult(list.get(i), query.substring(end + 1));
            }
        }
    }

}



