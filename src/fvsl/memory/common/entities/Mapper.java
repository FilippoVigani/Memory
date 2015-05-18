package fvsl.memory.common.entities;

import java.util.HashMap;

public class Mapper {
	HashMap<String, String> cardsMap;
	
	private static Mapper mapper;
	
	public Mapper(){
		cardsMap = new HashMap<String, String>();
		cardsMap.put("c1", "smile1.jpg");
	}
	
	public static Mapper getMapper(){
		if (mapper == null){
			mapper = new Mapper();
		}
		return mapper;
	}
}
