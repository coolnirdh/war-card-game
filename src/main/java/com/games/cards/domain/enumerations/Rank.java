package com.games.cards.domain.enumerations;

/**
 * Created by Nirdh on 04-03-2016.
 */
public enum Rank {
	TWO("2", 2),
	THREE("3", 3),
	FOUR("4", 4),
	FIVE("5", 5),
	SIX("6", 6),
	SEVEN("7", 7),
	EIGHT("8", 8),
	NINE("9", 9),
	TEN("10", 10),
	JACK("J", 11),
	QUEEN("Q", 12),
	KING("K", 13),
	ACE("A", 14);

	private final String label;
	private final int value;

	Rank(String theLabel, int theValue) {
		this.label = theLabel;
		this.value = theValue;
	}

	public String getLabel() {
		return this.label;
	}

	public int getValue() {
		return this.value;
	}
}
