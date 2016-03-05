package com.games.cards.domain;

import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import com.games.cards.domain.exceptions.DuplicateCardException;
import com.games.cards.domain.exceptions.OutOfCardsException;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * Created by Nirdh on 05-03-2016.
 */
public class HandTest {
	private Hand nonEmptyHand;
	private Hand emptyHand;

	public void setNonEmptyHand(Hand nonEmptyHand) {
		this.nonEmptyHand = nonEmptyHand;
	}

	public void setEmptyHand(Hand emptyHand) {
		this.emptyHand = emptyHand;
	}

	@Before
	public void setUp() {
		List<Card> someCards = new ArrayList<>(Arrays.asList(new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.QUEEN, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.CLUBS)
		));
		nonEmptyHand = new Hand(someCards);
		emptyHand = new Hand();
	}

	@Test
	public void sizeIsAlwaysZeroForEmptyHand() {
		assertThat(emptyHand.size(), is(0));
	}

	@Test
	public void sizeReflectsNumberOfCardsForNonEmptyHand() {
		assertThat(nonEmptyHand.size(), is(nonEmptyHand.listAllCards().size()));
	}

	@Test
	public void emptyHandDoesNotHaveMoreCards() {
		assertThat(emptyHand.hasMoreCards(), is(false));
	}

	@Test
	public void nonEmptyHandHasMoreCards() {
		assertThat(nonEmptyHand.hasMoreCards(), is(true));
	}

	@Test
	public void shufflingDoesNothingForEmptyHand() {
		emptyHand.shuffle();
	}

	@Test
	public void shufflingOfNonEmptyHandChangesOrderOfCards() {
		ImmutableList<Card> orderOfCardsBeforeShuffling = nonEmptyHand.listAllCards();
		nonEmptyHand.shuffle();
		ImmutableList<Card> orderOfCardsAfterShuffling = nonEmptyHand.listAllCards();
		assertThat(orderOfCardsBeforeShuffling, is(not(orderOfCardsAfterShuffling)));
	}

	@Test(expected = OutOfCardsException.class)
	public void cardCanNeverBeDrawnFromEmptyHand() {
		emptyHand.drawCard();
		fail();
	}

	@Test
	public void cardDrawnFromNonEmptyHandIsRemoved() {
		nonEmptyHand.shuffle();
		Card theCard = nonEmptyHand.drawCard();
		assertThat(nonEmptyHand.listAllCards(), not(hasItem(theCard)));
	}

	@Test
	public void allCardsCanBeDrawnFromNonEmptyHand() {
		nonEmptyHand.shuffle();
		while (nonEmptyHand.hasMoreCards()) {
			nonEmptyHand.drawCard();
		}
		assertThat(nonEmptyHand.listAllCards(), hasSize(0));
	}

	@Test
	public void cardCanAlwaysBeReturnedToEmptyHand() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		emptyHand.returnCard(theCard);
		assertThat(emptyHand.listAllCards(), hasItem(theCard));
	}

	@Test(expected = DuplicateCardException.class)
	public void cardCannotBeReturnedToNonEmptyHandWhenItAlreadyExists() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		assertThat(nonEmptyHand.listAllCards(), hasItem(theCard));
		nonEmptyHand.returnCard(theCard);
		fail();
	}

	@Test
	public void cardReturnedToNonEmptyHandIsAddedWhenItDoesNotExist() {
		nonEmptyHand.shuffle();
		Card theCard = nonEmptyHand.drawCard();
		assertThat(nonEmptyHand.listAllCards(), not(hasItem(theCard)));

		nonEmptyHand.returnCard(theCard);
		assertThat(nonEmptyHand.listAllCards(), hasItem(theCard));
	}
}
