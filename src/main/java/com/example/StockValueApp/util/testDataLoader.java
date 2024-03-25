package com.example.StockValueApp.util;
import com.example.StockValueApp.model.GrahamsModule;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.GrahamsValuationRepo;
import com.example.StockValueApp.repository.UserRepo;
import com.example.StockValueApp.service.GrahamsValuationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class testDataLoader implements CommandLineRunner {

    private final GrahamsValuationService grahamsValuationService;
    private final GrahamsValuationRepo grahamsValuationRepo;
    private final UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Andrius", "password");
        User user2 = new User("Jons", "slaptazodis");
        User user3 = new User("Paulius", "paulius123");
        User user4 = new User("kastytis", "sitytsak");
        GrahamsModule calculation1 = new GrahamsModule("baba",6.43,10.0,5.06, user1);
        user1.getGrahamsModules().add(calculation1);
        System.out.println("Stock value is: " + grahamsValuationService.calculateGrahamsValuation(calculation1));
        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        grahamsValuationService.saveGrahamsValuation(calculation1);

    }
}
