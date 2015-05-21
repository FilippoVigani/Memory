package fvsl.memory.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

public class Mapper {
	private HashMap<String, String> cardsMap;

	private static Mapper mapper;

	public Mapper() {
		File[] listOfFiles = new File(StringResources.resFig.toString()).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(StringResources.jpg.toString()) || name.endsWith(StringResources.png.toString()) || name.endsWith(StringResources.jpeg.toString());
			}
		});
		cardsMap = new HashMap<String, String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// System.out.println("File " + listOfFiles[i].getName());
				cardsMap.put((StringResources.idLetter.toString() + i), StringResources.fig + listOfFiles[i].getName());
			}
		}
	}

	public static Mapper getMapper() {
		if (mapper == null) {
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
	 * @param cardsMap
	 *            the cardsMap to set
	 */
	public void setCardsMap(HashMap<String, String> cardsMap) {
		this.cardsMap = cardsMap;
	}
}
