package apap.tugas1.bobaxixixi.controller;
import apap.tugas1.bobaxixixi.model.ManagerModel;
import apap.tugas1.bobaxixixi.model.StoreBobateaModel;
import apap.tugas1.bobaxixixi.service.BobateaService;
import apap.tugas1.bobaxixixi.service.ManagerService;
import apap.tugas1.bobaxixixi.service.StoreBobateaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util. ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Random;

@Controller
public class ManagerController {

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("bobateaServiceImpl")
    @Autowired
    private BobateaService bobateaService;

    @Qualifier("storeBobateaServiceImpl")
    @Autowired
    private StoreBobateaService storeBobateaService;

    @GetMapping("/filter/manager")
    public String filterManager(
            @RequestParam(name = "nameBoba", required = false) String namaBobatea,
            Model model
    ){
        List<ManagerModel> listManager = new ArrayList<>();
        for (StoreBobateaModel i : storeBobateaService.getListStoreBobatea()){
            if (i.getBobatea().getNamaBobatea().equals(namaBobatea)){
                listManager.add(i.getStore().getManager());
            }
        }
        model.addAttribute("listBobatea",bobateaService.getBobateaList());
        model.addAttribute("listManager",listManager);
        return "filter-manager";
    }



}
