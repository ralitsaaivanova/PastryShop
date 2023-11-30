package org.softuni.pastryShop.model.dto;

import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.Measure;

public record ProductDTO (String name,
                          Double price,
                          Double weight,
                          //photo
                          Currency currency,
                          Measure measure,
                          Category category){
}
