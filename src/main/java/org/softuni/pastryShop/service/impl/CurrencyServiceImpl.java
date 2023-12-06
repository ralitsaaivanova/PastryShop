package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.repository.CurrencyRepository;
import org.softuni.pastryShop.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getAll() {
        return this.currencyRepository.findAll();
    }
}
