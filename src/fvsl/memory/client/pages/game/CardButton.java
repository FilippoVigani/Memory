package fvsl.memory.client.pages.game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import fvsl.memory.common.entities.Card;
import fvsl.memory.common.util.Mapper;

public class CardButton extends JButton {

	private Card card;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3176672112421455182L;

	public CardButton(){
		
	}
	
	public CardButton(Card card) {
		setCard(card);
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
		String path = "/card_folded.jpg";
		if (!(card.getValue() == null || card.getValue().isEmpty())){
			path = Mapper.getMapper().getCardsMap().get(card.getValue());
		}
		
		ImageIcon img = new ImageIcon(this.getClass().getResource(path)); 
		
		setIcon(img);
	}
}
