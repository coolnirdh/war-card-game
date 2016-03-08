package com.games.cards.war;

import com.games.cards.domain.Card;
import com.games.cards.domain.Deck;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by Nirdh on 08-03-2016.
 */
public class Game {
	public static final int NUMBER_OF_FACE_DOWN_CARDS_IN_WAR = 3;

	private final Logger logger = LoggerFactory.getLogger(Game.class);

	private List<Player> players;
	private ImmutableList<Player> allPlayers;

	public Game(List<Player> thePlayers) {
		this.setPlayers(thePlayers);
		this.dealCards();
	}

	protected void setPlayers(List<Player> thePlayers) {
		if (thePlayers.isEmpty()) {
			throw new RuntimeException("Need to have at least one player");
		}
		this.players = thePlayers;
		logger.info("Starting game with {} players", players.size());
		allPlayers = ImmutableList.copyOf(players);
	}

	protected void dealCards() {
		Deck deck = new Deck();
		deck.shuffle();
		logger.info("Dealing cards");
		while (deck.size() >= players.size()) {
			players.stream().forEach(player -> player.collectCards(deck.drawCards(1)));
		}
	}

	public Player play() {
		while (players.size() > 1) {
			playNextRound();
		}
		return players.get(0);
	}

	protected void playNextRound() {
		logger.info("Cards in hand: {}", players);
		List<Player> winners = this.playBattleAndIdentifyWinners();
		while (this.isWarDeclaredBetween(winners)) {
			logger.warn("WAR Declared!");
			winners = this.playWarAndIdentifyWinners();
		}
		this.rewardWinner(winners.get(0));
	}

	protected List<Player> playBattleAndIdentifyWinners() {
		this.removeBustedPlayers();
		players.stream().forEach(Player::playBattle);
		return identifyWinners();
	}

	protected boolean isWarDeclaredBetween(List<Player> winners) {
		return winners.size() > 1;
	}

	protected List<Player> playWarAndIdentifyWinners() {
		this.removeBustedPlayers();
		players.stream().forEach(player -> player.playWar(NUMBER_OF_FACE_DOWN_CARDS_IN_WAR));
		return identifyWinners();
	}

	protected void rewardWinner(Player player) {
		List<Card> cardsAtStake = this.collectCardsAtStake();
		player.collectCards(cardsAtStake);
	}

	protected void removeBustedPlayers() {
		List<Player> bustedPlayers = players.stream().filter(Player::isBusted).collect(Collectors.toList());
		players.removeAll(bustedPlayers);
		if (bustedPlayers.size() > 0) {
			logger.warn("Busted: {}", bustedPlayers);
		}
	}

	protected List<Player> identifyWinners() {
		List<Card> competingCards = this.getCompetingCardsFromAllPlayers();
		logger.debug("Competing cards: " + competingCards);
		Card highestCard;
		try {
			highestCard = this.getHighestCard(competingCards);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("It's a draw!");
		}
		return this.identifyWinnersBasedOn(highestCard);
	}

	protected List<Card> collectCardsAtStake() {
		List<Card> cardsAtStake = new ArrayList<>();
		for (Player player : allPlayers) {
			cardsAtStake.addAll(player.getCardsAtStake());
			player.getCardsAtStake().clear();
		}
		return cardsAtStake;
	}

	protected List<Card> getCompetingCardsFromAllPlayers() {
		return players.stream().map(Player::getCompetingCard).collect(Collectors.toList());
	}

	protected Card getHighestCard(List<Card> cards) {
		return cards.stream().max(new CardComparator()).get();
	}

	protected List<Player> identifyWinnersBasedOn(Card highestCard) {
		return players.stream()
				.filter(player -> player.getCompetingCard().getRank() == highestCard.getRank())
				.collect(Collectors.toList());
	}
}
