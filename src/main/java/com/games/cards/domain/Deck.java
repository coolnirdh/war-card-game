package com.games.cards.domain;

import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirdh on 04-03-2016.
 */
public class Deck extends Hand {
	public Deck() {
		List<Card> theCollectionOfCards = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				theCollectionOfCards.add(new Card(rank, suit));
			}
		}
		this.setCollectionOfCards(theCollectionOfCards);
	}
}
