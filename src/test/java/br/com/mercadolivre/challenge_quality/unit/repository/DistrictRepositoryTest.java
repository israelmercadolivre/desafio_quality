package br.com.mercadolivre.challenge_quality.unit.repository;

import br.com.mercadolivre.challenge_quality.repository.DistrictRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DistrictRepositoryTest {

    private static DistrictRepository districtRepository;

    @BeforeAll
    public static void setUp(){
        districtRepository = new DistrictRepository();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Vila Olimpia", "belem", "ALPHAVILLE"})
    public void shouldFindDistrictWithNamesValid(String name){
        Optional<Map.Entry<String, Double>> district = districtRepository.findByName(name);
        assertNotNull(district);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Belim", "Diadema", "Santo Andre"})
    public void shouldNotFindDistrictWithNamesInvalid(String name){
        Optional<Map.Entry<String, Double>> district = districtRepository.findByName(name);
        assertTrue(district.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldNotFindDistrictWithValueNullAndEmpty(String name){
        Optional<Map.Entry<String, Double>> district = districtRepository.findByName(name);
        assertTrue(district.isEmpty());

    }
}