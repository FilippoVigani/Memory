package fvsl.memory.client.ui;

import java.io.Serializable;

public class Player implements Serializable{
	private String name;
	private boolean ready;
	
	public Player(String name) {
		super();
		this.name = name;
		ready = false;
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

	/**
	 * @return the ready
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * @param ready the ready to set
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
}
