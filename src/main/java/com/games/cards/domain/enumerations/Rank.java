package com.games.cards.domain.enumerations;

/**
 * Created by Nirdh on 04-03-2016.
 */
public enum Rank {
	ACE("A", 13),
	TWO("2", 1),
	THREE("3", 2),
	FOUR("4", 3),
	FIVE("5", 4),
	SIX("6", 5),
	SEVEN("7", 6),
	EIGHT("8", 7),
	NINE("9", 8),
	TEN("10", 9),
	JACK("J", 10),
	QUEEN("Q", 11),
	KING("K", 12);

	private final String label;
	private final int weight;

	Rank(String theLabel, int theValue) {
		this.label = theLabel;
		this.weight = theValue;
	}

	public String getLabel() {
		return this.label;
	}

	public int getWeight() {
		return this.weight;
	}
}
