package com.vengeance.importer.repositories;

import com.vengeance.importer.model.entity.Song;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface SongRepository extends JpaRepository<Song, Long> {

    @NonNull
    Page<Song> findAll(@NonNull Pageable pageable);

    @EntityGraph(value = "song-artist", type = EntityGraphType.FETCH)
    Page<Song> findAllBySync(Integer i, Pageable pageable);

    @NonNull
    Optional<Song> findById(@NonNull @Param("id") Long id);

}
