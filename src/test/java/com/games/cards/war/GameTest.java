package com.games.cards.war;

import com.games.cards.domain.Card;
import com.games.cards.domain.Deck;
import com.games.cards.domain.enumerations.Rank;
import com.games.cards.domain.enumerations.Suit;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nirdh on 08-03-2016.
 */
public class GameTest {
	private final String someName = "Player_";

	private Game game;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;

	@Before
	public void setUp() {
		game = new Game(new ArrayList<>(Arrays.asList(new Player(someName + 0))));
		player1 = new Player(someName + 1);
		player2 = new Player(someName + 2);
		player3 = new Player(someName + 3);
		player4 = new Player(someName + 4);
	}

	@Test(expected = RuntimeException.class)
	public void gameCannotBeInitiatedWithZeroPlayers() {
		game = new Game(new ArrayList<>());
	}

	@Test
	public void cardsAreShuffledBeforeBeingDealt() {
		List<Card> defaultOrderOfCardsInDeck = new Deck().listAllCards();
		game = new Game(new ArrayList<>(Arrays.asList(player1)));
		List<Card> orderOfCardsInPlayersHand = player1.showAllCardsInHand();
		assertThat(orderOfCardsInPlayersHand, is(not(defaultOrderOfCardsInDeck)));
	}

	@Test
	public void dealingCardsTo2PlayersMakesThemHave26CardsEach() {
		game = new Game(new ArrayList<>(Arrays.asList(player1, player2)));
		assertThat(player1.showAllCardsInHand(), hasSize(26));
		assertThat(player2.showAllCardsInHand(), hasSize(26));
	}

	@Test
	public void dealingCardsTo3PlayersMakesThemHave17CardsEach() {
		game = new Game(new ArrayList<>(Arrays.asList(player1, player2, player3)));
		assertThat(player1.showAllCardsInHand(), hasSize(17));
		assertThat(player2.showAllCardsInHand(), hasSize(17));
		assertThat(player3.showAllCardsInHand(), hasSize(17));
	}

	@Test
	public void dealingCardsTo4PlayersMakesThemHave13CardsEach() {
		game = new Game(new ArrayList<>(Arrays.asList(player1, player2, player3, player4)));
		assertThat(player1.showAllCardsInHand(), hasSize(13));
		assertThat(player2.showAllCardsInHand(), hasSize(13));
		assertThat(player3.showAllCardsInHand(), hasSize(13));
		assertThat(player4.showAllCardsInHand(), hasSize(13));
	}

	@Test
	public void removingBustedPlayersHasNoEffectIfAllPlayersAtLeastHaveOneCard() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
		game = new Game(players);

		game.removeBustedPlayers();
		assertThat(players, hasSize(4));
	}

	@Test
	public void removingBustedPlayersRemovesAllPlayersHavingNoCards() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);
		players.addAll(new ArrayList<>(Arrays.asList(player3, player4)));

		game.removeBustedPlayers();
		assertThat(players, hasSize(2));
	}

	@Test
	public void playingBattleRemovesExactlyOneCardFromEachPlayersHand() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);

		game.playBattleAndIdentifyWinners();
		assertThat(player1.showAllCardsInHand(), hasSize(25));
		assertThat(player2.showAllCardsInHand(), hasSize(25));
	}

	@Test
	public void bustedPlayersAreRemovedBeforePlayingBattle() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);
		players.add(player3);

		game.playBattleAndIdentifyWinners();
		assertThat(players, not(hasItem(player3)));
	}

	@Test
	public void playingWarRemovesNumberOfFaceDownCardsPlusOneFromEachPlayersHandHavingSufficientCards() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);

		game.playWarAndIdentifyWinners();
		assertThat(player1.showAllCardsInHand(), hasSize(26 - (Game.NUMBER_OF_FACE_DOWN_CARDS_IN_WAR + 1)));
		assertThat(player2.showAllCardsInHand(), hasSize(26 - (Game.NUMBER_OF_FACE_DOWN_CARDS_IN_WAR + 1)));
	}

	@Test
	public void playingWarEmptiesHandForAllPlayersWithInsufficientCards() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.QUEEN, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.CLUBS),
				new Card(Rank.TEN, Suit.SPADES),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.EIGHT, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.CLUBS)
		));
		player1.collectCards(cards.subList(0, 1));
		player2.collectCards(cards.subList(1, 8));

		game.setPlayers(players);
		game.playWarAndIdentifyWinners();

		assertThat(player1.showAllCardsInHand(), is(empty()));
		assertThat(player2.showAllCardsInHand(), hasSize(7 - (Game.NUMBER_OF_FACE_DOWN_CARDS_IN_WAR + 1)));
	}

	@Test
	public void bustedPlayersAreRemovedBeforePlayingWar() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);
		players.add(player3);

		game.playWarAndIdentifyWinners();
		assertThat(players, not(hasItem(player3)));
	}

	@Test
	public void competingCardsGotFromAllPlayersContainAllIndividualPlayersCompetingCards() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);
		game.playBattleAndIdentifyWinners();
		List<Card> competingCards = game.getCompetingCardsFromAllPlayers();
		assertThat(competingCards, containsInAnyOrder(player1.getCompetingCard(), player2.getCompetingCard()));
	}

	@Test
	public void cardsAreComparedByRank() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.KING, Suit.SPADES),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.QUEEN, Suit.SPADES)
		));
		assertThat(game.getHighestCard(cards), is(new Card(Rank.ACE, Suit.SPADES)));
	}

	@Test
	public void cardsWithSameRankAreComparedByPositionNotSuit() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.KING, Suit.SPADES),
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.CLUBS)
		));
		Collections.shuffle(cards);
		assertThat(game.getHighestCard(cards), is(cards.get(0)));
	}

	@Test
	public void multiplePlayersWinBattleWhenTheyHaveCompetingCardsWithRankSameAsHighestCard() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.ACE, Suit.CLUBS)
		));
		player1.collectCards(cards.subList(0, 1));
		player2.collectCards(cards.subList(1, 2));
		player3.collectCards(cards.subList(2, 3));
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));
		game.setPlayers(players);

		assertThat(game.playBattleAndIdentifyWinners(), hasItems(player2, player3));
	}

	@Test
	public void multiplePlayersWinWarWhenTheyHaveCompetingCardsWithRankSameAsHighestCard() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.ACE, Suit.CLUBS)
		));
		player1.collectCards(cards.subList(0, 1));
		player2.collectCards(cards.subList(1, 2));
		player3.collectCards(cards.subList(2, 3));
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));
		game.setPlayers(players);
		assertThat(game.playWarAndIdentifyWinners(), hasItems(player2, player3));
	}

	@Test
	public void singlePlayerWinsBattleWhenOthersHaveCompetingCardsWithRankLessThanHighestCard() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.CLUBS)
		));
		player1.collectCards(cards.subList(0, 1));
		player2.collectCards(cards.subList(1, 2));
		player3.collectCards(cards.subList(2, 3));
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));
		game.setPlayers(players);
		assertThat(game.playBattleAndIdentifyWinners(), hasSize(1));
	}

	@Test
	public void singlePlayerWinsWarWhenOthersHaveCompetingCardsWithRankLessThanHighestCard() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.CLUBS)
		));
		player1.collectCards(cards.subList(0, 1));
		player2.collectCards(cards.subList(1, 2));
		player3.collectCards(cards.subList(2, 3));
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));
		game.setPlayers(players);
		assertThat(game.playWarAndIdentifyWinners(), hasSize(1));
	}

	@Test
	public void warIsDeclaredWhenThereAreMultipleWinners() {
		List<Player> winners = new ArrayList<>(Arrays.asList(player1, player2));
		assertThat(game.isWarDeclaredBetween(winners), is(true));
	}

	@Test
	public void warIsNotDeclaredWhenThereIsSingleWinners() {
		List<Player> winners = new ArrayList<>(Arrays.asList(player1));
		assertThat(game.isWarDeclaredBetween(winners), is(false));
	}

	@Test
	public void cardsAtStakeAreCollectedFromAllPlayersIncludingBustedOnes() {
		List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
		game = new Game(players);

		players.addAll(new ArrayList<>(Arrays.asList(player3, player4)));
		player3.collectCards(new ArrayList<>(Arrays.asList(new Card(Rank.ACE, Suit.SPADES))));
		player3.playBattle();
		game.setPlayers(players);
		game.playBattleAndIdentifyWinners();

		assertThat(game.collectCardsAtStake(), hasSize(3));
	}

	@Test
	public void rewardingAWinnerAddsAllCardsAtStakeToTheirHand() {
		List<Card> cards = new ArrayList<>(Arrays.asList(
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.KING, Suit.HEARTS),
				new Card(Rank.QUEEN, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.CLUBS)
		));
		player1.collectCards(cards.subList(0, 2));
		player2.collectCards(cards.subList(2, 4));
		game.setPlayers(new ArrayList<>(Arrays.asList(player1, player2)));
		game.playBattleAndIdentifyWinners();
		game.rewardWinner(player1);
		assertThat(player1.showAllCardsInHand(), hasSize(3));
	}

	@Test
	public void fairGameMustProduceOneWinner() {
		game = new Game(new ArrayList<>(Arrays.asList(player1, player2, player3, player4)));
		Player winner = game.play();
		assertThat(winner, isA(Player.class));
	}
}
