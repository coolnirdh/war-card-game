package com.games.cards.domain;

import com.games.cards.domain.enumerations.Color;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Created by Nirdh on 03-03-2016.
 */
public class DeckTest extends HandTest {
	private Deck deck;

	@Before
	public void setUp() {
		deck = new Deck();
		this.setEmptyHand(new Hand());
		this.setNonEmptyHand(deck);
	}

	@Test
	public void deckHasExactly52CardsInTotal() {
		assertThat(deck.listAllCards(), hasSize(52));
	}

	@Test
	public void deckHasExactly26CardsOfEachColor() {
		for (Color theColor : Color.values()) {
			assertThat(deck.listAllCards().parallelStream().filter(card -> card.getColor() == theColor).count(), is(26L));
		}
	}

	@Test
	public void deckHasExactly13CardsOfEachSuit() {
		for (Suit theSuit : Suit.values()) {
			assertThat(deck.listAllCards().parallelStream().filter(card -> card.getSuit() == theSuit).count(), is(13L));
		}
	}

	@Test
	public void deckHasExactly4CardsOfEachRank() {
		for (Rank theRank : Rank.values()) {
			assertThat(deck.listAllCards().parallelStream().filter(card -> card.getRank() == theRank).count(), is(4L));
		}
	}
}
