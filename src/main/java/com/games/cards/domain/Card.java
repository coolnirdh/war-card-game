package com.games.cards.domain;

import com.games.cards.domain.enumerations.Color;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;

import java.util.Objects;

/**
 * Created by Nirdh on 03-03-2016.
 */
public class Card implements Comparable<Card> {
	private final Rank rank;
	private final Suit suit;

	public Card(Rank theRank, Suit theSuit) {
		this.rank = theRank;
		this.suit = theSuit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public Color getColor() {
		return suit.getColor();
	}

	public int getWeight() {
		return (rank.getWeight() - 1) * Suit.values().length + suit.getWeight();
	}

	public int compareTo(Card otherCard) {
		return this.getWeight() - otherCard.getWeight();
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, suit);
	}

	@Override
	public boolean equals(Object obj) {
		try {
			final Card otherCard = (Card) obj;
			return Objects.equals(this.rank, otherCard.rank) && Objects.equals(this.suit, otherCard.suit);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		return rank.getLabel().concat(suit.getSymbol());
	}
}
