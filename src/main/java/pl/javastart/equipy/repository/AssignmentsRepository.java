package pl.javastart.equipy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.javastart.equipy.model.Assignments;

import java.util.Optional;

@Repository
public interface AssignmentsRepository extends JpaRepository<Assignments, Long> {

    Optional<Assignments> findByAsset_IdAndEndIsNull(Long assetId);
}
