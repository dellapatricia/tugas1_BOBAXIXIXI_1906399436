package apap.tugas1.bobaxixixi.repository;

import apap.tugas1.bobaxixixi.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StoreDB extends JpaRepository<StoreModel,Long> {
//    Optional<StoreModel> findById(Long id);
}
