package com.games.cards.domain.enumerations;

/**
 * Created by Nirdh on 04-03-2016.
 */
public enum Suit {
	SPADES(Color.BLACK, "\u2660", 4),
	HEARTS(Color.RED, "\u2661", 3),
	DIAMONDS(Color.RED, "\u2662", 2),
	CLUBS(Color.BLACK, "\u2663", 1);

	private final Color color;
	private final String symbol;
	private final int weight;

	Suit(Color theColor, String theSymbol, int theWeight) {
		this.color = theColor;
		this.symbol = theSymbol;
		this.weight = theWeight;
	}

	public Color getColor() {
		return this.color;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public int getWeight() {
		return this.weight;
	}
}
