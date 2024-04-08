package com.example.StockValueApp.util;
import com.example.StockValueApp.dto.DcfModelRequestDTO;
import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.model.DcfModel;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.DcfModelRepository;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.DcfModelService;
import com.example.StockValueApp.service.GrahamsModelService;
import com.example.StockValueApp.service.mappingService.DcfModelMappingService;
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

    private final DcfModelService dcfModelService;
    private final DcfModelMappingService dcfModelMappingService;
    private final DcfModelRepository dcfModelRepository;

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




        DcfModelRequestDTO userInput2 = new DcfModelRequestDTO("PayPal", "pypl", 81817.0, 21540.0, 61115.0, 1070.0);
        DcfModel calculation2 = dcfModelMappingService.mapToEntity(userInput2);
        user1.getDcfModel().add(calculation2);
        calculation2.setUser(user1);


        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        dcfModelRepository.save(calculation2);
        grahamsModelService.addGrahamsValuation(userInput);
        System.out.println(calculation1.getIntrinsicValue() + " This is the value of alibaba ");

        System.out.println(calculation2.getIntrinsicValue() + " This is the value of paypal");
    }
}
