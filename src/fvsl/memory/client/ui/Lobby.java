package fvsl.memory.client.ui;

import java.io.Serializable;
import java.util.ArrayList;

public class Lobby implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379632040815885727L;
	
	private String id;
	private String name;
	private int numberOfPlayers;
	private int numberOfPairs;
	private double turnTimer;
	private String password;
	private ArrayList<Player> connectedPlayers;
	private Player owner;
	
	public Lobby(String name, int numberOfPlayers, int numberOfPairs, double turnTimer, String password) {
		super();
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfPairs = numberOfPairs;
		this.turnTimer = turnTimer;
		this.password = password;
		connectedPlayers = new ArrayList<Player>();
	}
	
	public Lobby(String id, String name, int numberOfPlayers, int numberOfPairs, double turnTimer, String password) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfPairs = numberOfPairs;
		this.turnTimer = turnTimer;
		this.password = password;
		connectedPlayers = new ArrayList<Player>();
	}
	
	
	public Lobby() {
		connectedPlayers = new ArrayList<Player>();
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean checkPassword(String password){
		return (this.password == null || this.password.equals("")) ? true : this.password.equals(password);
	}

	/**
	 * @return the connectedPlayers
	 */
	public ArrayList<Player> getConnectedPlayers() {
		return connectedPlayers;
	}

	/**
	 * @param connectedPlayers the connectedPlayers to set
	 */
	public void setConnectedPlayers(ArrayList<Player> connectedPlayers) {
		this.connectedPlayers = connectedPlayers;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
