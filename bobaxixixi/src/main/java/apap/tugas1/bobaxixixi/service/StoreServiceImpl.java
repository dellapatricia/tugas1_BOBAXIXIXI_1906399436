package apap.tugas1.bobaxixixi.service;
import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.model.StoreModel;
import apap.tugas1.bobaxixixi.repository.StoreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreDB storeDB;

    @Override
    public void addStore(StoreModel store){
        storeDB.save(store);
    }

    @Override
    public void updateStore(StoreModel store) {
        storeDB.save(store);
    }

    @Override
    public void deleteStore(StoreModel store) {
        storeDB.delete(store);
//        System.out.println(idStore);
//        storeDB.deleteById(idStore);
//        System.out.println(storeDB.findById(store.getId()).get().getNamaStore());
    }

//    @Override
//    public void deleteById(Long id){
//        storeDB.deleteById(id);
////        System.out.println(storeDB.findAll().size());
////        System.out.println(storeDB.findById(id).get().getNamaStore());
//    }

    @Override
    public List<StoreModel> getStoreList() {
        return storeDB.findAll();
    }

    @Override
    public StoreModel getStoreByIdStore(Long idStore) {
        Optional<StoreModel> store = storeDB.findById(idStore);
//        StoreModel store = storeDB.findById(idStore);
        return store.get();
    }

    @Override
    public List<String> getStoreCodeList(){
        List<String> storeCodeList = new ArrayList<>();
        for (StoreModel store : getStoreList()){
            storeCodeList.add(store.getStoreCode());
        }
        return storeCodeList;
    }

    @Override
    public List<BobateaModel> getBobateaList(StoreModel store){
        List<StoreBobateaModel> storeBobateaList = store.getListStoreBobatea();
        List<BobateaModel> listBobatea = new ArrayList<>();
        for (StoreBobateaModel i : storeBobateaList){
            listBobatea.add(i.getBobatea());
        }
        return listBobatea;
    }

}
