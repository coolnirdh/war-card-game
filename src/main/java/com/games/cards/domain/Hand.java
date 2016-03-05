package com.games.cards.domain;

import com.games.cards.domain.exceptions.DuplicateCardException;
import com.games.cards.domain.exceptions.OutOfCardsException;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nirdh on 05-03-2016.
 */
public class Hand {
	private List<Card> collectionOfCards;

	public Hand() {
		this(new ArrayList<>());
	}

	public Hand(List<Card> theCollectionOfCards) {
		this.setCollectionOfCards(theCollectionOfCards);
	}

	public void setCollectionOfCards(List<Card> collectionOfCards) {
		this.collectionOfCards = collectionOfCards;
	}

	public void shuffle() {
		Collections.shuffle(collectionOfCards);
	}

	public Card drawCard() {
		try {
			return collectionOfCards.remove(0);
		} catch (IndexOutOfBoundsException e) {
			throw new OutOfCardsException();
		}
	}

	public boolean removeCard(Card theCard) {
		try {
			return collectionOfCards.remove(theCard);
		} catch (Exception e) {
			return false;
		}
	}

	public void returnCard(Card theCard) {
		if (collectionOfCards.contains(theCard)) {
			throw new DuplicateCardException();
		}
		collectionOfCards.add(theCard);
	}

	public boolean hasMoreCards() {
		return !collectionOfCards.isEmpty();
	}

	public int size() {
		return collectionOfCards.size();
	}

	public ImmutableList<Card> listAllCards() {
		return ImmutableList.copyOf(collectionOfCards);
	}
}
