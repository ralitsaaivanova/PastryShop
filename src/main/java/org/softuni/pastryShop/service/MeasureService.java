package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.MeasureDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Measure;

import java.util.List;

public interface MeasureService {
    List<MeasureDTO> getAll();
}
