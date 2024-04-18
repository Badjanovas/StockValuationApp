package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.DividendDiscountRequestDTO;
import com.example.StockValueApp.dto.DividendDiscountResponseDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.DividendDiscountModel;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.DividendDiscountRepository;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.mappingService.DividendDiscountMappingService;
import com.example.StockValueApp.validator.DividendDiscountRequestValidator;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import com.example.StockValueApp.validator.UserRequestValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
public class DividendDiscountService {

    private final DividendDiscountRepository dividendDiscountRepository;
    private final DividendDiscountMappingService dividendDiscountMappingService;
    private final DividendDiscountRequestValidator dividendDiscountRequestValidator;
    private final GlobalExceptionValidator globalExceptionValidator;
    private final UserRequestValidator userRequestValidator;
    private final UserRepository userRepository;

    @Cacheable(value = "dividendDiscountValuationsCache")
    public List<DividendDiscountModel> getAllDividendDiscountValuations() throws NoDividendDiscountModelFoundException {
        final List<DividendDiscountModel> dividendDiscountValuations = dividendDiscountRepository.findAll();
        dividendDiscountRequestValidator.validateDividendDiscountList(dividendDiscountValuations);

        log.info(dividendDiscountValuations.size() + " Dividend discount valuations were found in DB.");
        return dividendDiscountValuations;
    }

    @Cacheable(value = "dividendDiscountValuationsByTickerCache", key = "#ticker.concat('-').concat(#userId.toString())")
    public List<DividendDiscountResponseDTO> getDividendDiscountValuationsByTicker(final String ticker, final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);

        final List<DividendDiscountModel> companiesValuations = dividendDiscountRepository.findByUserId(userId);

        final List<DividendDiscountModel> filteredCompaniesByTicker = companiesValuations.stream()
                .filter(valuation -> valuation.getTicker().equalsIgnoreCase(ticker))
                .collect(Collectors.toList());

        dividendDiscountRequestValidator.validateDividendDiscountList(filteredCompaniesByTicker, ticker);
        log.info("Found " + filteredCompaniesByTicker.size() + " Dividend discount company valuations with ticker: " + ticker);
        return dividendDiscountMappingService.mapToResponse(filteredCompaniesByTicker);
    }

    @Cacheable(value = "dividendDiscountValuationsByCompanyNameCache", key = "#companyName.concat('-').concat(#userId.toString())")
    public List<DividendDiscountResponseDTO> getDividendDiscountValuationsByCompanyName(final String companyName, final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);

        final List<DividendDiscountModel> companiesValuations = dividendDiscountRepository.findByUserId(userId);

        final List<DividendDiscountModel> filteredCompaniesByCompanyName = companiesValuations.stream()
                .filter(valuation -> valuation.getCompanyName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());

        dividendDiscountRequestValidator.validateDividendDiscountList(filteredCompaniesByCompanyName, companyName);

        log.info("Found " + filteredCompaniesByCompanyName.size() + " Dividend discount company valuations with name: " + companyName);
        return dividendDiscountMappingService.mapToResponse(filteredCompaniesByCompanyName);
    }

    @Cacheable(value = "dividendDiscountValuationsByDateCache", key = "#date.toString().concat('-').concat(#userId.toString())")
    public List<DividendDiscountResponseDTO> getDividendDiscountValuationsByDate(final LocalDate date, final Long userId) throws NoDividendDiscountModelFoundException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);

        final List<DividendDiscountModel> companiesValuations = dividendDiscountRepository.findByUserId(userId);

        final List<DividendDiscountModel> filteredCompaniesByDate = companiesValuations.stream()
                .filter(valuation -> valuation.getCreationDate().equals(date))
                .collect(Collectors.toList());

        dividendDiscountRequestValidator.validateDividendDiscountList(filteredCompaniesByDate, date);

        log.info("Found " + filteredCompaniesByDate.size() + " Dividend discount valuations made at: " + date);
        return dividendDiscountMappingService.mapToResponse(filteredCompaniesByDate);
    }

    @Caching(evict = {
            @CacheEvict(value = "dividendDiscountValuationsCache", allEntries = true),
            @CacheEvict(value = "dividendDiscountValuationsByTickerCache", allEntries = true),
            @CacheEvict(value = "dividendDiscountValuationsByCompanyNameCache", allEntries = true),
            @CacheEvict(value = "dividendDiscountValuationsByDateCache", allEntries = true)
    })
    public List<DividendDiscountResponseDTO> addDividendDiscountValuation(final DividendDiscountRequestDTO dividendDiscountRequestDTO, final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException, IncorrectCompaniesExpectedGrowthException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);
        dividendDiscountRequestValidator.validateDividendDiscountRequest(dividendDiscountRequestDTO);
        dividendDiscountRequestValidator.validateExpectedGrowthRateInput(dividendDiscountRequestDTO.getWacc(),dividendDiscountRequestDTO.getExpectedGrowthRate());
        final User user = userRepository.getReferenceById(userId);

        final DividendDiscountModel dividendDiscountModel = dividendDiscountMappingService.mapToEntity(dividendDiscountRequestDTO);
        user.getDividendDiscountModels().add(dividendDiscountModel);

        dividendDiscountModel.setUser(user);
        dividendDiscountRepository.save(dividendDiscountModel);
        log.info("Calculation created successfully.");
        return dividendDiscountMappingService.mapToResponse(dividendDiscountRepository.findByUserId(userId));
    }
    @Caching(evict = {
            @CacheEvict(value = "dividendDiscountValuationsCache", allEntries = true),
            @CacheEvict(value = "dividendDiscountValuationsByTickerCache", allEntries = true),
            @CacheEvict(value = "dividendDiscountValuationsByCompanyNameCache", allEntries = true),
            @CacheEvict(value = "dividendDiscountValuationsByDateCache", allEntries = true)
    })
    public void deleteDividendDiscountValuationById(final Long id) throws NotValidIdException, NoDividendDiscountModelFoundException {
        globalExceptionValidator.validateId(id);
        dividendDiscountRequestValidator.validateDividendDiscountById(id);
        dividendDiscountRepository.deleteById(id);
        log.info("Dividend discount valuation  with id number " + id + " was deleted from DB successfully.");
    }


}
