package com.submarine.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.submarine.exception.InvalidSubmarineActionsException;

public class SubmarineTest {

	@Test
	public void whenStarted_doActions_validPositions() {
		Submarine submarine;
		submarine = new Submarine(null, "YELLOW");
		submarine.executeActions("LMRDDMMUU");
		assertEquals(submarine.getLastPosition().getX(), -1);
		assertEquals(submarine.getLastPosition().getY(), 2);
		assertEquals(submarine.getLastPosition().getZ(), 0);
		assertEquals(submarine.getLastPosition().getDirection(), Direction.NORTH);
	}

	@Test
	public void whenStarted_doActions_validPositionsAndRouteSize() {
		Submarine submarine;
		submarine = new Submarine(null, "YELLOW");
		submarine.executeActions("RMMLMMMDDLL");
		assertEquals(submarine.getLastPosition().getX(), 2);
		assertEquals(submarine.getLastPosition().getY(), 3);
		assertEquals(submarine.getLastPosition().getZ(), -2);
		assertEquals(submarine.getLastPosition().getDirection(), Direction.SOUTH);
		assertEquals(submarine.getRoute().size(), 12);
	}

	@Test(expected = InvalidSubmarineActionsException.class)
	public void whenStarted_doActions_withWrongAction() {
		Submarine submarine;
		submarine = new Submarine(null, "YELLOW");
		submarine.executeActions("RMMLMMMDDLLK");
	}

	@Test(expected = InvalidSubmarineActionsException.class)
	public void whenStarted_doActions_withNullActions() {
		Submarine submarine;
		submarine = new Submarine(null, "YELLOW");
		submarine.executeActions(null);
	}

	@Test(expected = InvalidSubmarineActionsException.class)
	public void whenStarted_doActions_withEmptyActions() {
		Submarine submarine;
		submarine = new Submarine(null, "YELLOW");
		submarine.executeActions("");
	}

}
