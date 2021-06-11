package br.com.mercadolivre.challenge_quality.util;

import br.com.mercadolivre.challenge_quality.dto.PropertyDto;
import br.com.mercadolivre.challenge_quality.dto.RoomDto;

import java.util.Arrays;

public class Util {
    public static final String SALA = "Sala";
    public static final String COZINHA = "Cozinha";
    public static final String BANHEIRO = "Banheiro";
    public static final double SALA_LENGTH = 20d;
    public static final double SALA_WIDTH = 11d;
    public static final double COZINHA_LENGTH = 15d;
    public static final double COZINHA_WIDTH = 11d;
    public static final double BANHEIRO_LENGTH = 10d;
    public static final double BANHEIRO_WIDTH = 6d;

    public static PropertyDto generatePropertyDtoValid(){
        PropertyDto dto = new PropertyDto();
        dto.setProp_name("Casa Teste");
        dto.setProp_district("VILA OLIMPIA");

        RoomDto roomDto1 = new RoomDto(SALA, SALA_LENGTH, SALA_WIDTH);
        RoomDto roomDto2 = new RoomDto(COZINHA, COZINHA_LENGTH, COZINHA_WIDTH);
        RoomDto roomDto3 = new RoomDto(BANHEIRO, BANHEIRO_LENGTH, BANHEIRO_WIDTH);
        dto.setRooms(Arrays.asList(roomDto1, roomDto2, roomDto3));

        return dto;
    }
}
