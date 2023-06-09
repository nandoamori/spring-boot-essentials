package com.nandoamori.springboot2essencials.controller;

import com.nandoamori.springboot2essencials.domain.Anime;
import com.nandoamori.springboot2essencials.requests.AnimePostRequestBody;
import com.nandoamori.springboot2essencials.requests.AnimePutRequestBody;
import com.nandoamori.springboot2essencials.service.AnimeService;
import com.nandoamori.springboot2essencials.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;
    private final DateUtil dateUtil;
    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll(pageable));
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAllNonPageable());
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam(required = false) String name){
        return ResponseEntity.ok(animeService.findByName(name));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id){
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
