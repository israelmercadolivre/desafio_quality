package br.com.mercadolivre.challenge_quality.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
public class PropertyDto {
    @NotNull(message = "O nome da propriedade não pode estar vazio.")
    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "^[A-Z][A-Za-z ]*$" , message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "TO comprimento do nome não pode exceder 30 caracteres.")
    private String prop_name;
    @NotNull(message = "O bairro não pode estar vazio.")
    @NotBlank(message = "O bairro não pode estar vazio.")
    @Size(max = 45, message = "O comprimento do bairro não pode exceder 45 caracteres.")
    private String prop_district;
    @Valid
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
