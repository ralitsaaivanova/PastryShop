package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.dto.CurrencyDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.CurrencyRepository;
import org.softuni.pastryShop.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<CurrencyDTO> getAll() {
        List<Currency> currencies = currencyRepository.findAll();

        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        for (Currency currency:currencies) {
            CurrencyDTO currencyDTO = mapCurrencyToCurrencyDTO(currency);
            currencyDTOS.add(currencyDTO);
        }

        return currencyDTOS;
    }

    private Currency mapCurrencyDTOtoCurrency(CurrencyDTO currencyDTO) {

        Currency currency = new Currency();
        currency.setName(currencyDTO.getName());
        currency.setShortName(currencyDTO.getShortName());
        if(currencyDTO.getName().equals("leva")){
            currency.setConversionRate(1.00);
        }else if(currencyDTO.getName().equals("euro")){
            currency.setConversionRate(0.51);
        }

        return currency;
    }

    private CurrencyDTO mapCurrencyToCurrencyDTO(Currency currency){
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setId(currency.getId());
        currencyDTO.setName(currency.getName());
        currencyDTO.setShortName(currency.getShortName());

        return currencyDTO;
    }
}
