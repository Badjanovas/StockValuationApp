package com.example.StockValueApp.util;
import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.GrahamsModelService;
import com.example.StockValueApp.service.mappingService.GrahamsModelMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class testDataLoader implements CommandLineRunner {

    private final GrahamsModelService grahamsModelService;
    private final UserRepository userRepository;
    private final GrahamsModelMappingService grahamsModelMappingService;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Andrius", "password", "lalala@gmail.com");
        User user2 = new User("Jons", "slaptazodis", "MrEmail@yahoo.com");
        User user3 = new User("Paulius", "paulius123", "fake@hotmail.com");
        User user4 = new User("kastytis", "sitytsak", "blablabla@gmail.com");
        GrahamsRequestDTO userInput = new GrahamsRequestDTO("Alibaba","baba",6.43,10.0,5.06);
        GrahamsModel calculation1 = grahamsModelMappingService.mapToEntity(userInput);
        user1.getGrahamsModels().add(calculation1);
        calculation1.setUser(user1);


        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        grahamsModelService.addGrahamsValuation(userInput);
        System.out.println(calculation1.getIntrinsicValue() + " This is the value of a company");

    }
}
