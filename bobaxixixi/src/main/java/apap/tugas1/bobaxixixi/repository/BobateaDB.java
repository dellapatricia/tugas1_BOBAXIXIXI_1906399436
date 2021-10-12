package apap.tugas1.bobaxixixi.repository;

import apap.tugas1.bobaxixixi.model.BobateaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository

public interface BobateaDB extends JpaRepository<BobateaModel,Long> {
}
