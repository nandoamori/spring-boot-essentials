package com.nandoamori.springboot2essencials.service;

import com.nandoamori.springboot2essencials.domain.Anime;
import com.nandoamori.springboot2essencials.exception.BadRequestException;
import com.nandoamori.springboot2essencials.mapper.AnimeMapper;
import com.nandoamori.springboot2essencials.repository.AnimeRepository;
import com.nandoamori.springboot2essencials.requests.AnimePostRequestBody;
import com.nandoamori.springboot2essencials.requests.AnimePutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }
    public List<Anime>  listAllNonPageable() {
        return animeRepository.findAll();
    }
    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }
    public Anime findByIdOrThrowBadRequestException(Long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }
    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }


}
