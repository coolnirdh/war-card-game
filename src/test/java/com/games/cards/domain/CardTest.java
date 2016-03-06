package com.games.cards.domain;

import com.games.cards.domain.enumerations.Color;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nirdh on 04-03-2016.
 */
public class CardTest {

	@Test
	public void cardHasBothRankAndSuit() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		assertThat(theCard.getRank(), is(Rank.ACE));
		assertThat(theCard.getSuit(), is(Suit.SPADES));
		assertThat(theCard.toString(), is(theCard.getRank().getLabel().concat(theCard.getSuit().getSymbol())));
	}

	@Test
	public void colorOfCardIsBlackForSpades() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		assertThat(theCard.getColor(), is(Color.BLACK));
	}

	@Test
	public void colorOfCardIsRedForHearts() {
		Card theCard = new Card(Rank.ACE, Suit.HEARTS);
		assertThat(theCard.getColor(), is(Color.RED));
	}

	@Test
	public void colorOfCardIsRedForDiamonds() {
		Card theCard = new Card(Rank.ACE, Suit.DIAMONDS);
		assertThat(theCard.getColor(), is(Color.RED));
	}

	@Test
	public void colorOfCardIsBlackForClubs() {
		Card theCard = new Card(Rank.ACE, Suit.CLUBS);
		assertThat(theCard.getColor(), is(Color.BLACK));
	}

	@Test
	public void weightOfTwoOfClubsIs1() {
		Card theCard = new Card(Rank.TWO, Suit.CLUBS);
		assertThat(theCard.getWeight(), is(1));
	}

	@Test
	public void weightOfTwoOfDiamondsIs2() {
		Card theCard = new Card(Rank.TWO, Suit.DIAMONDS);
		assertThat(theCard.getWeight(), is(2));
	}

	@Test
	public void weightOfTwoOfHeartsIs3() {
		Card theCard = new Card(Rank.TWO, Suit.HEARTS);
		assertThat(theCard.getWeight(), is(3));
	}

	@Test
	public void weightOfTwoOfSpadesIs4() {
		Card theCard = new Card(Rank.TWO, Suit.SPADES);
		assertThat(theCard.getWeight(), is(4));
	}

	@Test
	public void weightOfAceOfClubsIs49() {
		Card theCard = new Card(Rank.ACE, Suit.CLUBS);
		assertThat(theCard.getWeight(), is(49));
	}

	@Test
	public void weightOfAceOfDiamondsIs50() {
		Card theCard = new Card(Rank.ACE, Suit.DIAMONDS);
		assertThat(theCard.getWeight(), is(50));
	}

	@Test
	public void weightOfAceOfHeartsIs51() {
		Card theCard = new Card(Rank.ACE, Suit.HEARTS);
		assertThat(theCard.getWeight(), is(51));
	}

	@Test
	public void weightOfAceOfSpadesIs52() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		assertThat(theCard.getWeight(), is(52));
	}

	@Test
	public void kingOfHeartsIsEqualToKingOfHearts() {
		Card theCard = new Card(Rank.KING, Suit.HEARTS);
		Card otherCard = new Card(Rank.KING, Suit.HEARTS);
		assertThat(theCard.compareTo(otherCard), is(0));
		assertThat(theCard.equals(otherCard), is(true));
	}

	@Test
	public void kingOfHeartsIsGreaterThanKingOfDiamonds() {
		Card theCard = new Card(Rank.KING, Suit.HEARTS);
		Card otherCard = new Card(Rank.KING, Suit.DIAMONDS);
		assertThat(theCard.compareTo(otherCard), is(greaterThan(0)));
		assertThat(theCard.equals(otherCard), is(false));
	}

	@Test
	public void kingOfHeartsIsLesserThanKingOfSpades() {
		Card theCard = new Card(Rank.KING, Suit.HEARTS);
		Card otherCard = new Card(Rank.KING, Suit.SPADES);
		assertThat(theCard.compareTo(otherCard), is(lessThan(0)));
		assertThat(theCard.equals(otherCard), is(false));
	}

	@Test
	public void kingOfHeartsIsGreaterThanQueenOfHearts() {
		Card theCard = new Card(Rank.KING, Suit.HEARTS);
		Card otherCard = new Card(Rank.QUEEN, Suit.HEARTS);
		assertThat(theCard.compareTo(otherCard), is(greaterThan(0)));
		assertThat(theCard.equals(otherCard), is(false));
	}

	@Test
	public void kingOfHeartsIsLesserThanAceOfHearts() {
		Card theCard = new Card(Rank.KING, Suit.HEARTS);
		Card otherCard = new Card(Rank.ACE, Suit.HEARTS);
		assertThat(theCard.compareTo(otherCard), is(lessThan(0)));
		assertThat(theCard.equals(otherCard), is(false));
	}

	@Test
	public void cardCannotBeEqualToNullCard() {
		Card theCard = new Card(Rank.ACE, Suit.SPADES);
		assertThat(theCard.equals(null), is(false));
	}
}
