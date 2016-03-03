package com.games.cards.domain.enumerations;

/**
 * Created by Nirdh on 04-03-2016.
 */
public enum Suit {
	CLUBS(Color.BLACK, "\u2663"),
	DIAMONDS(Color.RED, "\u2662"),
	HEARTS(Color.RED, "\u2661"),
	SPADES(Color.BLACK, "\u2660");

	private final Color color;
	private final String symbol;

	Suit(Color theColor, String theSymbol) {
		this.color = theColor;
		this.symbol = theSymbol;
	}

	public Color getColor() {
		return this.color;
	}

	public String getSymbol() {
		return this.symbol;
	}
}
