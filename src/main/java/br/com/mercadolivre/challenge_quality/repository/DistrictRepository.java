package br.com.mercadolivre.challenge_quality.repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class DistrictRepository implements Repository<String> {
    private Map<String, Double> districts = new HashMap<>();

    {

        districts.put("ALPHAVILLE", 900d);
        districts.put("BELEM", 500d);
        districts.put("VILA OLIMPIA", 700d);
    }

    @Override
    public Optional<Map.Entry<String, Double>> findByName(String name) {
       return this.districts.entrySet().stream().filter(d -> d.getKey().equalsIgnoreCase(name)).findFirst();
    }
}
