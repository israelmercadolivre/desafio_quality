package br.com.mercadolivre.challenge_quality.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private HttpStatus status;
    private LocalDate date;
    private Map<String, String> messages;
    private String message;
    private String description;

    public ErrorMessage(HttpStatus status, LocalDate date, Map<String, String> messages, String description) {
        this.status = status;
        this.date = date;
        this.messages = messages;
        this.description = description;
    }

    public ErrorMessage(HttpStatus status, LocalDate date, String message, String description) {
        this.status = status;
        this.date = date;
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
