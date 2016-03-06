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
	private List<Card> cardsToBeMerged;

	public void setNonEmptyHand(Hand nonEmptyHand) {
		this.nonEmptyHand = nonEmptyHand;
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
		cardsToBeMerged = new ArrayList<>();
	}

	@Test
	public void sizeReflectsNumberOfCardsInHand() {
		assertThat(nonEmptyHand.size(), is(nonEmptyHand.listAllCards().size()));
	}

	@Test
	public void sizeIsZeroForEmptyHand() {
		assertThat(emptyHand.size(), is(0));
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
	public void shufflingChangesOrderOfCards() {
		ImmutableList<Card> orderOfCardsBeforeShuffling = nonEmptyHand.listAllCards();
		nonEmptyHand.shuffle();
		ImmutableList<Card> orderOfCardsAfterShuffling = nonEmptyHand.listAllCards();
		assertThat(orderOfCardsBeforeShuffling, is(not(orderOfCardsAfterShuffling)));
	}

	@Test
	public void shufflingEmptyHandHasNoEffect() {
		ImmutableList<Card> orderOfCardsBeforeShuffling = emptyHand.listAllCards();
		emptyHand.shuffle();
		ImmutableList<Card> orderOfCardsAfterShuffling = emptyHand.listAllCards();
		assertThat(orderOfCardsBeforeShuffling, is(orderOfCardsAfterShuffling));
	}

	@Test(expected = OutOfCardsException.class)
	public void cannotDrawCardsMoreThanSizeOfHand() {
		nonEmptyHand.drawCards(nonEmptyHand.size() + 1);
		fail();
	}

	@Test(expected = OutOfCardsException.class)
	public void cannotDrawAnyCardFromEmptyHand() {
		emptyHand.drawCards(1);
		fail();
	}

	@Test(expected = OutOfCardsException.class)
	public void cannotDrawLessThanZeroCardsFromHand() {
		nonEmptyHand.drawCards(-1);
		fail();
	}

	@Test
	public void drawingZeroCardsFromHandHasNoEffect() {
		int countOfCards = nonEmptyHand.size();
		List<Card> cardsDrawn = nonEmptyHand.drawCards(0);

		assertThat(cardsDrawn, is(empty()));
		assertThat(nonEmptyHand.size(), is(countOfCards));
	}

	@Test
	public void cardsDrawnFromHandAreRemoved() {
		nonEmptyHand.shuffle();
		List<Card> cardsDrawn = nonEmptyHand.drawCards(1);
		assertThat(nonEmptyHand.listAllCards(), not(hasItem(cardsDrawn.get(0))));
	}

	@Test
	public void allCardsCanBeDrawnFromHand() {
		int countOfCards = nonEmptyHand.size();
		List<Card> cardsDrawn = nonEmptyHand.drawCards(countOfCards);

		assertThat(nonEmptyHand.hasMoreCards(), is(false));
		assertThat(cardsDrawn, hasSize(countOfCards));
	}

	@Test
	public void cardsAreDrawnFromFrontOfHand() {
		nonEmptyHand.shuffle();
		List<Card> cardsAtFront = nonEmptyHand.listAllCards().subList(0, 3);
		List<Card> cardsDrawn = nonEmptyHand.drawCards(3);

		assertThat(cardsAtFront, is(cardsDrawn));
	}

	@Test
	public void mergingEmptyCardListToHandHasNoEffect() {
		int countOfCards = nonEmptyHand.size();

		nonEmptyHand.mergeCards(cardsToBeMerged);
		assertThat(nonEmptyHand.size(), is(countOfCards));
	}

	@Test
	public void cardsCanBeMergedToEmptyHand() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		cardsToBeMerged.add(theCard);

		emptyHand.mergeCards(cardsToBeMerged);
		assertThat(emptyHand.listAllCards(), hasItem(theCard));
	}

	@Test(expected = DuplicateCardException.class)
	public void cannotMergeCardsToHandIfThereAreAnyDuplicates() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		cardsToBeMerged.add(theCard);
		assertThat(nonEmptyHand.listAllCards(), hasItem(theCard));

		nonEmptyHand.mergeCards(cardsToBeMerged);
		fail();
	}

	@Test
	public void cardsMergedToHandAreAdded() {
		nonEmptyHand.shuffle();
		List<Card> cardsDrawn = nonEmptyHand.drawCards(3);
		int countOfCards = nonEmptyHand.size();

		nonEmptyHand.mergeCards(cardsDrawn);
		assertThat(nonEmptyHand.size(), is(countOfCards + 3));
		assertThat(nonEmptyHand.listAllCards(), hasItems(cardsDrawn.get(0), cardsDrawn.get(1), cardsDrawn.get(2)));
	}

	@Test
	public void cardsAreMergedToBackOfHand() {
		nonEmptyHand.shuffle();
		List<Card> cardsDrawn = nonEmptyHand.drawCards(3);

		nonEmptyHand.mergeCards(cardsDrawn);
		List<Card> orderOfCardsAfterMerge = nonEmptyHand.listAllCards();
		int countOfCards = orderOfCardsAfterMerge.size();
		assertThat(orderOfCardsAfterMerge.subList(countOfCards - 3, countOfCards), is(cardsDrawn));
	}
}
