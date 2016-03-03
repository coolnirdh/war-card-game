package com.games.cards.domain;

import com.games.cards.domain.enumerations.Color;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import com.games.cards.domain.exceptions.DuplicateCardException;
import com.games.cards.domain.exceptions.EmptyDeckException;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nirdh on 04-03-2016.
 */
public class Deck {
	private final List<Card> collectionOfCards = new ArrayList<Card>();

	public Deck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				collectionOfCards.add(new Card(rank, suit));
			}
		}
	}

	public Deck(List<Card> theCollectionOfCards) {
		collectionOfCards.clear();
		collectionOfCards.addAll(theCollectionOfCards);
	}

	public void shuffle() {
		Collections.shuffle(collectionOfCards);
	}

	public Card drawCard() {
		try {
			return collectionOfCards.remove(0);
		} catch (IndexOutOfBoundsException e) {
			throw new EmptyDeckException();
		}
	}

	public void returnCard(Card theCard) {
		if (collectionOfCards.contains(theCard)) {
			throw new DuplicateCardException();
		}
		collectionOfCards.add(theCard);
	}

	public ImmutableList<Card> listCardsByColor(Color theColor) {
		ImmutableList.Builder<Card> selectedCards = new ImmutableList.Builder<Card>();
		for (Card theCard : collectionOfCards) {
			if (theColor.equals(theCard.getColor())) {
				selectedCards.add(theCard);
			}
		}
		return selectedCards.build();
	}

	public ImmutableList<Card> listCardsBySuit(Suit theSuit) {
		ImmutableList.Builder<Card> selectedCards = new ImmutableList.Builder<Card>();
		for (Card theCard : collectionOfCards) {
			if (theSuit.equals(theCard.getSuit())) {
				selectedCards.add(theCard);
			}
		}
		return selectedCards.build();
	}

	public ImmutableList<Card> listCardsByRank(Rank theRank) {
		ImmutableList.Builder<Card> selectedCards = new ImmutableList.Builder<Card>();
		for (Card theCard : collectionOfCards) {
			if (theRank.equals(theCard.getRank())) {
				selectedCards.add(theCard);
			}
		}
		return selectedCards.build();
	}

	public ImmutableList<Card> listAllCards() {
		ImmutableList.Builder<Card> selectedCards = new ImmutableList.Builder<Card>();
		selectedCards.addAll(collectionOfCards);
		return selectedCards.build();
	}

	public boolean hasMoreCards() {
		return !collectionOfCards.isEmpty();
	}

	public int size() {
		return collectionOfCards.size();
	}
}
