package apap.tugas1.bobaxixixi.controller;
import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.model.StoreModel;
import apap.tugas1.bobaxixixi.model.ManagerModel;
import apap.tugas1.bobaxixixi.service.BobateaService;
import apap.tugas1.bobaxixixi.service.StoreBobateaService;
import apap.tugas1.bobaxixixi.service.StoreService;
import apap.tugas1.bobaxixixi.service.ManagerService;
import org.apache.catalina.Store;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util. ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Map;
import java.util.Random;

@Controller
public class StoreController {

    @Qualifier("bobateaServiceImpl")
    @Autowired
    private BobateaService bobateaService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("managerServiceImpl")
    @Autowired
    ManagerService managerService;

    @Qualifier("storeBobateaServiceImpl")
    @Autowired
    private StoreBobateaService storeBobateaService;

    @GetMapping("/store")
    public String allStore(Model model){
        List<StoreModel> listStore = storeService.getStoreList();
        model.addAttribute("listStore", listStore);
        return "all-store";
    }

    @GetMapping("/store/add")
    public String addStoreForm(Model model){
        String namaStore = "";
        String alamatStore = "";
        LocalTime openHour = null;
        LocalTime closeHour = null;
        boolean clicked = false;
        model.addAttribute("clicked", clicked);
        model.addAttribute("namaStore",namaStore);
        model.addAttribute("alamatStore",alamatStore);
        model.addAttribute("openHour",openHour);
        model.addAttribute("closeHour",openHour);
        model.addAttribute("store", new StoreModel());
        model.addAttribute("listManager",managerService.getManagerList());
        return "form-add-store";
    }

    @PostMapping("/store/add")
    public String addStoreSubmit(
            @ModelAttribute StoreModel store,
            Model model
    ) {
        if (store.getManager() == null){
            boolean clicked = true;
            model.addAttribute("manager",store.getManager());
            model.addAttribute("clicked", clicked);
            model.addAttribute("namaStore",store.getNamaStore());
            model.addAttribute("alamatStore",store.getAlamatStore());
            model.addAttribute("openHour",store.getOpenHour());
            model.addAttribute("closeHour",store.getCloseHour());
            model.addAttribute("store",store);
            model.addAttribute("listManager",managerService.getManagerList());
            return "form-add-store";
        }
        String storeCode = processStoreCode(store);
        store.setStoreCode(storeCode);
        storeService.addStore(store);
        model.addAttribute("storeCode",storeCode);
        model.addAttribute("namaStore",store.getNamaStore());
        return "add-store";
    }

    @GetMapping("/store/update/{idStore}")
    public String updateStoreForm(
            @PathVariable Long idStore,
            Model model
    ){
        StoreModel store = storeService.getStoreByIdStore(idStore);
        model.addAttribute("store", store);
        model.addAttribute("listManager",managerService.getManagerList());
        return "form-update-store";
    }

    @PostMapping("/store/update/{idStore}")
    public String updateStoreSubmit(
            @ModelAttribute StoreModel store,
            Model model
    ){
        if ((store.getOpenHour().isBefore(LocalTime.now())&&(store.getCloseHour()).isAfter(LocalTime.now()))){
            model.addAttribute("namaStore",store.getNamaStore());
            return "fail-update-store";
        }
        String storeCode = processStoreCode(store);
        store.setStoreCode(storeCode);
        storeService.updateStore(store);
        model.addAttribute( "storeCode",store.getStoreCode());
        model.addAttribute("namaStore",store.getNamaStore());
        return "update-store";
    }

    @GetMapping("/store/view/{idStore}")
    public String viewDetailStore(
            @PathVariable Long idStore,
            Model model
    ){
        StoreModel store = storeService.getStoreByIdStore(idStore);
//        List<StoreBobateaModel> listStoreBobatea = store.getListStoreBobatea();
//        List<BobateaModel> listBobatea = new ArrayList<>();
//        for (StoreBobateaModel i : listStoreBobatea){
//            listBobatea.add(i.getBobatea());
//        }


        model.addAttribute("store", store);
        model.addAttribute("manager",store.getManager());
//        model.addAttribute("listBobatea",listBobatea);
        model.addAttribute("listBobateaInStore", store.getListStoreBobatea());
        return "view-store";
    }

    @PostMapping("/store/delete/{idStore}")
    public String deleteStore(
            @PathVariable Long idStore,
            Model model
    ){
        boolean closed = false;
        boolean hasNoBoba = false;
        StoreModel store = storeService.getStoreByIdStore(idStore);
        if (!((LocalTime.now().isAfter(store.getOpenHour()))&&(LocalTime.now().isBefore(store.getCloseHour())))){
            closed = true;
        }
        if(storeService.getBobateaList(store).isEmpty()){
            hasNoBoba = true;
        }
        if (closed == true && hasNoBoba == true){
            storeService.deleteStore(store);
        }
        model.addAttribute("nameStore", store.getNamaStore());
        model.addAttribute("closed", closed);
        model.addAttribute("hasNoBoba", hasNoBoba);
        return "delete-store";
    }

    @GetMapping("/store/{idStore}/assign-boba/")
    public String addBobaToStore(
            @PathVariable Long idStore,
            Model model
    ){
        StoreModel store = storeService.getStoreByIdStore(idStore);
        List<BobateaModel> unassigned = new ArrayList<>();
        for(BobateaModel i : bobateaService.getBobateaList()){
            if (storeService.getBobateaList(store).contains(i) == false ){
                unassigned.add(i);
            }
        }


//        model.addAttribute("listBobateaInStore", storeBobateaService.getListStoreBobatea());
        model.addAttribute("unassigned", unassigned);
        model.addAttribute("storebobatea", new StoreBobateaModel());
        model.addAttribute("store",store);
        model.addAttribute("listStoreBobatea", store.getListStoreBobatea());
//        model.addAttribute("listBobatea",bobateaService.getBobateaList());
        return "check-add-boba-to-store";
    }

    @PostMapping("/store/{idStore}/assign-boba/")
    public String addBobaToStoreSubmit(
            @PathVariable Long idStore,
            @RequestParam Map<String, String> params,
            @ModelAttribute StoreModel storeModel,
            Model model
    ){
//        System.out.println("aaaa " + params);
        StoreModel store = storeService.getStoreByIdStore(idStore);
        for (String i : params.values()){
            BobateaModel bobatea = bobateaService.getBobateaById(Long.parseLong(i));
            StoreBobateaModel storeBobatea = new StoreBobateaModel();
            storeBobatea.setBobatea(bobatea);
            storeBobatea.setStore(store);
            storeBobatea.setProductionCode(processProductionCode(storeBobatea));
            storeBobateaService.addStoreBobatea(storeBobatea);
        }
//        System.out.println(storeModel.getListStoreBobatea());
////        for(StoreBobateaModel k : storeModel.getListStoreBobatea()){
////            System.out.println(k.getBobatea().getNamaBobatea());
////        }
//        for(StoreBobateaModel j : storeModel.getListStoreBobatea()){
//            if (storeBobateaService.getListStoreBobateabyStore(store).contains(j) == false){
//                BobateaModel bobatea = bobateaService.getBobateaById(j.getBobatea().getIdBobatea());
//                StoreBobateaModel storeBobatea = new StoreBobateaModel();
//                storeBobatea.setStore(storeModel);
//                storeBobatea.setBobatea(bobatea);
//                storeBobatea.setProductionCode(processProductionCode(storeBobatea));
//                storeBobateaService.addStoreBobatea(storeBobatea);
//            }
//        }
//        for (StoreBobateaModel i : storeBobateaService.getListStoreBobateabyStore(store)){
//
//            if (storeModel.getListStoreBobatea().contains(i) == false){
//                storeBobateaService.deleteStoreBobatea(i);
//            }
//        }
        //        storeBobateaService.addStoreBobatea(s);
        model.addAttribute("namaStore", store.getNamaStore());
        model.addAttribute("idStore", store.getIdStore());
        model.addAttribute("listBobateaInStore", store.getListStoreBobatea());
        return "assigned-bobatea-to-store";
    }


    private String processStoreCode(StoreModel store){
        String storeCode = "SC";

        // 3 karakter pertama name dari STORE dan dibalik
        String storeCodeNama = store.getNamaStore().toUpperCase().substring(0,3);
        StringBuffer sb = new StringBuffer(storeCodeNama);
        storeCode = storeCode + sb.reverse();

        // 3 karakter hh pertama dari open_hour dan h terakhir dari close_hour yang dibagi 10 kemudian dibulatkan ke bawah
        String storeCodeOpen = store.getOpenHour().toString().split(":")[0];
        String storeCodeClose = Integer.toString(Integer.parseInt(store.getCloseHour().toString().split(":")[0])/10);
        storeCode = storeCode + storeCodeOpen + storeCodeClose;

        // 2 karakter terakhir adalah huruf kapital random
        String checkStoreCode = storeCode;
        for (int i = 0; i<2; i++){
            Random r = new Random();
            char c = (char)(r.nextInt(26) + 'a');
            checkStoreCode = checkStoreCode + String.valueOf(c).toUpperCase();
        }

        for(String s : storeService.getStoreCodeList()){
           while(s.equals(checkStoreCode)){
               checkStoreCode = storeCode;
                for (int i = 0; i<2; i++){
                    Random r = new Random();
                    char c = (char)(r.nextInt(26) + 'a');
                    checkStoreCode = checkStoreCode + String.valueOf(c).toUpperCase();
                }
           }
        }

        storeCode = checkStoreCode;

        return storeCode;
    }

    private String processProductionCode(StoreBobateaModel storeBobateaModel){
        String prodCode = "PC";

        // 3 karakter selanjutnya adalah id dari STORE dengan penambahan 0 di depan sesuai kebutuhan
        String prodCodeIDStore = Long.toString(storeBobateaModel.getStore().getIdStore());
        if (prodCodeIDStore.length() == 1){
            prodCodeIDStore = "00" + prodCodeIDStore;
        } else if (prodCodeIDStore.length() == 2){
            prodCodeIDStore = "0" + prodCodeIDStore;
        }
        prodCode = prodCode + prodCodeIDStore;

        //1 karakter selanjutnya adalah angka 0 jika boba tidak memiliki topping atau 1 jika boba memiliki topping
        if (storeBobateaModel.getBobatea().getTopping() == null){
            prodCode = prodCode + "0";
        } else {
            prodCode = prodCode + "1";
        }

        // 3 karakter selanjutnya adalah id dari BOBA_TEA dengan penambahan 0 di depan sesuai kebutuhan
        String prodCodeIDBobatea = Long.toString(storeBobateaModel.getBobatea().getIdBobatea());
        if (prodCodeIDBobatea.length() == 1){
            prodCodeIDBobatea = "00" + prodCodeIDBobatea;
        } else if (prodCodeIDBobatea.length() == 2){
            prodCodeIDBobatea = "0" + prodCodeIDBobatea;
        }
        prodCode = prodCode + prodCodeIDBobatea;

        return prodCode;
    }


}
