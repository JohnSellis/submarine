package com.submarine.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PositionTest {

	@Test
	public void whenNorth_turnRight_goEast() {
		Position position = Position.newInstance(0, 0, 0, Direction.NORTH).right();
		assertEquals(Direction.EAST, position.getDirection());
	}

	@Test
	public void whenNorth_turnLeft_goWest() {
		Position position = Position.newInstance(0, 0, 0, Direction.NORTH).left();
		assertEquals(Direction.WEST, position.getDirection());
	}

	@Test
	public void whenEast_turnRight_goSouth() {
		Position position = Position.newInstance(0, 0, 0, Direction.EAST).right();
		assertEquals(Direction.SOUTH, position.getDirection());
	}

	@Test
	public void whenEast_turnLeft_goNorth() {
		Position position = Position.newInstance(0, 0, 0, Direction.EAST).left();
		assertEquals(Direction.NORTH, position.getDirection());
	}

	@Test
	public void whenSouth_turnRight_goWest() {
		Position position = Position.newInstance(0, 0, 0, Direction.SOUTH).right();
		assertEquals(Direction.WEST, position.getDirection());
	}

	@Test
	public void whenSouth_turnLeft_goRight() {
		Position position = Position.newInstance(0, 0, 0, Direction.SOUTH).left();
		assertEquals(Direction.EAST, position.getDirection());
	}

	@Test
	public void whenWest_turnRight_goNorth() {
		Position position = Position.newInstance(0, 0, 0, Direction.WEST).right();
		assertEquals(Direction.NORTH, position.getDirection());
	}

	@Test
	public void whenWest_turnLeft_goSouth() {
		Position position = Position.newInstance(0, 0, 0, Direction.WEST).left();
		assertEquals(Direction.SOUTH, position.getDirection());
	}

	@Test
	public void whenZIsZero_goUp_keepZero() {
		Position position = Position.newInstance(0, 0, 0, Direction.NORTH).up();
		assertEquals(position.getZ(), 0);
	}

	@Test
	public void whenZIsNegative_goUp_justRaise() {
		Position position = Position.newInstance(0, 0, -2, Direction.NORTH).up();
		assertEquals(position.getZ(), -1);
	}

	@Test
	public void whenZIsNegative_goDown_justDive() {
		Position position = Position.newInstance(0, 0, -2, Direction.NORTH).down();
		assertEquals(position.getZ(), -3);
	}

	@Test
	public void whenPointToNorth_move_incY() {
		Position position = Position.newInstance(0, 4, 0, Direction.NORTH).move();
		assertEquals(position.getY(), 5);
		position = Position.newInstance(0, -3, 0, Direction.NORTH).move();
		assertEquals(position.getY(), -2);
	}

	@Test
	public void whenPointToSouth_move_decY() {
		Position position = Position.newInstance(0, 7, 0, Direction.SOUTH).move();
		assertEquals(position.getY(), 6);
		position = Position.newInstance(0, -6, 0, Direction.SOUTH).move();
		assertEquals(position.getY(), -7);
	}

	@Test
	public void whenPointToEast_move_incX() {
		Position position = Position.newInstance(4, 0, 0, Direction.EAST).move();
		assertEquals(position.getX(), 5);
		position = Position.newInstance(-3, 0, 0, Direction.EAST).move();
		assertEquals(position.getX(), -2);
	}

	@Test
	public void whenPointToWest_move_decX() {
		Position position = Position.newInstance(7, 0, 0, Direction.WEST).move();
		assertEquals(position.getX(), 6);
		position = Position.newInstance(-6, 0, 0, Direction.WEST).move();
		assertEquals(position.getX(), -7);
	}

}
