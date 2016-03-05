package com.games.cards.war;

import com.games.cards.domain.Card;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Created by Nirdh on 04-03-2016.
 */
public class CardComparatorTest {
	private CardComparator cardComparator;

	@Before
	public void setUp() {
		cardComparator = new CardComparator();
	}

	@Test
	public void suitsAreInsignificant() {
		Card aceOfSpades = new Card(Rank.ACE, Suit.SPADES);
		Card aceOfHearts = new Card(Rank.ACE, Suit.HEARTS);
		Card aceOfDiamonds = new Card(Rank.ACE, Suit.DIAMONDS);
		Card aceOfClubs = new Card(Rank.ACE, Suit.CLUBS);

		assertThat(cardComparator.compare(aceOfSpades, aceOfHearts), is(0));
		assertThat(cardComparator.compare(aceOfHearts, aceOfDiamonds), is(0));
		assertThat(cardComparator.compare(aceOfDiamonds, aceOfClubs), is(0));
	}

	@Test
	public void aceIsHigherThanKing() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		Card anotherCard = new Card(Rank.KING, Suit.SPADES);
		assertThat(cardComparator.compare(theCard, anotherCard), is(greaterThan(0)));
	}

	@Test
	public void kingIsHigherThanQueen() {
		Card theCard = new Card(Rank.KING, Suit.SPADES);
		Card anotherCard = new Card(Rank.QUEEN, Suit.SPADES);
		assertThat(cardComparator.compare(theCard, anotherCard), is(greaterThan(0)));
	}

	@Test
	public void QueenIsHigherThanJack() {
		Card theCard = new Card(Rank.QUEEN, Suit.SPADES);
		Card anotherCard = new Card(Rank.JACK, Suit.SPADES);
		assertThat(cardComparator.compare(theCard, anotherCard), is(greaterThan(0)));
	}

	@Test
	public void jackIsHigherThanTen() {
		Card theCard = new Card(Rank.JACK, Suit.SPADES);
		Card anotherCard = new Card(Rank.TEN, Suit.SPADES);
		assertThat(cardComparator.compare(theCard, anotherCard), is(greaterThan(0)));
	}
}
