package br.com.mercadolivre.challenge_quality.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<T> {
    public Optional<Map.Entry<String, Double>> findByName(String name);
}
