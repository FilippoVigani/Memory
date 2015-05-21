package fvsl.memory.client.pages.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import fvsl.memory.common.entities.Card;
import fvsl.memory.common.util.Mapper;
import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * The Class CardButton.
 */
public class CardButton extends JButton {

	private Card card;
	private Image image;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3176672112421455182L;

	/**
	 * Instantiates a new card button.
	 */
	public CardButton() {
		// setLayout(new BorderLayout());

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				JButton btn = (JButton) e.getComponent();
				Dimension size = btn.getSize();
				if (size.width > size.height) {
					size.width = -1;
				} else {
					size.height = -1;
				}
				Image scaled = image.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
				if (image.getHeight(btn) != size.height && image.getWidth(btn) != size.width) {
					btn.setIcon(new ImageIcon(scaled));

					btn.removeComponentListener(this);
				}
			}

		});
	}

	/**
	 * Instantiates a new card button.
	 * 
	 * @param card
	 *            the card
	 */
	public CardButton(Card card) {
		setCard(card);
	}

	/**
	 * Gets the card.
	 * 
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * Sets the card.
	 * 
	 * @param card
	 *            the new card
	 */
	public void setCard(Card card) {
		this.card = card;
		String path = StringResources.foldedCard.toString();
		if (!(card.getValue() == null || card.getValue().isEmpty())) {
			path = Mapper.getMapper().getCardsMap().get(card.getValue());
		}
		image = new ImageIcon(this.getClass().getResource(path)).getImage();
		// ImageIcon img = new ImageIcon(image);

		// JLabel imgContainer = new JLabel(img);
		// add(imgContainer, BorderLayout.CENTER);
		if (getSize().height > 0 && getSize().width > 0) {
			Image scaled = image.getScaledInstance(getSize().width, getSize().height, java.awt.Image.SCALE_SMOOTH);
			setIcon(new ImageIcon(scaled));
		} else {
			setIcon(new ImageIcon());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		if (size.width != size.height) {
			int d = Math.min(size.width, size.height);
			setSize(d, d);
			setPreferredSize(new Dimension(d, d));
		}
	}
}
