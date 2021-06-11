package br.com.mercadolivre.challenge_quality.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Validated
public class RoomDto {
    @NotNull(message = "O campo não pode estar vazio.")
    @NotBlank(message = "O campo não pode estar vazio.")
    @Pattern(regexp = "^[A-Z][A-Za-z ]*$" , message = "O nome do cômodo deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do cômodo não pode exceder 30 caracteres.")
    private String room_name;
    @NotNull(message = "A largura do cômodo não pode estar vazia")
    @Range(min = 1, max = 25, message = "A largura máxima permitida por cômodo é de 25 metros")
    private Double room_width;
    @NotNull(message = "O comprimento do cômodo não pode estar vazio.")
    @Range(min = 1, max = 33, message = "O comprimento máximo permitido por cômodo é de 33 metros.")
    private Double room_length;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double squareMeter;

    public RoomDto() {
    }

    public RoomDto(String room_name, Double room_length, Double room_width) {
        this.room_name = room_name;
        this.room_length = room_length;
        this.room_width = room_width;
        this.squareMeter = this.calculateSquareMeter();
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Double getRoom_width() {
        return room_width;
    }

    public void setRoom_width(Double room_width) {
        this.room_width = room_width;
    }

    public Double getRoom_length() {
        return room_length;
    }

    public void setRoom_length(Double room_length) {
        this.room_length = room_length;
    }

    public Double getSquareMeter() {
        return squareMeter;
    }

    public Double calculateSquareMeter(){
        return this.getRoom_width() * this.getRoom_length();
    }
}
