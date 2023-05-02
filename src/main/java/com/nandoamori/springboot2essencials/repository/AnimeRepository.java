package com.nandoamori.springboot2essencials.repository;

import com.nandoamori.springboot2essencials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByName(String name);
}
