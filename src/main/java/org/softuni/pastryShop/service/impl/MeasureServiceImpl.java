package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.CurrencyDTO;
import org.softuni.pastryShop.model.dto.MeasureDTO;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.Measure;
import org.softuni.pastryShop.repository.MeasureRepository;
import org.softuni.pastryShop.service.MeasureService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasureServiceImpl implements MeasureService {
    private final MeasureRepository measureRepository;

    public MeasureServiceImpl(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;
    }

    @Override
    public List<MeasureDTO> getAll() {
        List<Measure> measures = measureRepository.findAll();

        List<MeasureDTO> measureDTOS = new ArrayList<>();
        for (Measure measure:measures) {
            MeasureDTO currencyDTO = mapMeasureToMeasureDTO(measure);
            measureDTOS.add(currencyDTO);
        }

        return measureDTOS;
    }

    private Measure mapMeasureDTOtoMeasure(MeasureDTO measureDTO) {

        Measure measure = new Measure();
        measure.setName(measureDTO.getName());
        measure.setShortName(measureDTO.getShortName());

        return measure;
    }

    private MeasureDTO mapMeasureToMeasureDTO(Measure measure){
        MeasureDTO measureDTO = new MeasureDTO();
        measureDTO.setName(measure.getName());
        measureDTO.setShortName(measure.getShortName());

        return measureDTO;
    }
}
