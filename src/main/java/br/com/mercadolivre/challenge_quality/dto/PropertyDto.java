package br.com.mercadolivre.challenge_quality.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Validated
public class PropertyDto {
    @NotNull(message = "O nome da propriedade nao pode estar vazio.")
    @NotBlank(message = "O nome da propriedade nao pode estar vazio.")
    @Size(max = 30, message = "O comprimento do nome nao pode exceder 30 caracteres.")
    @Pattern(regexp = "^[A-Z][A-Za-z ]*$" , message = "O nome da propriedade deve comecar com uma letra maiuscula.")
    private String prop_name;

    @NotNull(message = "O bairro nao pode estar vazio.")
    @NotBlank(message = "O bairro nao pode estar vazio.")
    @Size(max = 45, message = "O comprimento do bairro nao pode exceder 45 caracteres.")
    private String prop_district;

    @Valid
    @NotNull(message = "Rooms nao pode estar vazio.")
    @NotEmpty(message = "Rooms nao pode estar vazio.")
    private List<RoomDto> rooms;

    public String getProp_name() {
        return prop_name;
    }

    public void setProp_name(String prop_name) {
        this.prop_name = prop_name;
    }

    public String getProp_district() {
        return prop_district;
    }

    public void setProp_district(String prop_district) {
        this.prop_district = prop_district;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }
}
