package com.submarine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.util.StringUtils;

import com.submarine.exception.InvalidSubmarineActionsException;
import com.submarine.exception.InvalidSubmarineNameException;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Submarine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	private Long id;
	@Column
	@XmlElement
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@XmlTransient
	private List<Position> route = new ArrayList<>();

	Submarine() {
	}

	public Submarine(Long id, String name) {
		super();
		this.id = id;
		this.setName(name);
		this.route.add(Position.newInstance());
	}

	public Submarine(String name) {
		this(null, name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new InvalidSubmarineNameException("Invalid name");
		}
		this.name = name;
	}

	public List<Position> getRoute() {
		return route;
	}

	@XmlElement(name = "lastPosition")
	public Position getLastPosition() {
		int p = getRoute().size() - 1;
		return this.route.get(p);
	}

	private void executeAction(Command command) {
		Position position = command.execute(getLastPosition());
		route.add(position);
	}

	public void executeActions(String actions) {
		actions = validateActions(actions);
		for (int i = 0; i < actions.length(); i++) {
			String action = actions.substring(i, i + 1);
			Command command = Command.valueOf(action);
			executeAction(command);
		}
	}

	private static Pattern pattern = Pattern.compile("[RLUDM]*");

	private String validateActions(String actions) {
		if (actions == null || "".equals(actions)) {
			throw new InvalidSubmarineActionsException("actions is empty");
		}
		actions = actions.toUpperCase();
		Matcher matcher = pattern.matcher(actions);
		if (!matcher.matches()) {
			throw new InvalidSubmarineActionsException("actions contain wrong characters");
		}
		return actions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Submarine other = (Submarine) obj;
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
		return "Submarine [id=" + id + ", name=" + name + ", lastPosition=" + getLastPosition() + "]";
	}

}

enum Command {

	R(SubmarineAction.right), //
	L(SubmarineAction.left), //
	U(SubmarineAction.up), //
	D(SubmarineAction.down), //
	M(SubmarineAction.move);

	private SubmarineAction submarineAction;

	private Command(SubmarineAction submarineAction) {
		this.submarineAction = submarineAction;
	}

	public Position execute(Position position) {
		return submarineAction.execute(position);
	}

}

interface SubmarineAction {
	SubmarineAction right = new Right();
	SubmarineAction left = new Left();
	SubmarineAction up = new Up();
	SubmarineAction down = new Down();
	SubmarineAction move = new Move();

	Position execute(Position position);
}

class Right implements SubmarineAction {

	@Override
	public Position execute(Position position) {
		return position.right();
	}

}

class Left implements SubmarineAction {

	@Override
	public Position execute(Position position) {
		return position.left();
	}

}

class Up implements SubmarineAction {

	@Override
	public Position execute(Position position) {
		return position.up();
	}

}

class Down implements SubmarineAction {

	@Override
	public Position execute(Position position) {
		return position.down();
	}

}

class Move implements SubmarineAction {

	@Override
	public Position execute(Position position) {
		return position.move();
	}

}
