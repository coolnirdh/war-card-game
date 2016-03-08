package com.games.cards.war;

import com.games.cards.domain.Card;
import com.games.cards.domain.Hand;
import com.games.cards.domain.exceptions.OutOfCardsException;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirdh on 07-03-2016.
 */
public class Player {
	private final Logger logger = LoggerFactory.getLogger(Player.class);

	private final String name;
	private final Hand hand;
	private final List<Card> cardsAtStake;

	public Player(String theName) {
		this.name = theName;
		this.hand = new Hand();
		this.cardsAtStake = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public ImmutableList<Card> showAllCardsInHand() {
		return hand.listAllCards();
	}

	public List<Card> getCardsAtStake() {
		return cardsAtStake;
	}

	public Card getCompetingCard() {
		try {
			return cardsAtStake.get(cardsAtStake.size() - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new OutOfCardsException();
		}
	}

	public void collectCards(List<Card> cardsCollected) {
		hand.mergeCards(cardsCollected);
	}

	public void playBattle() {
		cardsAtStake.clear();
		cardsAtStake.addAll(hand.drawCards(1));
	}

	public void playWar(int numberOfFaceDownCards) {
		try {
			cardsAtStake.addAll(hand.drawCards(numberOfFaceDownCards + 1));
		} catch (OutOfCardsException e) {
			logger.warn("Last chance for {}, ran out of cards during WAR!", name);
			cardsAtStake.addAll(hand.drawCards(hand.size()));
		}
	}

	public boolean isBusted() {
		return !hand.hasMoreCards();
	}

	@Override
	public String toString() {
		return name + ": " + hand.size();
	}
}
