package br.com.mercadolivre.challenge_quality.service;

import br.com.mercadolivre.challenge_quality.dto.PropertyDto;
import br.com.mercadolivre.challenge_quality.dto.RoomDto;
import br.com.mercadolivre.challenge_quality.dto.TotSquareMeter;
import br.com.mercadolivre.challenge_quality.exception.DistrictNotFoundException;
import br.com.mercadolivre.challenge_quality.repository.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class PropertyService {
    private static final String NOT_FOUND_DISTRICT = "Not found district [%s]";
    private DistrictRepository districtRepository;

    public PropertyService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public TotSquareMeter calculateSquareMeter(PropertyDto propertyDto){
        this.verifyDistrict(propertyDto.getProp_district());
        TotSquareMeter dto = new TotSquareMeter();

        Double totCalculateSquareMeter = getTotCalculateSquareMeter(propertyDto);

        dto.setName(propertyDto.getProp_name());
        dto.setTotalMetroQuadrado(totCalculateSquareMeter);

        return dto;
    }

    public TotSquareMeter calculateValueProperty(PropertyDto propertyDto){
        Map.Entry<String, Double> district =  this.verifyDistrict(propertyDto.getProp_district());
        TotSquareMeter dto = new TotSquareMeter();

        Double totCalculateSquareMeter = getTotCalculateSquareMeter(propertyDto);

        dto.setName(propertyDto.getProp_name());
        dto.setTotalMetroQuadrado(totCalculateSquareMeter);
        dto.setTotalMetroQuadrado(totCalculateSquareMeter * district.getValue());

        return dto;
    }

    private Double getTotCalculateSquareMeter(PropertyDto propertyDto) {
        return propertyDto.getRooms().stream()
                .map(RoomDto::calculateSquareMeter)
                .reduce(Double::sum)
                .get();
    }


    private Map.Entry<String, Double> verifyDistrict(String district){
        return  this.districtRepository.findByName(district)
                .orElseThrow(()->  new DistrictNotFoundException(String.format(NOT_FOUND_DISTRICT, district)));
    }

    public RoomDto biggerRoom(PropertyDto propertyDto) {
        this.verifyDistrict(propertyDto.getProp_district());
        return propertyDto.getRooms().stream()
                .max(Comparator.comparing(RoomDto::calculateSquareMeter))
                .get();
    }

    public List<RoomDto> calculateSquareMeterRoom(PropertyDto propertyDto) {
        List<RoomDto> dto = new ArrayList<>();
        this.verifyDistrict(propertyDto.getProp_district());
        propertyDto.getRooms().stream().forEach(r -> dto.add(
                new RoomDto(
                        r.getRoom_name(),
                        r.getRoom_length(), r.getRoom_width())));
        return dto;
    }
}
