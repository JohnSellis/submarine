package com.submarine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.submarine.domain.Submarine;

@Repository
public interface SubmarineRepository extends JpaRepository<Submarine, Long> {

}
