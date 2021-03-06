package org.example.API.repository;

import org.example.API.model.Content;
import org.example.API.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    public boolean existsByContentId(Long contentId);

    public boolean existsByPersonIdAndContentId(Long personId, Long contentId);

    @Query(value="SELECT * FROM rating WHERE person_id = :personId AND content_id = :contentId ;" , nativeQuery = true)
    public List<Rating> selectRatingByPersonIdAndContentId(@Param("personId") Long personId, @Param("contentId") Long contentId);

    public Long countByContentId(Long contentId);

}
