package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.mockaroo.MockPersonRunner;

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
            System.err.println("ERROR: File not found");
        }
    }

    public List<List<String>> parse(){
        //checks if bufferedReader was able to be initialized or not (if file is invalid, it is not)
        if (_bufferedReader == null){
            return null;
        }
        //boolean representing if we are reading the mock data or not
        boolean mock = false;
        List<List<String>> data = new ArrayList<>();
        //have to read first line to make sure it matches StarID,ProperName,X,Y,Z format
        try{
            String firstLine = _bufferedReader.readLine();
            String[] first = firstLine.split(",");
            if (first.length == 5  || first.length == 6) {
                if (first.length == 5) {
                    if (!first[0].equals("StarID") || !first[1].equals("ProperName") ||
                            !first[2].equals("X") || !first[3].equals("Y") || !first[4].equals("Z")) {
                        System.err.println("ERROR: First line of data does not match 'StarID,ProperName,X,Y,Z'");
                        return null;
                    }
                }
                else {
                    mock = true;
                    if (!first[0].equals("First Name") || !first[1].equals("Last Name") ||
                            !first[2].equals("Datetime") || !first[3].equals("Email Address")
                            || !first[4].equals("Gender") || !first[5].equals("Street Address")) {
                        System.err.println("ERROR: First line of data does not match 'First Name" +
                                ",Last Name,Datetime,Email Address,Gender,Street Address'");
                        return null;
                    }
                }
            }
            else {
                System.err.println("ERROR: First line of data is invalid");
            }
        }
        catch (IOException e){
            System.err.println("ERROR: IOException in CSV");
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
                    if (mock){
                        if (MockPersonRunner.checkData(currData)) {
                            //converting array to list- THIS MIGHT NOT BE EFFICIENT
                            for (int i = 0; i < currData.length; i++) {
                                currDataList.add(currData[i]);
                            }
                            data.add(currDataList);
                        } else {
                            return null;
                        }
                    }
                    else {
                        if (Stars.checkData(currData)) {
                            //converting array to list- THIS MIGHT NOT BE EFFICIENT
                            for (int i = 0; i < currData.length; i++) {
                                currDataList.add(currData[i]);
                            }
                            data.add(currDataList);
                        } else {
                            return null;
                        }
                    }
                }
                //this happens when we reach end of file, break
                else {
                    break;
                }
            }
            //handle ERROR for readLine method
            catch(IOException e){
                System.err.println("ERROR: IOException in CSV" + e);
                return null;
            }
        }
        return data;
    }
}
