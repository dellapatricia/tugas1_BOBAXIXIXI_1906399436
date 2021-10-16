package apap.tugas1.bobaxixixi.service;
import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.model.StoreModel;
import apap.tugas1.bobaxixixi.repository.ManagerDB;
import apap.tugas1.bobaxixixi.repository.StoreBobateaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreBobateaServiceImpl implements StoreBobateaService{

    @Autowired
    StoreBobateaDB storeBobateaDB;

    @Override
    public List<BobateaModel> getListBobatea(){
        return null;
    }

    @Override
    public void addStoreBobatea(StoreBobateaModel storeBobatea) {
        storeBobateaDB.save(storeBobatea);
    }

    @Override
    public List<StoreBobateaModel> getListStoreBobatea() {
        return storeBobateaDB.findAll();
    }

    @Override
    public List<StoreBobateaModel> getListStoreBobateabyStore(StoreModel store) {
        return storeBobateaDB.findStoreBobateaModelByStore(store);
    }

    @Override
    public void deleteStoreBobatea(StoreBobateaModel storeBobatea) {
        storeBobateaDB.delete(storeBobatea);
    }


}
