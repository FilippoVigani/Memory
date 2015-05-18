package fvsl.memory.common.entities;

public class Card {
	//Position ID
	private String id;
	//Figure value
	private String value;
	
	private boolean isTurned;
	
	public Card(String id, String value) {
		super();
		this.id = id;
		this.value = value;
		isTurned = false;
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
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the isTurned
	 */
	public boolean isTurned() {
		return isTurned;
	}
	/**
	 * @param isTurned the isTurned to set
	 */
	public void setTurned(boolean isTurned) {
		this.isTurned = isTurned;
	}
}
