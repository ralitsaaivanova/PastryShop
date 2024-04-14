package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.CurrencyDTO;
import org.softuni.pastryShop.model.entities.Currency;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDTO> getAll();
}
