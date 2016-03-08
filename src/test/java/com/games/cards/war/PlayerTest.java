package com.games.cards.war;

import com.games.cards.domain.Card;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import com.games.cards.domain.exceptions.DuplicateCardException;
import com.games.cards.domain.exceptions.OutOfCardsException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * Created by Nirdh on 06-03-2016.
 */
public class PlayerTest {
	private final String someName = "John Doe";

	private List<Card> atLeast4Cards;
	private Player player;

	@Before
	public void setUp() {
		atLeast4Cards = Arrays.asList(
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.QUEEN, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.CLUBS)
		);
		player = new Player(someName);
	}

	@Test
	public void playerHasAName() {
		assertThat(player.getName(), is(someName));
	}

	@Test
	public void playerHasNoCardsInHandInitially() {
		assertThat(player.showAllCardsInHand(), is(empty()));
	}

	@Test
	public void playerHasNoCardsAtStakeInitially() {
		assertThat(player.getCardsAtStake(), is(empty()));
	}

	@Test
	public void cardsCollectedAreAddedToHand() {
		player.collectCards(atLeast4Cards);
		assertThat(player.showAllCardsInHand(), is(atLeast4Cards));
	}

	@Test
	public void orderOfCardsCollectedIsPreserved() {
		player.collectCards(atLeast4Cards.subList(0, 2));
		player.collectCards(atLeast4Cards.subList(2, 3));
		player.collectCards(atLeast4Cards.subList(3, 4));
		assertThat(player.showAllCardsInHand(), is(atLeast4Cards));
	}

	@Test(expected = DuplicateCardException.class)
	public void playerCannotCollectCardsIfAnyOfThemIsAlreadyInHand() {
		player.collectCards(atLeast4Cards.subList(0, 2));
		player.collectCards(atLeast4Cards.subList(1, 4));
		fail();
	}

	@Test
	public void playingBattleRemovesFirstCardFromHand() {
		player.collectCards(atLeast4Cards);
		player.playBattle();
		assertThat(player.showAllCardsInHand(), is(atLeast4Cards.subList(1, 4)));
	}

	@Test
	public void playingBattleIntroducesOneCardAtStake() {
		player.collectCards(atLeast4Cards);
		player.playBattle();
		assertThat(player.getCardsAtStake(), hasSize(1));
	}

	@Test
	public void cardsPreviouslyAtStakeAreRemovedUponPlayingABattle() {
		player.collectCards(atLeast4Cards);

		player.playBattle();
		assertThat(player.getCardsAtStake(), hasSize(1));
		assertThat(player.getCardsAtStake(), is(atLeast4Cards.subList(0, 1)));

		player.playBattle();
		assertThat(player.getCardsAtStake(), hasSize(1));
		assertThat(player.getCardsAtStake(), is(atLeast4Cards.subList(1, 2)));
	}

	@Test(expected = OutOfCardsException.class)
	public void playerCannotPlayBattleWithEmptyHand() {
		player.playBattle();
		fail();
	}

	@Test
	public void playingWarWithFewerCardsThanNumberOfFaceDownCardsNeededEmptiesHand() {
		player.collectCards(atLeast4Cards);
		player.playWar(5);
		assertThat(player.showAllCardsInHand(), hasSize(0));
	}

	@Test
	public void playingWarWithAsManyCardsAsNumberOfFaceDownCardsNeededEmptiesHand() {
		player.collectCards(atLeast4Cards);
		player.playWar(4);
		assertThat(player.showAllCardsInHand(), hasSize(0));
	}

	@Test
	public void playingWarWithInsufficientCardsIncreasesCardsAtStakeBySizeOfHand() {
		player.collectCards(atLeast4Cards);
		player.playBattle();
		assertThat(player.getCardsAtStake(), hasSize(1));
		int handSize = player.showAllCardsInHand().size();

		player.playWar(5);
		assertThat(player.getCardsAtStake(), hasSize(1 + handSize));
	}

	@Test
	public void playingWarWithSufficientCardsReducesNumberOfCardsInHandByNumberOfFaceDownCardsPlusOne() {
		player.collectCards(atLeast4Cards);
		player.playWar(1);
		assertThat(player.showAllCardsInHand(), hasSize(2));
	}

	@Test
	public void playingWarWithSufficientCardsIncreasesCardsAtStakeByNumberOfFaceDownCardsPlusOne() {
		player.collectCards(atLeast4Cards);
		player.playBattle();
		assertThat(player.getCardsAtStake(), hasSize(1));

		player.playWar(1);
		assertThat(player.getCardsAtStake(), hasSize(3));
	}

	@Test
	public void playingWarWithZeroCardsHasNoEffect() {
		player.playWar(1);
		assertThat(player.getCardsAtStake(), hasSize(0));
		player.playWar(3);
		assertThat(player.getCardsAtStake(), hasSize(0));
	}

	@Test
	public void competingCardIsLatestCardAddedToCardsAtStake() {
		player.collectCards(atLeast4Cards);
		player.playBattle();
		player.playWar(1);
		assertThat(player.getCompetingCard(), is(atLeast4Cards.get(2)));
	}

	@Test(expected = OutOfCardsException.class)
	public void cannotGetCompetingCardWhenThereAreNoCardsAtStake() {
		player.getCompetingCard();
		fail();
	}

	@Test
	public void playerIsBustedIfThereAreNoMoreCardsInHand() {
		assertThat(player.isBusted(), is(true));
	}

	@Test
	public void playerIfNotBustedIfThereAreMoreCardsInHand() {
		player.collectCards(atLeast4Cards);
		assertThat(player.isBusted(), is(false));
	}
}
