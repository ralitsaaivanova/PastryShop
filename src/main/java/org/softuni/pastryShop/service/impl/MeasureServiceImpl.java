package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.entities.Measure;
import org.softuni.pastryShop.repository.MeasureRepository;
import org.softuni.pastryShop.service.MeasureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureServiceImpl implements MeasureService {
    private final MeasureRepository measureRepository;

    public MeasureServiceImpl(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;
    }

    @Override
    public List<Measure> getAll() {
        return this.measureRepository.findAll();
    }
}
