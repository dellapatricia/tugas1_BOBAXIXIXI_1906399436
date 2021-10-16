package apap.tugas1.bobaxixixi.controller;
import apap.tugas1.bobaxixixi.model.BobateaModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.model.StoreModel;
import apap.tugas1.bobaxixixi.service.BobateaService;
import apap.tugas1.bobaxixixi.service.StoreBobateaService;
import apap.tugas1.bobaxixixi.service.StoreService;
import apap.tugas1.bobaxixixi.service.ToppingService;
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
public class BobateaController {

    @Qualifier("bobateaServiceImpl")
    @Autowired
    private BobateaService bobateaService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeBobateaServiceImpl")
    @Autowired
    private StoreBobateaService storeBobateaService;

    @GetMapping("/boba")
    public String allBobatea(Model model){
        List<BobateaModel> listBobatea = bobateaService.getBobateaList();
        System.out.println(listBobatea);
        model.addAttribute("bobateaList", listBobatea);
        return "all-bobatea";
    }

    @GetMapping("/boba/add")
    public String addBobateaForm(Model model){
        String clicked = "cape";
        String namaBobatea = "";
        int hargaBobatea = 0;
        model.addAttribute("clicked", clicked);
        model.addAttribute("bobatea", new BobateaModel());
        model.addAttribute("namaBobatea", namaBobatea);
        model.addAttribute("hargaBobatea", hargaBobatea);
        model.addAttribute("listTopping",toppingService.getToppingList());
        return "form-add-bobatea";
    }

    @PostMapping("/boba/add")
    public String addBobateaSubmit(
            @ModelAttribute BobateaModel bobatea,
            Model model
    ) {
        if (bobatea.getSizeBobatea() == 3 || bobatea.getIceLevel() == 3 || bobatea.getSugarLevel() == 3){
//
            boolean clicked = false;
            model.addAttribute("clicked", clicked);
            model.addAttribute("namaBobatea", bobatea.getNamaBobatea());
            model.addAttribute("hargaBobatea", bobatea.getHargaBobatea());
            model.addAttribute("size", bobatea.getSizeBobatea());
            model.addAttribute("iceLevel", bobatea.getIceLevel());
            model.addAttribute("sugarLevel", bobatea.getSugarLevel());
            model.addAttribute("bobatea", bobatea);
            model.addAttribute("listTopping",toppingService.getToppingList());
            return "form-add-bobatea";
        }
        bobateaService.addBobatea(bobatea);
        model.addAttribute("namaBobatea",bobatea.getNamaBobatea());
        return "add-bobatea";
    }

    @GetMapping("/boba/update/{idBoba}")
    public String updateBobateaForm(
            @PathVariable Long idBoba,
            Model model
    ){
        BobateaModel bobatea = bobateaService.getBobateaById(idBoba);
        model.addAttribute("bobatea", bobatea);
        model.addAttribute("listTopping",toppingService.getToppingList());
        return "form-update-bobatea";
    }

    @PostMapping("/boba/update/{idStore}")
    public String updateBioskopSubmit(
            @ModelAttribute BobateaModel bobatea,
            Model model
    ){
        boolean failed = true;
        if (bobatea.getListStoreBobatea() == null){
            bobatea.setListStoreBobatea(new ArrayList<StoreBobateaModel>());
        }

        for (StoreBobateaModel i : bobatea.getListStoreBobatea()){
            if (i.getStore().getOpenHour().isBefore(LocalTime.now()) && i.getStore().getCloseHour().isAfter(LocalTime.now())){
                model.addAttribute("failed",failed);
                return "update-bobatea";
            }
        }
        bobateaService.updateBobatea(bobatea);
        failed = false;
        model.addAttribute("failed",failed);
        model.addAttribute("namaBobatea",bobatea.getNamaBobatea());
        return "update-bobatea";
    }

    @PostMapping("/boba/delete/{idBobatea}")
    public String deleteBobatea(
            @PathVariable Long idBobatea,
            @ModelAttribute BobateaModel bobatea,
            Model model
    ){
        String namaBobatea = bobateaService.getBobateaById(idBobatea).getNamaBobatea();
        bobateaService.deleteBobatea(bobatea);
        model.addAttribute("nameBobatea", namaBobatea);
        return "delete-bobatea";
    }

    @GetMapping("/search")
    public String searchBobabyTopping(
            @RequestParam(name = "bobaName", required = false) String namaBoba,
            @RequestParam(name = "topping", required = false) String namaTopping,
            Model model
    ){
        List<StoreBobateaModel> listSearchedBobatea = new ArrayList<>();
        for (StoreBobateaModel i : storeBobateaService.getListStoreBobatea()){
            if(i.getBobatea().getNamaBobatea().equals(namaBoba) && i.getBobatea().getTopping().getNamaTopping().equals(namaTopping)){
                if(i.getStore().getOpenHour().isBefore(LocalTime.now()) && i.getStore().getCloseHour().isAfter(LocalTime.now())){
                    listSearchedBobatea.add(i);
                }
            }
        }
        model.addAttribute("listSearchedBobatea",listSearchedBobatea);
        model.addAttribute("listBobatea",bobateaService.getBobateaList());
        model.addAttribute("listTopping", toppingService.getToppingList());
        return "search-boba-by-topping";
    }

    @GetMapping("/boba/{idBoba}/assign-store/")
    public String addStoreToBoba(
            @PathVariable Long idBoba,
            Model model
    ){
        model.addAttribute("storebobatea", new StoreBobateaModel());
        model.addAttribute("bobatea",bobateaService.getBobateaById(idBoba));
        model.addAttribute("listStore",storeService.getStoreList());
        return "check-add-store-to-boba";
    }

    @PostMapping("/boba/{idBoba}/assign-store/")
    public String addStoretoBobaSubmit(
            @PathVariable Long idBoba,
            @RequestParam Map<String, String> params,
            Model model
    ){
        BobateaModel bobatea = bobateaService.getBobateaById(idBoba);
        for (String i : params.values()){
            StoreModel store = storeService.getStoreByIdStore(Long.parseLong(i));
            StoreBobateaModel storeBobatea = new StoreBobateaModel();
            storeBobatea.setBobatea(bobatea);
            storeBobatea.setStore(store);
            storeBobatea.setProductionCode(processProductionCode(storeBobatea));
            storeBobateaService.addStoreBobatea(storeBobatea);
        }
        List<StoreModel> storeInBoba = new ArrayList<>();
        for (StoreBobateaModel i : bobatea.getListStoreBobatea()){
            storeInBoba.add(i.getStore());
        }
        model.addAttribute("namaBobatea", bobatea.getNamaBobatea());
        model.addAttribute("listStore", storeInBoba);
        return "assigned-store-to-bobatea";
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
