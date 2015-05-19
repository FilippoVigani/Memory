package fvsl.memory.common.entities;

import java.util.HashMap;

public class Mapper {
	private HashMap<String, String> cardsMap;
	
	private static Mapper mapper;
	
	public Mapper(){
		cardsMap = new HashMap<String, String>();
		cardsMap.put("c1", "smile.jpg");
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
