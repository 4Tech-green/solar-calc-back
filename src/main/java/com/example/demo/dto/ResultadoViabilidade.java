package com.example.demo.dto;

public class ResultadoViabilidade {

    private Double potenciaEstimadaKwp;
    private Double irradiacaoMediaAnual;

    private Double custoBasico;
    private Double custoIntermediario;
    private Double custoCompleto;

    private Double economiaAnualEstimada;
    private Double paybackAnos;

    private String legislacaoEstadual;
    private Double aliquotaIcms;
    private Boolean temIncentivosLocais;
    private String observacoes;

    // Getters e Setters
    public Double getPotenciaEstimadaKwp() { return potenciaEstimadaKwp; }
    public void setPotenciaEstimadaKwp(Double potenciaEstimadaKwp) { this.potenciaEstimadaKwp = potenciaEstimadaKwp; }

    public Double getIrradiacaoMediaAnual() { return irradiacaoMediaAnual; }
    public void setIrradiacaoMediaAnual(Double irradiacaoMediaAnual) { this.irradiacaoMediaAnual = irradiacaoMediaAnual; }

    public Double getCustoBasico() { return custoBasico; }
    public void setCustoBasico(Double custoBasico) { this.custoBasico = custoBasico; }

    public Double getCustoIntermediario() { return custoIntermediario; }
    public void setCustoIntermediario(Double custoIntermediario) { this.custoIntermediario = custoIntermediario; }

    public Double getCustoCompleto() { return custoCompleto; }
    public void setCustoCompleto(Double custoCompleto) { this.custoCompleto = custoCompleto; }

    public Double getEconomiaAnualEstimada() { return economiaAnualEstimada; }
    public void setEconomiaAnualEstimada(Double economiaAnualEstimada) { this.economiaAnualEstimada = economiaAnualEstimada; }

    public Double getPaybackAnos() { return paybackAnos; }
    public void setPaybackAnos(Double paybackAnos) { this.paybackAnos = paybackAnos; }

    public String getLegislacaoEstadual() { return legislacaoEstadual; }
    public void setLegislacaoEstadual(String legislacaoEstadual) { this.legislacaoEstadual = legislacaoEstadual; }

    public Double getAliquotaIcms() { return aliquotaIcms; }
    public void setAliquotaIcms(Double aliquotaIcms) { this.aliquotaIcms = aliquotaIcms; }

    public Boolean getTemIncentivosLocais() { return temIncentivosLocais; }
    public void setTemIncentivosLocais(Boolean temIncentivosLocais) { this.temIncentivosLocais = temIncentivosLocais; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
