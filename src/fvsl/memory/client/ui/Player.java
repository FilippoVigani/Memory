package fvsl.memory.client.ui;

import java.io.Serializable;

public class Player implements Serializable{
	private String name;
	
	public Player(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
