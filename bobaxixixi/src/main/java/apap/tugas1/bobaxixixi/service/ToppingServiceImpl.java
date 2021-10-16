package apap.tugas1.bobaxixixi.service;
import apap.tugas1.bobaxixixi.model.ToppingModel;
import apap.tugas1.bobaxixixi.repository.ToppingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ToppingServiceImpl implements ToppingService{

    @Autowired
    ToppingDB toppingDB;

    @Override
    public List<ToppingModel> getToppingList() {
        return toppingDB.findAll();
    }
}
