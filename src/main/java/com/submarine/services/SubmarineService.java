package com.submarine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.submarine.domain.Submarine;
import com.submarine.exception.SubmarineNotFoundException;
import com.submarine.repository.SubmarineRepository;

@Service
public class SubmarineService {

	@Autowired
	private SubmarineRepository submarineRepository;

	public Submarine createSubmarine(String name) {
		Submarine submarine = submarineRepository.saveAndFlush(new Submarine(name));
		return submarine;
	}

	public List<Submarine> readSubmarine() {
		return submarineRepository.findAll();
	}

	public Submarine readSubmarine(Long id) {
		Submarine submarine = submarineRepository.findOne(id);
		if (submarine == null) {
			throw new SubmarineNotFoundException("Submarine not found");
		}
		return submarine;
	}

	public Submarine updateSubmarine(Long id, String name) {
		Submarine submarine = readSubmarine(id);
		submarine.setName(name);
		submarineRepository.saveAndFlush(submarine);
		return submarine;
	}

	public void deleteSubmarine(Long id) {
		Submarine submarine = readSubmarine(id);
		submarineRepository.delete(submarine);
	}

	public Submarine performActions(Long id, String actions) {
		Submarine submarine = readSubmarine(id);
		submarine.executeActions(actions);
		submarineRepository.saveAndFlush(submarine);
		return submarine;
	}

}
