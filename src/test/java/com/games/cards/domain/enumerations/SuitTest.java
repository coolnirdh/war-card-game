package com.games.cards.domain.enumerations;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nirdh on 06-03-2016.
 */
public class SuitTest {

	@Test
	public void thereAre4Suits() {
		assertThat(Suit.values(), is(arrayWithSize(4)));
	}

	@Test
	public void suitsAreSpadesHeartsDiamondsAndClubs() {
		assertThat(Suit.values(), is(arrayContaining(Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS)));
	}

	@Test
	public void spadesAreBlack() {
		assertThat(Suit.SPADES.getColor(), is(Color.BLACK));
	}

	@Test
	public void heartsAreRed() {
		assertThat(Suit.HEARTS.getColor(), is(Color.RED));
	}

	@Test
	public void diamondsAreRed() {
		assertThat(Suit.DIAMONDS.getColor(), is(Color.RED));
	}

	@Test
	public void clubsAreBlack() {
		assertThat(Suit.CLUBS.getColor(), is(Color.BLACK));
	}

	@Test
	public void spadesAreRepresentedByBlackSpadesSymbol() {
		assertThat(Suit.SPADES.getSymbol(), is("\u2660"));
	}

	@Test
	public void heartsAreRepresentedByWhiteHeartsSymbol() {
		assertThat(Suit.HEARTS.getSymbol(), is("\u2661"));
	}

	@Test
	public void diamondsAreRepresentedByWhiteDiamondsSymbol() {
		assertThat(Suit.DIAMONDS.getSymbol(), is("\u2662"));
	}

	@Test
	public void clubsAreRepresentedByBlackClubsSymbol() {
		assertThat(Suit.CLUBS.getSymbol(), is("\u2663"));
	}

	@Test
	public void minimumWeightIs1() {
		assertThat(Arrays.asList(Suit.values()).stream().mapToInt(Suit::getWeight).min().getAsInt(), is(1));
	}

	@Test
	public void maximumWeightIsSameAsCountOfSuits() {
		assertThat(Arrays.asList(Suit.values()).stream().mapToInt(Suit::getWeight).max().getAsInt(), is(Suit.values().length));
	}

	@Test
	public void spadesHaveHigherWeightThanHearts() {
		assertThat(Suit.SPADES.getWeight() - Suit.HEARTS.getWeight(), greaterThan(0));
	}

	@Test
	public void heartsHaveHigherWeightThanDiamonds() {
		assertThat(Suit.HEARTS.getWeight() - Suit.DIAMONDS.getWeight(), greaterThan(0));
	}

	@Test
	public void diamondsHaveHigherWeightThanClubs() {
		assertThat(Suit.DIAMONDS.getWeight() - Suit.CLUBS.getWeight(), greaterThan(0));
	}
}
