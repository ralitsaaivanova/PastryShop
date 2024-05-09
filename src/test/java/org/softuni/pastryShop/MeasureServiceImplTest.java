package org.softuni.pastryShop;

import org.junit.jupiter.api.Test;
import org.softuni.pastryShop.model.dto.MeasureDTO;
import org.softuni.pastryShop.model.entities.Measure;
import org.softuni.pastryShop.repository.MeasureRepository;
import org.softuni.pastryShop.service.impl.MeasureServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeasureServiceImplTest {

    @Test
    void testGetAll() {
        // Mock data
        MeasureRepository measureRepository = mock(MeasureRepository.class);
        MeasureServiceImpl measureService = new MeasureServiceImpl(measureRepository);

        List<Measure> measures = new ArrayList<>();
        Measure measure1 = new Measure();
        measure1.setId(1L);
        measure1.setName("Kilogram");
        measure1.setShortName("kg");

        Measure measure2 = new Measure();
        measure2.setId(2L);
        measure2.setName("Gram");
        measure2.setShortName("g");

        measures.add(measure1);
        measures.add(measure2);

        when(measureRepository.findAll()).thenReturn(measures);

        // Test method
        List<MeasureDTO> result = measureService.getAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Kilogram", result.get(0).getName());
        assertEquals("kg", result.get(0).getShortName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Gram", result.get(1).getName());
        assertEquals("g", result.get(1).getShortName());
    }

    @Test
    void testMapMeasureDTOtoMeasure() {
        // Create MeasureDTO
        MeasureDTO measureDTO = new MeasureDTO();
        measureDTO.setName("Kilogram");
        measureDTO.setShortName("kg");

        // Test method
        MeasureServiceImpl measureService = new MeasureServiceImpl(null);
        Measure result = measureService.mapMeasureDTOtoMeasure(measureDTO);

        // Assertions
        assertEquals("Kilogram", result.getName());
        assertEquals("kg", result.getShortName());
    }

    @Test
    void testMapMeasureToMeasureDTO() {
        // Create Measure
        Measure measure = new Measure();
        measure.setId(1L);
        measure.setName("Kilogram");
        measure.setShortName("kg");

        // Test method
        MeasureServiceImpl measureService = new MeasureServiceImpl(null);
        MeasureDTO result = measureService.mapMeasureToMeasureDTO(measure);

        // Assertions
        assertEquals(1L, result.getId());
        assertEquals("Kilogram", result.getName());
        assertEquals("kg", result.getShortName());
    }
}