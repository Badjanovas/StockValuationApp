package com.example.StockValueApp.validator;

import com.example.StockValueApp.dto.GrahamsRequestDTO;
import com.example.StockValueApp.exception.MandatoryFieldsMissingException;
import com.example.StockValueApp.exception.NoGrahamsModelFoundException;
import com.example.StockValueApp.exception.NoUsersFoundException;
import com.example.StockValueApp.exception.ValuationDoestExistForSelectedUser;
import com.example.StockValueApp.model.GrahamsModel;
import com.example.StockValueApp.model.User;
import com.example.StockValueApp.repository.GrahamsModelRepository;
import com.example.StockValueApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrahamsModelRequestValidator {

    private final GrahamsModelRepository grahamsModelRepository;

    public void validateGrahamsModelRequest(final GrahamsRequestDTO grahamsRequestDTO) throws MandatoryFieldsMissingException {
        if (grahamsRequestDTO == null){
            log.error("Request was empty.");
            throw new MandatoryFieldsMissingException("Request was empty.");
        } else if (grahamsRequestDTO.getCompanyName() == null){
            log.error("Mandatory company name field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory company name field is missing.");
        } else if (grahamsRequestDTO.getCompanyName().isBlank()){
            log.error("Mandatory company name field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory company name field is empty.");
        } else if (grahamsRequestDTO.getCompanyTicker() == null){
            log.error("Mandatory company ticker field is missing.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is missing.");
        } else if (grahamsRequestDTO.getCompanyTicker().isBlank()){
            log.error("Mandatory company ticker field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory company ticker field is empty.");
        } else if (grahamsRequestDTO.getEps() == null){
            log.error("Mandatory eps field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory eps field is empty.");
        } else if (grahamsRequestDTO.getGrowthRate() == null){
            log.error("Mandatory growth rate field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory growth rate field is empty.");
        } else if (grahamsRequestDTO.getCurrentYieldOfBonds() == null){
            log.error("Mandatory current yield of bonds field is empty.");
            throw new MandatoryFieldsMissingException("Mandatory current yield of bonds field is empty.");
        }
    }

    public void validateGrahamsModelById(final Long id) throws NoGrahamsModelFoundException {
        if (!grahamsModelRepository.existsById(id)){
            log.error("Grahams valuation with id number " + id + " not found.");
            throw new NoGrahamsModelFoundException("Grahams valuation with id number " + id + " not found.");
        }
    }

    public void validateGrahamsModelList(final List<GrahamsModel> grahamsValuations, String companyNameOrTicker) throws NoGrahamsModelFoundException {
        if (grahamsValuations.isEmpty()){
            log.error("No Graham valuations found for: " + companyNameOrTicker + ".");
            throw new NoGrahamsModelFoundException("No Graham valuations found for: " + companyNameOrTicker + ".");
        }
    }

    public void validateGrahamsModelList(final List<GrahamsModel> valuationList) throws NoGrahamsModelFoundException {
        if (valuationList.isEmpty()){
            log.error("No Graham valuations.");
            throw new NoGrahamsModelFoundException("No Graham valuations.");
        }
    }

    public void validateGrahamsModelList(final List<GrahamsModel> valuationList, LocalDate date) throws NoGrahamsModelFoundException {
        if (valuationList.isEmpty()){
            log.error("There are no valuations made in " + date);
            throw new NoGrahamsModelFoundException("There are no valuations made in " + date);
        }
    }
    public void validateGrahamsModelForUser(final Long valuationId, final Long userId) throws ValuationDoestExistForSelectedUser {
        Optional<GrahamsModel> valuation = grahamsModelRepository.findById(valuationId);
        if (valuation.isEmpty() || !valuation.get().getUser().getId().equals(userId)) {
            log.error("Valuation does not exist for this user");
            throw new ValuationDoestExistForSelectedUser("Valuation does not exist for this user");
        }
    }
}
