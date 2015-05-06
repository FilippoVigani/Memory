package fvsl.memory.client.ui;

import java.io.Serializable;

public class Lobby implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379632040815885727L;
	
	private String name;
	private int numberOfPlayers;
	private int numberOfPairs;
	private double turnTimer;
	
	public Lobby(String name, int numberOfPlayers, int numberOfPairs, double turnTimer) {
		super();
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfPairs = numberOfPairs;
		this.turnTimer = turnTimer;
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
	 * @return the numberOfPlayers
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	/**
	 * @param numberOfPlayers the numberOfPlayers to set
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	/**
	 * @return the numberOfPairs
	 */
	public int getNumberOfPairs() {
		return numberOfPairs;
	}
	/**
	 * @param numberOfPairs the numberOfPairs to set
	 */
	public void setNumberOfPairs(int numberOfPairs) {
		this.numberOfPairs = numberOfPairs;
	}
	/**
	 * @return the turnTimer
	 */
	public double getTurnTimer() {
		return turnTimer;
	}
	/**
	 * @param turnTimer the turnTimer to set
	 */
	public void setTurnTimer(double turnTimer) {
		this.turnTimer = turnTimer;
	}
	
	public String toString(){
		return name;
	}
	
}
