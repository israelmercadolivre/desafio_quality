package br.com.mercadolivre.challenge_quality.integration.controller;

import br.com.mercadolivre.challenge_quality.controller.PropertyController;
import br.com.mercadolivre.challenge_quality.dto.PropertyDto;
import br.com.mercadolivre.challenge_quality.dto.RoomDto;
import br.com.mercadolivre.challenge_quality.dto.TotSquareMeter;
import br.com.mercadolivre.challenge_quality.repository.DistrictRepository;
import br.com.mercadolivre.challenge_quality.service.PropertyService;
import br.com.mercadolivre.challenge_quality.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static br.com.mercadolivre.challenge_quality.util.Util.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {PropertyController.class, PropertyService.class, DistrictRepository.class})
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PropertyDto dto;

    @BeforeEach
    public void setUp() {
        dto = Util.generatePropertyDtoValid();
    }

    @ParameterizedTest
    @NullSource
    public void shouldNotCalculateSquareMeterWithNullPayload(PropertyDto propertyDto) throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @NullSource
    public void shouldNotCalculateSquareMeterWithNamePropertyNull(String nameProperty) throws Exception {
        dto.setProp_name(nameProperty);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O nome da propriedade nao pode estar vazio.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"casa Teste", "propiedade", "cONdomINIO"})
    public void shouldNotCalculateSquareMeterWithNamePropertyWithoutFirstLetterLowerCase(String nameProperty) throws Exception {
        dto.setProp_name(nameProperty);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O nome da propriedade deve comecar com uma letra maiuscula.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Casa Teste Casa Teste Casa Teste Casa Teste Casa Teste Casa Teste"})
    public void shouldNotCalculateSquareMeterWithNamePropertyOverTheLimit(String nameProperty) throws Exception {
        dto.setProp_name(nameProperty);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O comprimento do nome nao pode exceder 30 caracteres.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Belim", "Diadema", "Santo Andre"})
    public void shouldNotCalculateSquareMeterWithDistrictInvalid(String district) throws Exception {
        dto.setProp_district(district);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("Not found district [" + district + "]")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldNotCalculateSquareMeterWithDistrictNullAndEmpty(String district) throws Exception {
        dto.setProp_district(district);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O bairro nao pode estar vazio.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Belim Diadema Santos Santo Andre Vila Olimpia Belim Diadema Santos Santo Andre Vila Olimpia"})
    public void shouldNotCalculateSquareMeterWithDistrictOverTheLimit(String district) throws Exception {
        dto.setProp_district(district);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O comprimento do bairro nao pode exceder 45 caracteres.")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldNotCalculateSquareMeterWithNullRooms(List<RoomDto> roomDto) throws Exception {
        dto.setRooms(roomDto);
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("Rooms nao pode estar vazio.")));

    }

    @ParameterizedTest
    @NullSource
    public void shouldNotCalculateSquareMeterWithNameRoomNull(String nameRoom) throws Exception {
        dto.setRooms(Arrays.asList(new RoomDto(nameRoom, 10d, 10d)));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O nome do comodo nao pode estar vazio.")));

    }

    @ParameterizedTest
    @ValueSource(strings = {"Sala cozinha quarto Sala cozinha quarto Sala cozinha quarto Sala cozinha quarto Sala cozinha quarto Sala cozinha quarto"})
    public void shouldNotCalculateSquareMeterWithNameRoomOverTheLimit(String nameRoom) throws Exception {
        dto.setRooms(Arrays.asList(new RoomDto(nameRoom, 10d, 10d)));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O comprimento do comodo nao pode exceder 30 caracteres.")));

    }

    @ParameterizedTest
    @ValueSource(strings = {"quarto", "cozinha", "sala"})
    public void shouldNotCalculateSquareMeterWithNameRoomLowerCase(String nameRoom) throws Exception {
        dto.setRooms(Arrays.asList(new RoomDto(nameRoom, 10d, 10d)));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O nome do comodo deve comecar com uma letra maiuscula.")));
    }

    @ParameterizedTest
    @CsvSource({"0d, 0d", "-10d, -20d", "26d, 34d"})
    public void shouldNotCalculateSquareMeterWithWidthAndLengthInvalid(Double width, Double length) throws Exception {
        dto.setRooms(Arrays.asList(new RoomDto(SALA, length, width)));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("A largura maxima permitida por comodo e de 25 metros.")))
                .andExpect(content().string(Matchers.containsString("O comprimento maximo permitido por comodo e de 33 metros.")));
    }

    @ParameterizedTest
    @NullSource
    public void shouldNotCalculateSquareMeterWithValueNull(Double value) throws Exception {
        dto.setRooms(Arrays.asList(new RoomDto(SALA, value, value)));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.containsString("O comprimento do comodo nao pode estar vazio.")))
                .andExpect(content().string(Matchers.containsString("A largura do comodo nao pode estar vazia.")));
    }

    @Test
    public void shouldCalculateSquareMeterCorrectly() throws Exception {
        TotSquareMeter result = new TotSquareMeter();
        result.setTotalMetroQuadrado(445d);
        result.setName(dto.getProp_name());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalCasa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString(objectMapper.writeValueAsString(result))));
    }

    @Test
    public void shouldCalculateValuePropertyCorrectly() throws Exception {
        TotSquareMeter result = new TotSquareMeter();
        result.setTotalMetroQuadrado(445d);
        result.setValorTotal(new BigDecimal(311500d));
        result.setName(dto.getProp_name());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/valor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString(objectMapper.writeValueAsString(result))));
    }

    @Test
    public void shouldReturnBiggerRoomCorrectly() throws Exception {
        RoomDto roomDto1 = new RoomDto(SALA, SALA_LENGTH, SALA_WIDTH);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/maiorComodo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString(objectMapper.writeValueAsString(roomDto1))));
    }

    @Test
    public void shouldCalculateCorrectly() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/areaTotalComodo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString(objectMapper.writeValueAsString(dto.getRooms()))));
    }
}