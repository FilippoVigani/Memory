package fvsl.memory.common.entities;

import java.io.File;
import java.util.HashMap;

public class Mapper {
	private HashMap<String, String> cardsMap;
	
	private static Mapper mapper;
	private File folder = new File("/Memory/res");
	private File[] listOfFiles = folder.listFiles();

	
	public Mapper(){
		cardsMap = new HashMap<String, String>();
		 for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		        cardsMap.put(("c"+i), listOfFiles[i].getName());
		      } 
		    }
		
	}
	
	public static Mapper getMapper(){
		if (mapper == null){
			mapper = new Mapper();
		}
		return mapper;
	}

	/**
	 * @return the cardsMap
	 */
	public HashMap<String, String> getCardsMap() {
		return cardsMap;
	}

	/**
	 * @param cardsMap the cardsMap to set
	 */
	public void setCardsMap(HashMap<String, String> cardsMap) {
		this.cardsMap = cardsMap;
	}
}
