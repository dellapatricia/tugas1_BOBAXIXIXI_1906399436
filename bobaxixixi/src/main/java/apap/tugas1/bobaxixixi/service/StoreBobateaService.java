package apap.tugas1.bobaxixixi.service;
import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.model.StoreModel;

import java.util.List;
public interface StoreBobateaService {
    List<BobateaModel> getListBobatea();
    void addStoreBobatea(StoreBobateaModel storeBobatea);
    List<StoreBobateaModel> getListStoreBobatea();
    List<StoreBobateaModel> getListStoreBobateabyStore(StoreModel store);
    void deleteStoreBobatea(StoreBobateaModel storeBobatea);

}
