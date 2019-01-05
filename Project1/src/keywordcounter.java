import FibonacciHeap.MaxFibonacciHeap;
import FibonacciHeap.Node;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class keywordcounter {

    private static MaxFibonacciHeap maxHeap = new MaxFibonacciHeap();
    private static Hashtable<String, Node> hashTable = new Hashtable<>();

    public static void main(String[] args) {
        
        if (args.length == 1) {

            String fileName = args[0];

            StringBuilder outputStringBuilder = new StringBuilder();

            File file = new File(fileName);

            if (!file.exists()){

                file = new File(fileName+".txt");
            }

            try {

                FileReader fileReader = new FileReader(file);

                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String inputLine;
                final String prefix = "$";
                final int prefixSize = prefix.length();

                while((inputLine = bufferedReader.readLine()) != null) {

                    if (inputLine.toLowerCase().equals("stop")) {

                        stop();

                    } else {

                        if (inputLine.startsWith(prefix)) {

                            String parsedString = inputLine.substring(prefixSize);

                            String[] components = parsedString.split(" ");

                            if (components.length == 2) {

                                String keyword = components[0];
                                int count = Integer.parseInt(components[1]);

                                addKeyword(keyword, count);
                            }

                        } else {

                            int count = Integer.parseInt(inputLine);

                            runQuery(outputStringBuilder, count);
                        }
                    }
                }

            } catch (Exception exception) {

                String message = "Exception : " + exception.getLocalizedMessage();

                createOutputFile(message);
            }
        }
        else
        {
            createOutputFile("Invalid arguments");
        }
    }

    //Private Methods

    //Adds keyword to maxHeap or update the existing node
    private static void addKeyword(String keyword, Integer value){

        if (hashTable.containsKey(keyword)){

            Node node = hashTable.get(keyword);
            maxHeap.increaseKey(node, value);
        }
        else
        {
            Node newNode = maxHeap.insert(value);
            hashTable.put(keyword, newNode);
        }
    }

    //Extract top elements from the maxHeap
    private static void runQuery(StringBuilder outputStringBuilder, Integer count){

        Hashtable<String, Integer> deletedNodesTable = new Hashtable<>();

        while (count > 0) {

            Node node = maxHeap.removeMax();

            int data = node.getData();
            String keyword = returnNameForNode(node);

            deletedNodesTable.put(keyword, data);

            outputStringBuilder.append(keyword);

            if (count > 1) {

                outputStringBuilder.append(", ");
            }

            count--;
        }

        outputStringBuilder.append(System.getProperty("line.separator"));

        //Insert the removed max values into the heap
        for(Map.Entry entry: deletedNodesTable.entrySet()){

            addKeyword((String)entry.getKey(), (Integer)entry.getValue());
        }

        createOutputFile(outputStringBuilder.toString());
    }

    //Stop the execution of the program
    private static void stop(){

        System.exit(0);
    }

    //Returns keyword associated with the node in the hashTable
    private static String returnNameForNode(Node node){

        for(Map.Entry entry: hashTable.entrySet()){

            if(node.equals(entry.getValue())){

                String keyword = (String)entry.getKey();
                hashTable.remove(keyword);

                return keyword;
            }
        }

        return "";
    }

    //Writes output to a file
    private static void createOutputFile(String output){

        File file = new File("output_file.txt");

        try {

            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write(output);
            fileWriter.flush();
            fileWriter.close();

        }
        catch(IOException ex) {

            ex.printStackTrace();
        }
    }
}
