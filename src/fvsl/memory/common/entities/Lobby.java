package fvsl.memory.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

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
	private Vector<Player> connectedPlayers;
	private Player owner;
	
	public Lobby(String name, int numberOfPlayers, int numberOfPairs, double turnTimer, String password) {
		super();
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfPairs = numberOfPairs;
		this.turnTimer = turnTimer;
		this.password = password;
		connectedPlayers = new Vector<Player>();
	}
	
	public Lobby(String id, String name, int numberOfPlayers, int numberOfPairs, double turnTimer, String password) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfPairs = numberOfPairs;
		this.turnTimer = turnTimer;
		this.password = password;
		connectedPlayers = new Vector<Player>();
	}
	
	
	public Lobby() {
		connectedPlayers = new Vector<Player>();
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

	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * @return the connectedPlayers
	 */
	public Vector<Player> getConnectedPlayers() {
		return connectedPlayers;
	}

	/**
	 * @param connectedPlayers the connectedPlayers to set
	 */
	public void setConnectedPlayers(Vector<Player> connectedPlayers) {
		this.connectedPlayers = connectedPlayers;
	}

	public Player getConnectedPlayerByName(String name) {
		Player player = null;
		boolean found = false;
		for (int i = 0; !found && i < getConnectedPlayers().size(); i++){
			player = getConnectedPlayers().get(i);
			found = player.getName().equals(name);
		}
		
		return found?player:null;
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
