package com.games.cards.war;

import com.games.cards.domain.Card;

import java.util.Comparator;

/**
 * Created by Nirdh on 04-03-2016.
 */
public class CardComparator implements Comparator<Card> {
	public int compare(Card theCard, Card otherCard) {
		return theCard.getRank().getValue() - otherCard.getRank().getValue();
	}
}
