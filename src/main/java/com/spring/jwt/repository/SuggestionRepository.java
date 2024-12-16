package com.spring.jwt.repository;

import com.spring.jwt.model.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<SuggestionEntity,Long> {
    Boolean existsByText(String text);
    Optional<SuggestionEntity> findByText(String text);
    @Query(value="SELECT * FROM Suggestion s WHERE s.user_id = :userId", nativeQuery = true)
    List<SuggestionEntity> findAllByUserId(@Param("userId") Long id);
    @Query(value="SELECT * FROM Suggestion s WHERE is_validated = :value", nativeQuery = true)
    List<SuggestionEntity> findAllByIsValidated(boolean value);
}
