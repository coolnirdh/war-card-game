package com.games.cards.domain;

import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Created by Nirdh on 04-03-2016.
 */
public class CardTest {
	@Test
	public void spadesIsHigherThanHearts() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		Card anotherCard = new Card(Rank.ACE, Suit.HEARTS);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}

	@Test
	public void heartsIsHigherThanDiamonds() {
		Card theCard = new Card(Rank.ACE, Suit.HEARTS);
		Card anotherCard = new Card(Rank.ACE, Suit.DIAMONDS);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}

	@Test
	public void diamondsIsHigherThanClubs() {
		Card theCard = new Card(Rank.ACE, Suit.DIAMONDS);
		Card anotherCard = new Card(Rank.ACE, Suit.CLUBS);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}

	@Test
	public void aceIsHigherThanKing() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		Card anotherCard = new Card(Rank.KING, Suit.SPADES);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}

	@Test
	public void kingIsHigherThanQueen() {
		Card theCard = new Card(Rank.KING, Suit.SPADES);
		Card anotherCard = new Card(Rank.QUEEN, Suit.SPADES);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}

	@Test
	public void QueenIsHigherThanJack() {
		Card theCard = new Card(Rank.QUEEN, Suit.SPADES);
		Card anotherCard = new Card(Rank.JACK, Suit.SPADES);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}

	@Test
	public void jackIsHigherThanTen() {
		Card theCard = new Card(Rank.JACK, Suit.SPADES);
		Card anotherCard = new Card(Rank.TEN, Suit.SPADES);
		assertThat(theCard.compareTo(anotherCard), is(greaterThan(0)));
	}
}
