package com.iteye.wwwcomy.llk;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class CardHolder {
	private static final int size = 10;
	private Card[][] cards;

	private Card selectedCard;

	private IReachable reachable;

	public CardHolder() {
		init();
	}

	public void init() {
		if (cards == null)
			cards = new Card[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j = j + 2) {
				int value = new Random().nextInt(10);
				cards[i][j] = new Card(i, j, value);
				cards[i][j + 1] = new Card(i, j + 1, value);
			}
		}
		reachable = new DefaultReachableStrategy();
	}

	public Card[][] getCards() {
		return cards;
	}

	public void setCards(Card[][] cards) {
		this.cards = cards;
	}

	public void draw(Graphics g) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Card card = cards[i][j];
				if (card != null)
					card.draw(g);
			}
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
		Card card = cards[stepX][stepY];
		return card;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Card selectedCard) {
		this.selectedCard = selectedCard;
	}

	public boolean judgeSame(Card selectedCard, Card card) {
		return reachable.reach(cards, selectedCard, card);
	}

	public void removeCard(Card card) {
		int x = card.getX();
		int y = card.getY();
		cards[x][y] = null;
	}

	public IReachable getReachable() {
		return reachable;
	}

	public void setReachable(IReachable reachable) {
		this.reachable = reachable;
	}
}
