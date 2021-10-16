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
public class BaseController {
    @Qualifier("bobateaServiceImpl")
    @Autowired
    private BobateaService bobateaService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("storeBobateaServiceImpl")
    @Autowired
    private StoreBobateaService storeBobateaService;

    @GetMapping("/")
    private String home() { return "home";}


    @GetMapping("/bonus")
    private String searchBonus(){
        return "search-bonus";
    }

    @PostMapping("/bonus")
    private String searchBonus(
            @RequestParam(name = "keyword") String keyword,
            Model model
    ){
        boolean post = false;

        if (keyword != ""){
            List<BobateaModel> listBobatea = new ArrayList<>();
            List<StoreModel> listStore = new ArrayList<>();
            post = true;
            for (BobateaModel boba : bobateaService.getBobateaList()){
                if (boba.getNamaBobatea().toLowerCase().contains(keyword.toLowerCase())){
                    listBobatea.add(boba);
                }
            }

            for(StoreModel store : storeService.getStoreList()){
                if(store.getNamaStore().toLowerCase().contains(keyword.toLowerCase())){
                    listStore.add(store);
                }
            }

            for(StoreBobateaModel storeBobatea : storeBobateaService.getListStoreBobatea()){
                if(storeBobatea.getBobatea().getNamaBobatea().toLowerCase().contains(keyword.toLowerCase())){
                    listStore.add(storeBobatea.getStore());
                }
            }
            model.addAttribute("listBobatea", listBobatea);
            model.addAttribute("listStore",listStore);
        }

        model.addAttribute("post",post);
        return "search-bonus";

    }


}
