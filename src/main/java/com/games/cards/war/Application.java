package com.games.cards.war;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirdh on 08-03-2016.
 */
public class Application {
	public static void main(String args[]) throws IOException {
		List<Player> players = new ArrayList<>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter number of players: ");
		int numberOfPlayers = Integer.parseInt(in.readLine());

		for (int i = 1; i <= numberOfPlayers; i++) {
			System.out.print("Enter the name of Player " + i + ": ");
			players.add(new Player(in.readLine()));
		}

		Game game = new Game(players);
		game.play();
	}
}
