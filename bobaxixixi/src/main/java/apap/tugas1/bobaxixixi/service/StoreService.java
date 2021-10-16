package apap.tugas1.bobaxixixi.service;

import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.model.StoreModel;

import java.util.List;

public interface StoreService {
    void addStore(StoreModel store);
    void updateStore(StoreModel store);
    void deleteStore(StoreModel store);
//    void deleteById(Long id);
    List<StoreModel> getStoreList();
    List<String> getStoreCodeList();
    StoreModel getStoreByIdStore(Long idStore);
    List<BobateaModel> getBobateaList(StoreModel store);

}
