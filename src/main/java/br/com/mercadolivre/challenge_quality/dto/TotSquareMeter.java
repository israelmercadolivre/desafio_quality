package br.com.mercadolivre.challenge_quality.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotSquareMeter {
    private String name;
    private Double totSquareMeter;
    private BigDecimal valorTotal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalMetroQuadrado(Double totalMetroQuadrado) {
        this.totSquareMeter = totalMetroQuadrado;
    }

    public Double getTotSquareMeter() {
        return totSquareMeter;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
