package apap.tugas1.bobaxixixi.service;
import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.repository.BobateaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BobateaServiceImpl implements BobateaService{
    @Autowired
    BobateaDB bobateaDB;

    @Override
    public void addBobatea(BobateaModel bobatea) {
        bobateaDB.save(bobatea);
    }

    @Override
    public void updateBobatea(BobateaModel bobatea) {
        bobateaDB.save(bobatea);
    }

    @Override
    public void deleteBobatea(BobateaModel bobatea) {
        bobateaDB.delete(bobatea);
    }

    @Override
    public List<BobateaModel> getBobateaList() {
        return bobateaDB.findAll();
    }

    @Override
    public BobateaModel getBobateaById(Long idBobatea){
        return bobateaDB.getById(idBobatea);
    }
}
