package br.com.mercadolivre.challenge_quality.exception;

public class DistrictNotFoundException extends RuntimeException{
    public DistrictNotFoundException(String message) {
        super(message);
    }
}
