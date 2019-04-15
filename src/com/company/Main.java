package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//JSON is a serializer/deserializer for data text files
//https://code.google.com/archive/p/json-simple/
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.lang.Float.parseFloat;

public class Main
{

    //Writes Worker class ArrayList to Json file
    public static void WriteJsonFile(String filename, ArrayList<Worker> workers)
    {
        JSONObject root = new JSONObject();
        JSONArray jsonWorkersArray = new JSONArray();
        try
        {
            for (int i = 0; i < workers.size(); i++)
            {
                jsonWorkersArray.add(CreateJsonObject(workers.get(i)));
            }
            root.put("root", jsonWorkersArray );
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter(filename))
        {
            file.write(root.toJSONString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Reads Json file strings and outputs a Worker class ArrayList
    public static ArrayList<Worker> ReadJsonFile(String filename)
    {
        File file = new File(filename);
        StringBuilder fileContents = new StringBuilder((int) file.length());

        String rawData = null;

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            rawData = fileContents.toString();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        JSONObject data;
        try
        {
            JSONParser helper = new JSONParser();
            data = (JSONObject) helper.parse(rawData);
        }
        catch (ParseException parse)
        {
            System.out.println(parse);
            return null;
        }

        // Note that these may throw several exceptions
        JSONArray jsonArray = (JSONArray) data.get("root");
        int length = jsonArray.size();
        ArrayList<Worker> parsedData = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
        {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            parsedData.add(JsonToClassParser(jsonObject));
        }

        return parsedData;
    }

    //Parses Worker class data to Json string
    public static JSONObject CreateJsonObject(Worker workerData)
    {
        JSONObject worker = new JSONObject();
        worker.put("name", workerData.name);
        worker.put("lastName", workerData.lastName);
        worker.put("workPlace", workerData.workPlace);
        worker.put("job", workerData.job);
        worker.put("earnings", String.valueOf(workerData.earnings));
        return worker;
    }

    //Parse Json string values to Worker class values
    public static Worker JsonToClassParser(JSONObject jsonObject)
    {
        return new Worker(jsonObject.get("name").toString(), jsonObject.get("lastName").toString(),
                jsonObject.get("workPlace").toString(), jsonObject.get("job").toString(),
                parseFloat(jsonObject.get("earnings").toString()));
    }

    public static void main(String[] args)
    {
        //Open GUI
        UserGUI userGUI = new UserGUI();
        userGUI.setVisible(true);
    }
}
