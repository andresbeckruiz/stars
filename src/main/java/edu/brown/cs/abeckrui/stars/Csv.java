package edu.brown.cs.abeckrui.stars;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Csv {

    private BufferedReader _bufferedReader;

    /**
     *
     * @param file
     */

    public Csv(File file){
        try {
            _bufferedReader = new BufferedReader(new FileReader(file));
        }
        //catch exception from initializing filereader
        catch (FileNotFoundException e)  {
            System.err.println("Error: File not found");
        }
    }

    public List<List<String>> parse(){
        //checks if bufferedReader was able to be initialized or not (if file is invalid, it is not)
        if (_bufferedReader == null){
            return null;
        }
        List<List<String>> data = new ArrayList<>();
        //have to read first line to make sure it matches StarID,ProperName,X,Y,Z format
        try{
            String firstLine = _bufferedReader.readLine();
            String[] first = firstLine.split(",");
            if (!first[0].equals("StarID") || !first[1].equals("ProperName") || !first[2].equals("X")
                || !first[3].equals("Y") || !first[4].equals("Z")){
                System.err.println("Error: First line of data does not match 'StarID,ProperName,X,Y,Z'");
                return null;
            }
        }
        catch (IOException e){
            System.err.println("Error: IOException in CSV");
            return null;
        }
        while (true) {
            try{
                String currLine = _bufferedReader.readLine();
                //check to see if there are lines left to read
                if (currLine != null){
                    String[] currData = currLine.split(",");
                    List<String> currDataList = new ArrayList<String>();
                    //see stars class for specific
                    if (Stars.checkData(currData)){
                        //converting array to list- THIS MIGHT NOT BE EFFICIENT
                        for (int i = 0; i < currData.length; i++){
                            currDataList.add(currData[i]);
                        }
                        data.add(currDataList);
                    }
                    else{
                        return null;
                    }
                }
                //this happens when we reach end of file, break
                else {
                    break;
                }
            }
            //handle error for readLine method
            catch(IOException e){
                System.err.println("Error: IOException in CSV" + e);
                return null;
            }
        }
        return data;
    }
}
