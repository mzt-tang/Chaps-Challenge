package Persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class CSVReader {

  /**
   * @param filePath filepath of csv file.
   */
  public ArrayList<ArrayList<String>> readCSV(String filePath) {
	ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>();
    try {
    	//Creating a JSONObject object
    	
    	//Read CSV file
		BufferedReader buffRead = new BufferedReader(new FileReader(filePath));
		//The current line of the CSV file being read
	    String currentData;
	    //The current line index (y position)
	    int yIndex = 0;
	    while((currentData = buffRead.readLine()) != null) {
	    	ArrayList<String> currentLineList = new ArrayList<String>();
	    	String[] data = currentData.split(",");
	    	//Remove csv "header"
//	    	if(yIndex == 0) {
//	    		String[] indexZeroTable = data[0].split("¿");
//	    		System.out.println(indexZeroTable[0]);
//	    		data[0] = indexZeroTable[1];
//	    	}
	    	for(String entry : data) {
	    		currentLineList.add(entry);
	    	}
	    	returnList.add(currentLineList);
	    	yIndex++;
	    }
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    return returnList;
  }
}
