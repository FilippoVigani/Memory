package fvsl.memory.common.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6587194458690535519L;
	private String name;
	private boolean ready;
	private Integer score; // Used by client only

	/**
	 * Instantiates a new player.
	 * 
	 * @param name
	 *            the name
	 */
	public Player(String name) {
		super();
		this.name = name;
		ready = false;
		score = 0;
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
	 * Checks if is ready.
	 * 
	 * @return true, if is ready
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * Sets the ready.
	 * 
	 * @param ready
	 *            the new ready
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	/**
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * Sets the score.
	 * 
	 * @param score
	 *            the new score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
}
