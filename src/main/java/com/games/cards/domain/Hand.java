package com.games.cards.domain;

import com.games.cards.domain.exceptions.DuplicateCardException;
import com.games.cards.domain.exceptions.OutOfCardsException;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<Card> drawCards(int count) {
		try {
			List<Card> cards = new ArrayList<>(collectionOfCards.subList(0, count));
			collectionOfCards.subList(0, count).clear();
			return cards;
		} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
			throw new OutOfCardsException();
		}
	}

	public void mergeCards(List<Card> cards) {
		List<Card> cardsToBeMerged = cards.stream().
				filter(theCard -> !collectionOfCards.contains(theCard))
				.collect(Collectors.toList());

		if (cardsToBeMerged.size() != cards.size()) {
			throw new DuplicateCardException();
		}
		collectionOfCards.addAll(cardsToBeMerged);
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
