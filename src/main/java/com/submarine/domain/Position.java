package com.submarine.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "X", "Y", "Z", "direction" })
public class Position {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long id;
	@Column
	@XmlElement
	private int X = 0;
	@Column
	@XmlElement
	private int Y = 0;
	@Column
	@XmlElement
	private int Z = 0;
	@Column
	@Enumerated(EnumType.STRING)
	@XmlElement
	private Direction direction = Direction.NORTH;

	private Position() {
	}

	public static Position newInstance() {
		return new Position();
	}

	public static Position newInstance(int x, int y, int z, Direction direction) {
		Position position = new Position();
		position.X = x;
		position.Y = y;
		position.Z = z;
		if (position.Z > 0) {
			// premissa de não lançar exceção ao tentar levantar eixo Z acima
			// da superficie
			position.Z = 0;
		}
		position.direction = direction;
		return position;
	}

	public Position move() {
		return direction.forward(this);
	}

	public Position right() {
		return newInstance(X, Y, Z, this.direction.turnRight());
	}

	public Position left() {
		return newInstance(X, Y, Z, this.direction.turnLeft());
	}

	public Position up() {
		return newInstance(X, Y, Z + 1, direction);
	}

	public Position down() {
		return newInstance(X, Y, Z - 1, direction);
	}

	public Position incY() {
		return newInstance(X, Y + 1, Z, direction);
	}

	public Position decY() {
		return newInstance(X, Y - 1, Z, direction);
	}

	public Position incX() {
		return newInstance(X + 1, Y, Z, direction);
	}

	public Position decX() {
		return newInstance(X - 1, Y, Z, direction);
	}

	public Long getId() {
		return id;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public int getZ() {
		return Z;
	}

	public Direction getDirection() {
		return direction;
	}

	public String readPosition() {
		return String.format("%s %s %s %s", this.X, this.Y, this.Z, this.direction);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + X;
		result = prime * result + Y;
		result = prime * result + Z;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (X != other.X) {
			return false;
		}
		if (Y != other.Y) {
			return false;
		}
		if (Z != other.Z) {
			return false;
		}
		if (direction != other.direction) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", X=" + X + ", Y=" + Y + ", Z=" + Z + ", direction=" + direction + "]";
	}

}

enum Direction {

	NORTH(Forward.goNorth), SOUTH(Forward.goSouth), EAST(Forward.goEast), WEST(Forward.goWest);

	private Forward forward;
	private Turn turn;

	Direction(Forward forward) {
		this.forward = forward;
		this.turn = (Turn) forward;
	}

	public Position forward(Position position) {
		return forward.forward(position);
	}

	public Direction turnRight() {
		return turn.right();
	}

	public Direction turnLeft() {
		return turn.left();
	}

}

interface Forward {

	Forward goNorth = new GoNorth();
	Forward goSouth = new GoSouth();
	Forward goEast = new GoEst();
	Forward goWest = new GoWest();

	Position forward(Position position);

}

interface Turn {

	Direction right();

	Direction left();

}

class GoNorth implements Forward, Turn {

	@Override
	public Position forward(Position position) {
		return position.incY();
	}

	@Override
	public Direction right() {
		return Direction.EAST;
	}

	@Override
	public Direction left() {
		return Direction.WEST;
	}

}

class GoSouth implements Forward, Turn {

	@Override
	public Position forward(Position position) {
		return position.decY();
	}

	@Override
	public Direction right() {
		return Direction.WEST;
	}

	@Override
	public Direction left() {
		return Direction.EAST;
	}

}

class GoEst implements Forward, Turn {

	@Override
	public Position forward(Position position) {
		return position.incX();
	}

	@Override
	public Direction right() {
		return Direction.SOUTH;
	}

	@Override
	public Direction left() {
		return Direction.NORTH;
	}

}

class GoWest implements Forward, Turn {

	@Override
	public Position forward(Position position) {
		return position.decX();
	}

	@Override
	public Direction right() {
		return Direction.NORTH;
	}

	@Override
	public Direction left() {
		return Direction.SOUTH;
	}

}