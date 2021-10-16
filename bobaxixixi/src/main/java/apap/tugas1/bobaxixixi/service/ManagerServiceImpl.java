package apap.tugas1.bobaxixixi.service;
import apap.tugas1.bobaxixixi.model.ManagerModel;
import apap.tugas1.bobaxixixi.repository.ManagerDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    ManagerDB managerDB;

    @Override
    public List<ManagerModel> getManagerList() {
        return managerDB.findAll();
    }

}
