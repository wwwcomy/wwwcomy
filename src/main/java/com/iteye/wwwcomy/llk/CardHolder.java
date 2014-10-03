package com.iteye.wwwcomy.llk;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardHolder {
	private List<Card> cards;

	private Card selectedCard;

	public CardHolder() {
		init();
	}

	public void init() {
		if (cards == null)
			cards = new ArrayList<Card>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j = j + 2) {
				int value = new Random().nextInt(10);
				cards.add(new Card(i, j, value));
				cards.add(new Card(i, j + 1, value));
			}
		}

	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void draw(Graphics g) {
		for (Card card : cards) {
			card.draw(g);
		}
		if (selectedCard != null) {
			Color c = g.getColor();
			g.setColor(Color.green);
			selectedCard.draw(g);
			g.setColor(c);
		}
	}

	public Card getCardByLocation(int x, int y) {
		int stepX = (x / Card.width) - 1;
		int stepY = (y / Card.height) - 1;
		for (Card card : cards) {
			if (card.getX() == stepX && card.getY() == stepY)
				return card;
		}
		return null;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Card selectedCard) {
		this.selectedCard = selectedCard;
	}

	public boolean judgeSame(Card selectedCard, Card card) {
		if (selectedCard.getValue() == card.getValue())
			return true;
		return false;
	}
	
	public boolean removeCard(Card card){
		return cards.remove(card);
	}
}
