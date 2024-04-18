package com.example.StockValueApp.service;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.dto.GrahamsResponseDTO;
import com.example.StockValueApp.exception.*;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.GrahamsModelRepository;
import com.example.StockValueApp.repository.UserRepository;
import com.example.StockValueApp.service.mappingService.GrahamsModelMappingService;
import com.example.StockValueApp.util.CacheConfig;
import com.example.StockValueApp.validator.GlobalExceptionValidator;
import com.example.StockValueApp.validator.GrahamsModelRequestValidator;
import com.example.StockValueApp.validator.UserRequestValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Data
public class GrahamsModelService {

    private final GrahamsModelRepository grahamsRepository;
    private final GrahamsModelMappingService grahamsModelMappingService;
    private final GlobalExceptionValidator globalExceptionValidator;
    private final GrahamsModelRequestValidator grahamsModelRequestValidator;
    private final UserRequestValidator userRequestValidator;
    private final UserRepository userRepository;

    @Caching(evict = {
            @CacheEvict(value = "grahamsValuationsCache", allEntries = true),
            @CacheEvict(value = "grahamsValuationsByTickerCache", allEntries = true),
            @CacheEvict(value = "grahamsValuationByCompanyNameCache", allEntries = true),
            @CacheEvict(value = "grahamsValuationsByDateCache", allEntries = true)
    })
    public List<GrahamsResponseDTO> addGrahamsValuation(final GrahamsRequestDTO grahamsRequestDTO, final Long userId) throws MandatoryFieldsMissingException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);
        grahamsModelRequestValidator.validateGrahamsModelRequest(grahamsRequestDTO);
        final User user = userRepository.getReferenceById(userId);

        final GrahamsModel grahamsModel = grahamsModelMappingService.mapToEntity(grahamsRequestDTO);
        user.getGrahamsModels().add(grahamsModel);
        grahamsModel.setUser(user);

        grahamsRepository.save(grahamsModel);
        log.info("Calculation created successfully.");
        return grahamsModelMappingService.mapToResponse(grahamsRepository.findByUserId(userId));
    }

    @Caching(evict = {
            @CacheEvict(value = "grahamsValuationsCache", allEntries = true),
            @CacheEvict(value = "grahamsValuationsByTickerCache", allEntries = true),
            @CacheEvict(value = "grahamsValuationByCompanyNameCache", allEntries = true),
            @CacheEvict(value = "grahamsValuationsByDateCache", allEntries = true)
    })
    public void deleteGrahamsValuationById(final Long valuationId, final Long userId) throws NotValidIdException, NoGrahamsModelFoundException, ValuationDoestExistForSelectedUser {
        globalExceptionValidator.validateId(valuationId);
        globalExceptionValidator.validateId(userId);
        grahamsModelRequestValidator.validateGrahamsModelById(valuationId);
        grahamsModelRequestValidator.validateGrahamsModelForUser(valuationId, userId);

        grahamsRepository.deleteById(valuationId);
        log.info("Grahams valuation  with id number " + valuationId + " was deleted from DB successfully.");
    }

    @Cacheable(value = "grahamsValuationsCache")
    public List<GrahamsModel> getAllGrahamsValuations() throws NoGrahamsModelFoundException {
        final List<GrahamsModel> grahamsValuations = grahamsRepository.findAll();
        grahamsModelRequestValidator.validateGrahamsModelList(grahamsValuations);

        log.info(grahamsValuations.size() + " Grahams valuations were found in DB.");
        return grahamsValuations;
    }

    // The key expression #ticker.concat('-').concat(#usersId.toString()) constructs a unique identifier for caching by concatenating the stock ticker symbol (ticker)
// and user ID (usersId), separated by a hyphen. This ensures that cached data is specific to both the stock and the user, preventing cache collisions between
// different stocks or users.
    @Cacheable(value = "grahamsValuationsByTickerCache", key = "#ticker.concat('-').concat(#userId.toString())")
    public List<GrahamsResponseDTO> getGrahamsValuationsByTicker(final String ticker, final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);
        final List<GrahamsModel> companiesValuations = grahamsRepository.findByUserId(userId);

        final List<GrahamsModel> filteredCompaniesByTicker = companiesValuations.stream()
                .filter(valuation -> valuation.getTicker().equalsIgnoreCase(ticker))
                .collect(Collectors.toList());

        grahamsModelRequestValidator.validateGrahamsModelList(filteredCompaniesByTicker, ticker);

        log.info("Found " + companiesValuations.size() + " Grahams company valuations with ticker: " + ticker);
        return grahamsModelMappingService.mapToResponse(filteredCompaniesByTicker);
    }

    @Cacheable(value = "grahamsValuationByCompanyNameCache", key = "#companyName.concat('-').concat(#userId.toString())")
    public List<GrahamsResponseDTO> getGrahamsValuationsByCompanyName(final String companyName, final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);
        final List<GrahamsModel> companiesValuations = grahamsRepository.findByUserId(userId);

        final List<GrahamsModel> filteredCompaniesByCompanyName = companiesValuations.stream()
                .filter(valuation -> valuation.getName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());

        grahamsModelRequestValidator.validateGrahamsModelList(filteredCompaniesByCompanyName, companyName);

        log.info("Found " + filteredCompaniesByCompanyName.size() + " Grahams company valuations with ticker: " + companyName);
        return grahamsModelMappingService.mapToResponse(filteredCompaniesByCompanyName);
    }

    @Cacheable(value = "grahamsValuationsByDateCache", key = "#date.toString().concat('-').concat(#userId.toString())")
    public List<GrahamsResponseDTO> getGrahamsValuationsByDate(final LocalDate date, final Long userId) throws NoGrahamsModelFoundException, NotValidIdException, NoUsersFoundException {
        globalExceptionValidator.validateId(userId);
        userRequestValidator.validateUserById(userId);
        final List<GrahamsModel> companiesValuations = grahamsRepository.findByUserId(userId);

        final List<GrahamsModel> filteredValuationsByDate = companiesValuations.stream()
                .filter(valuation -> valuation.getCreationDate().equals(date))
                .collect(Collectors.toList());

        grahamsModelRequestValidator.validateGrahamsModelList(filteredValuationsByDate, date);

        log.info("Found " + filteredValuationsByDate.size() + " Grahams valuations made at: " + date);
        return grahamsModelMappingService.mapToResponse(filteredValuationsByDate);
    }


}
