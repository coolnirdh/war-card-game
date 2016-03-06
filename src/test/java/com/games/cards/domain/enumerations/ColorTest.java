package com.games.cards.domain.enumerations;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Nirdh on 06-03-2016.
 */
public class ColorTest {

	@Test
	public void thereAre2Colors() {
		assertThat(Color.values(), is(arrayWithSize(2)));
	}

	@Test
	public void colorsAreBlackAndRed() {
		assertThat(Color.values(), is(arrayContaining(Color.BLACK, Color.RED)));
	}
}
