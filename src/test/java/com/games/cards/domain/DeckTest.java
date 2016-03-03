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
	private Deck standardDeck;
	private Deck miniDeck;

	@Before
	public void setUp() {
		standardDeck = new Deck();
		miniDeck = new Deck(ImmutableList.of(
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.HEARTS),
				new Card(Rank.JACK, Suit.DIAMONDS)
		));
	}

	@Test
	public void thereAreExactly52CardsInTheDeck() {
		assertThat(standardDeck.listAllCards(), hasSize(52));
	}

	@Test
	public void deckHasExactly26CardsOfEachColor() {
		for (Color theColor : Color.values()) {
			assertThat(standardDeck.listCardsByColor(theColor), hasSize(26));
		}
	}

	@Test
	public void deckHasExactly13CardsOfEachSuit() {
		for (Suit theSuit : Suit.values()) {
			assertThat(standardDeck.listCardsBySuit(theSuit), hasSize(13));
		}
	}

	@Test
	public void deckHasExactly4CardsOfEachRank() {
		for (Rank theRank : Rank.values()) {
			assertThat(standardDeck.listCardsByRank(theRank), hasSize(4));
		}
	}

	@Test
	public void shufflingOfDeckIsRandom() {
		ImmutableList<Card> orderOfCardsBeforeShuffling = miniDeck.listAllCards();
		miniDeck.shuffle();
		ImmutableList<Card> orderOfCardsAfterShuffling = miniDeck.listAllCards();
		assertThat(orderOfCardsBeforeShuffling, is(not(orderOfCardsAfterShuffling)));
	}

	@Test
	public void cardDrawnFromDeckDoesNotExistAfterwards() {
		standardDeck.shuffle();
		Card theCard = standardDeck.drawCard();
		assertThat(standardDeck.listAllCards(), not(hasItem(theCard)));
		assertThat(standardDeck.size(), is(51));
	}

	@Test
	public void cardReturnedToDeckExistsAfterwards() {
		standardDeck.shuffle();
		Card theCard = standardDeck.drawCard();
		assertThat(standardDeck.listAllCards(), not(hasItem(theCard)));

		standardDeck.returnCard(theCard);
		assertThat(standardDeck.listAllCards(), hasItem(theCard));
	}

	@Test
	public void allCardsCanBeDrawn() {
		standardDeck.shuffle();
		while (standardDeck.hasMoreCards()) {
			standardDeck.drawCard();
		}
		assertThat(standardDeck.listAllCards(), hasSize(0));
	}

	@Test
	public void allCardsCanBeReturned() {
		List<Card> cardsDrawn = new ArrayList<Card>();
		standardDeck.shuffle();
		while (standardDeck.hasMoreCards()) {
			cardsDrawn.add(standardDeck.drawCard());
		}
		assertThat(standardDeck.listAllCards(), hasSize(0));

		for (Card theCard : cardsDrawn) {
			standardDeck.returnCard(theCard);
		}
		assertThat(standardDeck.listAllCards(), hasSize(52));
	}

	@Test(expected = EmptyDeckException.class)
	public void cardCannotBeDrawnWhenDeckIsEmpty() {
		miniDeck.shuffle();
		while (miniDeck.hasMoreCards()) {
			miniDeck.drawCard();
		}
		assertThat(miniDeck.listAllCards(), hasSize(0));

		miniDeck.drawCard();
		fail();
	}

	@Test(expected = DuplicateCardException.class)
	public void cardCannotBeAddedToDeckWhenItAlreadyExists() {
		miniDeck.returnCard(new Card(Rank.ACE, Suit.SPADES));
		fail();
	}
}
