package org.softuni.pastryShop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.softuni.pastryShop.model.dto.CurrencyDTO;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.repository.CurrencyRepository;
import org.softuni.pastryShop.service.impl.CurrencyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CurrencyServiceImplTest {
    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        // Mock data
        List<Currency> currencies = new ArrayList<>();
        Currency currency1 = new Currency();
        currency1.setId(1L);
        currency1.setName("Leva");
        currency1.setShortName("BGN");
        currency1.setConversionRate(1.00);

        Currency currency2 = new Currency();
        currency2.setId(2L);
        currency2.setName("Euro");
        currency2.setShortName("EUR");
        currency2.setConversionRate(0.51);

        currencies.add(currency1);
        currencies.add(currency2);

        when(currencyRepository.findAll()).thenReturn(currencies);

        // Test method
        List<CurrencyDTO> result = currencyService.getAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Leva", result.get(0).getName());
        assertEquals("BGN", result.get(0).getShortName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Euro", result.get(1).getName());
        assertEquals("EUR", result.get(1).getShortName());
    }

}
