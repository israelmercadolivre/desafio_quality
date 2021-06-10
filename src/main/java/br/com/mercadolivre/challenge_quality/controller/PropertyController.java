package br.com.mercadolivre.challenge_quality.controller;

import br.com.mercadolivre.challenge_quality.dto.PropertyDto;
import br.com.mercadolivre.challenge_quality.dto.RoomDto;
import br.com.mercadolivre.challenge_quality.dto.TotSquareMeter;
import br.com.mercadolivre.challenge_quality.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    //USS 001
    @PostMapping("/areaTotalCasa")
    public ResponseEntity calculateSquareMeter(@Valid @RequestBody PropertyDto propertyDto){
        TotSquareMeter dto = propertyService.calculateSquareMeter(propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //USS 002
    @PostMapping("/valor")
    public ResponseEntity calculateValue(@Valid @RequestBody PropertyDto propertyDto){
        TotSquareMeter dto = propertyService.calculateValueProperty(propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //USS 003
    @PostMapping("/maiorComodo")
    public ResponseEntity biggerRoom(@Valid @RequestBody PropertyDto propertyDto){
        RoomDto dto = propertyService.biggerRoom(propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //USS 004
    @PostMapping("/areaTotalComodo")
    public ResponseEntity calculateSquareMeterRoom(@Valid @RequestBody PropertyDto propertyDto){
        List<RoomDto> dto = propertyService.calculateSquareMeterRoom(propertyDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
