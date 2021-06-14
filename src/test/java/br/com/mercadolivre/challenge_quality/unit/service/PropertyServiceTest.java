package br.com.mercadolivre.challenge_quality.unit.service;

import br.com.mercadolivre.challenge_quality.dto.PropertyDto;
import br.com.mercadolivre.challenge_quality.dto.RoomDto;
import br.com.mercadolivre.challenge_quality.dto.TotSquareMeter;
import br.com.mercadolivre.challenge_quality.exception.DistrictNotFoundException;
import br.com.mercadolivre.challenge_quality.repository.DistrictRepository;
import br.com.mercadolivre.challenge_quality.service.PropertyService;
import br.com.mercadolivre.challenge_quality.util.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static br.com.mercadolivre.challenge_quality.util.Util.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PropertyServiceTest {


    private static PropertyService service;
    @Mock
    private static DistrictRepository repository;

    private static PropertyDto dto;

    @BeforeAll
    public static void setUp(){
        dto = Util.generatePropertyDtoValid();
        repository = mock(DistrictRepository.class);
        service = new PropertyService(repository);
    }

    @BeforeEach
    public void mockRepository(){
        Map.Entry<String, Double> district = mock(Map.Entry.class);
        when(district.getValue()).thenReturn(700d);
        Mockito.when(repository.findByName(dto.getProp_district())).thenReturn(Optional.of(district));
    }

    @Test
    public void shouldNotCalculateSquareMeterWithDistrictInvalid(){
        PropertyDto dto = new PropertyDto();
        String districtInvalid = "Belim";
        dto.setProp_district(districtInvalid);
        Mockito.when(repository.findByName(districtInvalid)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DistrictNotFoundException.class, () -> {
            service.calculateSquareMeter(dto);
        });

        String expectedMessage = "Not found district ["+districtInvalid+"]";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldCalculateSquareMeterCorrectly(){
        TotSquareMeter result = service.calculateSquareMeter(dto);
        assertEquals(dto.getProp_name(), result.getName());
        assertEquals(445d, result.getTotSquareMeter());
    }

    @Test
    public void shouldCalculateValuePropertyCorrectly(){

        TotSquareMeter result = service.calculateValueProperty(dto);
        assertEquals(dto.getProp_name(), result.getName());
        assertEquals(311500d, result.getValorTotal().doubleValue());
    }

    @Test
    public void shouldReturnBiggerRoomCorrectly(){
        RoomDto result = service.biggerRoom(dto);
        assertEquals("Sala", result.getRoom_name());
        assertEquals(11, result.getRoom_width());
        assertEquals(20, result.getRoom_length());
    }

    @Test
    public void shouldCalculateSquareMeterRoomCorrectly(){
        List<RoomDto> result = service.calculateSquareMeterRoom(dto);
        assertEquals(SALA, result.get(0).getRoom_name());
        assertEquals(SALA_WIDTH, result.get(0).getRoom_width());
        assertEquals(SALA_LENGTH, result.get(0).getRoom_length());
        assertEquals(220, result.get(0).getSquareMeter());

        assertEquals(COZINHA, result.get(1).getRoom_name());
        assertEquals(COZINHA_WIDTH, result.get(1).getRoom_width());
        assertEquals(COZINHA_LENGTH, result.get(1).getRoom_length());
        assertEquals(165, result.get(1).getSquareMeter());

        assertEquals(BANHEIRO, result.get(2).getRoom_name());
        assertEquals(BANHEIRO_WIDTH, result.get(2).getRoom_width());
        assertEquals(BANHEIRO_LENGTH, result.get(2).getRoom_length());
        assertEquals(60, result.get(2).getSquareMeter());
    }

}