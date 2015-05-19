package fvsl.memory.common.entities;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CardButton extends JButton {

	private Card card;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3176672112421455182L;

	public CardButton(Card card) {
		this.card = card;
		String path = "cartacoperta.jpg";
		if (!(card.getValue() == null || card.getValue().isEmpty())){
			path = Mapper.getMapper().getCardsMap().get(card.getValue());
		}
		Image img = new ImageIcon(this.getClass().getResource(path)).getImage(); 
		
		setIcon(new ImageIcon(img));
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
