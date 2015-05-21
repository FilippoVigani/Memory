package fvsl.memory.common.entities;

import java.io.Serializable;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * Represents a lobby where players can connect or disconnect, holds data about the game settings
 */
/**
 * @author Filippo Vigani
 *
 */
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

	/**
	 * Instantiates a new lobby.
	 * 
	 * @param name
	 *            the name
	 * @param numberOfPlayers
	 *            the number of players
	 * @param numberOfPairs
	 *            the number of pairs
	 * @param turnTimer
	 *            the turn timer
	 * @param password
	 *            the password
	 */
	public Lobby(String name, int numberOfPlayers, int numberOfPairs, double turnTimer, String password) {
		super();
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.numberOfPairs = numberOfPairs;
		this.turnTimer = turnTimer;
		this.password = password;
		connectedPlayers = new Vector<Player>();
	}

	/**
	 * Instantiates a new lobby.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param numberOfPlayers
	 *            the number of players
	 * @param numberOfPairs
	 *            the number of pairs
	 * @param turnTimer
	 *            the turn timer
	 * @param password
	 *            the password
	 */
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

	/**
	 * Instantiates a new lobby.
	 */
	public Lobby() {
		connectedPlayers = new Vector<Player>();
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the number of players.
	 * 
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * Sets the number of players.
	 * 
	 * @param numberOfPlayers
	 *            the new number of players
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	/**
	 * Gets the number of pairs.
	 * 
	 * @return the number of pairs
	 */
	public int getNumberOfPairs() {
		return numberOfPairs;
	}

	/**
	 * Sets the number of pairs.
	 * 
	 * @param numberOfPairs
	 *            the new number of pairs
	 */
	public void setNumberOfPairs(int numberOfPairs) {
		this.numberOfPairs = numberOfPairs;
	}

	/**
	 * Gets the turn timer.
	 * 
	 * @return the turn timer
	 */
	public double getTurnTimer() {
		return turnTimer;
	}

	/**
	 * Sets the turn timer.
	 * 
	 * @param turnTimer
	 *            the new turn timer
	 */
	public void setTurnTimer(double turnTimer) {
		this.turnTimer = turnTimer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Check password.
	 * 
	 * @param password
	 *            the password
	 * @return true, if successful
	 */
	public boolean checkPassword(String password) {
		return (this.password == null || this.password.equals("")) ? true : this.password.equals(password);
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the connected players.
	 * 
	 * @return the connected players
	 */
	public Vector<Player> getConnectedPlayers() {
		return connectedPlayers;
	}

	/**
	 * Sets the connected players.
	 * 
	 * @param connectedPlayers
	 *            the new connected players
	 */
	public void setConnectedPlayers(Vector<Player> connectedPlayers) {
		this.connectedPlayers = connectedPlayers;
	}

	/**
	 * Gets the connected player by name.
	 * 
	 * @param name
	 *            the name
	 * @return the connected player by name
	 */
	public Player getConnectedPlayerByName(String name) {
		Player player = null;
		boolean found = false;
		for (int i = 0; !found && i < getConnectedPlayers().size(); i++) {
			player = getConnectedPlayers().get(i);
			found = player.getName().equals(name);
		}

		return found ? player : null;
	}

	/**
	 * Gets the owner.
	 * 
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 * 
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
