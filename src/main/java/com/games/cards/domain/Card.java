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
		return this.rank;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public Color getColor() {
		return this.suit.getColor();
	}

	public int compareTo(Card otherCard) {
		int suitComparison = suit.compareTo(otherCard.getSuit());
		if (suitComparison == 0) {
			return rank.compareTo(otherCard.getRank());
		}
		return suitComparison;
	}

	@Override
	public String toString() {
		return rank.getLabel().concat(suit.getSymbol());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Card otherCard = (Card) obj;
		return Objects.equals(this.rank, otherCard.rank) && Objects.equals(this.suit, otherCard.suit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.rank, this.suit);
	}
}
