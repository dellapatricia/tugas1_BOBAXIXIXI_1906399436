package apap.tugas1.bobaxixixi.service;

import apap.tugas1.bobaxixixi.model.BobateaModel;

import java.util.List;

public interface BobateaService {
    void addBobatea(BobateaModel bobatea);
    void updateBobatea(BobateaModel bobatea);
    void deleteBobatea(BobateaModel bobatea);
    List<BobateaModel> getBobateaList();
    BobateaModel getBobateaById(Long idBobatea);
}
