package Persistence;

public class PersistenceTest {
	public static void main(String[] args) {
	    CSVReader csvRead = new CSVReader();
	    JSONMaker makeJSON = new JSONMaker();
	    JSONReader readJSON = new JSONReader();
	    makeJSON.makeJSON(csvRead.readCSV("bin/Persistence/level.csv"), "bin/Persistence/level.json");
	    readJSON.readJSON("bin/Persistence/level.json");
	}
}
