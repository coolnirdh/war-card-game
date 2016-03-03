package com.games.cards.domain;

import com.games.cards.domain.enumerations.Color;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import com.games.cards.domain.exceptions.DuplicateCardException;
import com.games.cards.domain.exceptions.EmptyDeckException;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * Created by Nirdh on 03-03-2016.
 */
public class DeckTest {
	private Deck deck;

	@Before
	public void setUp() {
		deck = new Deck();
	}

	@Test
	public void thereAreExactly52CardsInTheDeck() {
		assertThat(deck.listAllCards(), hasSize(52));
	}

	@Test
	public void deckHasExactly26CardsOfEachColor() {
		for (Color theColor : Color.values()) {
			assertThat(deck.listCardsByColor(theColor), hasSize(26));
		}
	}

	@Test
	public void deckHasExactly13CardsOfEachSuit() {
		for (Suit theSuit : Suit.values()) {
			assertThat(deck.listCardsBySuit(theSuit), hasSize(13));
		}
	}

	@Test
	public void deckHasExactly4CardsOfEachRank() {
		for (Rank theRank : Rank.values()) {
			assertThat(deck.listCardsByRank(theRank), hasSize(4));
		}
	}

	@Test
	public void shufflingOfDeckIsRandom() {
		ImmutableList<Card> orderOfCardsBeforeShuffling = deck.listAllCards();
		deck.shuffle();
		ImmutableList<Card> orderOfCardsAfterShuffling = deck.listAllCards();
		assertThat(orderOfCardsBeforeShuffling, is(not(orderOfCardsAfterShuffling)));
	}

	@Test
	public void cardDrawnFromDeckDoesNotExistAfterwards() {
		deck.shuffle();
		Card theCard = deck.drawCard();
		assertThat(deck.listAllCards(), not(hasItem(theCard)));
		assertThat(deck.size(), is(51));
	}

	@Test
	public void cardReturnedToDeckExistsAfterwards() {
		deck.shuffle();
		Card theCard = deck.drawCard();
		assertThat(deck.listAllCards(), not(hasItem(theCard)));

		deck.returnCard(theCard);
		assertThat(deck.listAllCards(), hasItem(theCard));
	}

	@Test
	public void allCardsCanBeDrawn() {
		deck.shuffle();
		while (deck.hasMoreCards()) {
			deck.drawCard();
		}
		assertThat(deck.listAllCards(), hasSize(0));
	}

	@Test
	public void allCardsCanBeReturned() {
		List<Card> cardsDrawn = new ArrayList<Card>();
		deck.shuffle();
		while (deck.hasMoreCards()) {
			cardsDrawn.add(deck.drawCard());
		}
		assertThat(deck.listAllCards(), hasSize(0));

		for (Card theCard : cardsDrawn) {
			deck.returnCard(theCard);
		}
		assertThat(deck.listAllCards(), hasSize(52));
	}

	@Test(expected = EmptyDeckException.class)
	public void cardCannotBeDrawnWhenDeckIsEmpty() {
		deck.shuffle();
		while (deck.hasMoreCards()) {
			deck.drawCard();
		}
		assertThat(deck.listAllCards(), hasSize(0));

		deck.drawCard();
		fail();
	}

	@Test(expected = DuplicateCardException.class)
	public void cardCannotBeAddedToDeckWhenItAlreadyExists() {
		deck.returnCard(new Card(Rank.ACE, Suit.SPADES));
		fail();
	}
}
