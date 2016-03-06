package com.games.cards.domain.enumerations;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nirdh on 06-03-2016.
 */
public class RankTest {

	@Test
	public void thereAre13Ranks() {
		assertThat(Rank.values(), is(arrayWithSize(13)));
	}

	@Test
	public void ranksAreAceTwoThreeFourFiveSixSevenEightNineTenJackQueenAndKing() {
		assertThat(Rank.values(), is(arrayContaining(
				Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN,
				Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING)));
	}

	@Test
	public void aceIsRepresentedByA() {
		assertThat(Rank.ACE.getLabel(), is("A"));
	}

	@Test
	public void twoIsRepresentedBy2() {
		assertThat(Rank.TWO.getLabel(), is("2"));
	}

	@Test
	public void threeIsRepresentedBy3() {
		assertThat(Rank.THREE.getLabel(), is("3"));
	}

	@Test
	public void fourIsRepresentedBy4() {
		assertThat(Rank.FOUR.getLabel(), is("4"));
	}

	@Test
	public void fiveIsRepresentedBy5() {
		assertThat(Rank.FIVE.getLabel(), is("5"));
	}

	@Test
	public void sixIsRepresentedBy6() {
		assertThat(Rank.SIX.getLabel(), is("6"));
	}

	@Test
	public void sevenIsRepresentedBy7() {
		assertThat(Rank.SEVEN.getLabel(), is("7"));
	}

	@Test
	public void eightIsRepresentedBy8() {
		assertThat(Rank.EIGHT.getLabel(), is("8"));
	}

	@Test
	public void nineIsRepresentedBy9() {
		assertThat(Rank.NINE.getLabel(), is("9"));
	}

	@Test
	public void tenIsRepresentedBy10() {
		assertThat(Rank.TEN.getLabel(), is("10"));
	}

	@Test
	public void jackIsRepresentedByJ() {
		assertThat(Rank.JACK.getLabel(), is("J"));
	}

	@Test
	public void queenIsRepresentedByQ() {
		assertThat(Rank.QUEEN.getLabel(), is("Q"));
	}

	@Test
	public void kingIsRepresentedByK() {
		assertThat(Rank.KING.getLabel(), is("K"));
	}

	@Test
	public void minimumWeightIs1() {
		assertThat(Arrays.asList(Rank.values()).stream().mapToInt(Rank::getWeight).min().getAsInt(), is(1));
	}

	@Test
	public void maximumWeightIsSameAsCountOfRanks() {
		assertThat(Arrays.asList(Rank.values()).stream().mapToInt(Rank::getWeight).max().getAsInt(), is(Rank.values().length));
	}

	@Test
	public void aceHasHigherWeightThanKing() {
		assertThat(Rank.ACE.getWeight() - Rank.KING.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void kingHasHigherWeightThanQueen() {
		assertThat(Rank.KING.getWeight() - Rank.QUEEN.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void queenHasHigherWeightThanJack() {
		assertThat(Rank.QUEEN.getWeight() - Rank.JACK.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void jackHasHigherWeightThanTen() {
		assertThat(Rank.JACK.getWeight() - Rank.TEN.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void tenHasHigherWeightThanNine() {
		assertThat(Rank.TEN.getWeight() - Rank.NINE.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void nineHasHigherWeightThanEight() {
		assertThat(Rank.NINE.getWeight() - Rank.EIGHT.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void eightHasHigherWeightThanSeven() {
		assertThat(Rank.EIGHT.getWeight() - Rank.SEVEN.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void sevenHasHigherWeightThanSix() {
		assertThat(Rank.SEVEN.getWeight() - Rank.SIX.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void sixHasHigherWeightThanFive() {
		assertThat(Rank.SIX.getWeight() - Rank.FIVE.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void fiveHasHigherWeightThanFour() {
		assertThat(Rank.FIVE.getWeight() - Rank.FOUR.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void fourHasHigherWeightThanThree() {
		assertThat(Rank.FOUR.getWeight() - Rank.THREE.getWeight(), is(greaterThan(0)));
	}

	@Test
	public void threeHasHigherWeightThanTwo() {
		assertThat(Rank.THREE.getWeight() - Rank.TWO.getWeight(), is(greaterThan(0)));
	}
}
