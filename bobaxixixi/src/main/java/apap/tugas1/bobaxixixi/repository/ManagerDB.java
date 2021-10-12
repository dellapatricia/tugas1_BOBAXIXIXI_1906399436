package apap.tugas1.bobaxixixi.repository;

import apap.tugas1.bobaxixixi.model.ManagerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ManagerDB extends JpaRepository<ManagerModel,Long> {
}
