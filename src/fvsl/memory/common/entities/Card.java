package fvsl.memory.common.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Card.
 */
/**
 * @author Filippo Vigani
 *
 */
public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2830066687217678432L;
	/**
	 * The ID to identify the position and the card itself
	 */
	private String id;
	/**
	 * The value representing the figure to be displayed on the card once turned
	 */
	private String value;

	private boolean isTurned;

	/**
	 * Instantiates a new card.
	 * 
	 * @param id
	 *            the id
	 * @param value
	 *            the value
	 */
	public Card(String id, String value) {
		super();
		this.id = id;
		this.value = value;
		isTurned = false;
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
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Checks if is turned.
	 * 
	 * @return true, if is turned
	 */
	public boolean isTurned() {
		return isTurned;
	}

	/**
	 * Sets the turned.
	 * 
	 * @param isTurned
	 *            the new turned
	 */
	public void setTurned(boolean isTurned) {
		this.isTurned = isTurned;
	}
}
