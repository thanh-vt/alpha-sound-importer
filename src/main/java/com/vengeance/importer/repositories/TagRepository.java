package com.vengeance.importer.repositories;

import com.vengeance.importer.constant.ModelStatus;
import com.vengeance.importer.model.entity.Tag;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByIdAndName(Long id, String name);
    //    @Query(value = "SELECT * FROM tag WHERE BINARY title=:title", nativeQuery = true)
    Optional<Tag> findByName(String name);

    List<Tag> findAllByNameInAndStatus(Set<String> tagNames, ModelStatus modelStatus);

    Page<Tag> findAllByOrderByUpdateTimeDescCreateTimeDesc(Pageable pageable);

    Page<Tag> findAllByNameContaining(String name, Pageable pageable);
}
