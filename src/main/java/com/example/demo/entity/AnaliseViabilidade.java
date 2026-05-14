package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analises_viabilidade")
public class AnaliseViabilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados solares da localidade (via API INMET ou CRESESB)
    private Double irradiacaoMediaAnual;   // kWh/m²/dia
    private Double potenciaEstimadaKwp;    // capacidade do sistema sugerido

    // Estimativas financeiras
    private Double custoInstalacaoBasico;   // R$
    private Double custoInstalacaoIntermediario;
    private Double custoInstalacaoCompleto;

    private Double economiaAnualEstimada;   // R$
    private Double retornoInvestimentoAnos; // payback em anos

    // Legislação e tributação
    private String legislacaoEstadual;      // resumo da lei local
    private Double aliquotaIcms;            // % ICMS sobre energia
    private Boolean temIncentivosLocais;

    private String observacoes;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "conta_de_luz_id")
    private ContaDeLuz contaDeLuz;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getIrradiacaoMediaAnual() { return irradiacaoMediaAnual; }
    public void setIrradiacaoMediaAnual(Double irradiacaoMediaAnual) { this.irradiacaoMediaAnual = irradiacaoMediaAnual; }

    public Double getPotenciaEstimadaKwp() { return potenciaEstimadaKwp; }
    public void setPotenciaEstimadaKwp(Double potenciaEstimadaKwp) { this.potenciaEstimadaKwp = potenciaEstimadaKwp; }

    public Double getCustoInstalacaoBasico() { return custoInstalacaoBasico; }
    public void setCustoInstalacaoBasico(Double custoInstalacaoBasico) { this.custoInstalacaoBasico = custoInstalacaoBasico; }

    public Double getCustoInstalacaoIntermediario() { return custoInstalacaoIntermediario; }
    public void setCustoInstalacaoIntermediario(Double custoInstalacaoIntermediario) { this.custoInstalacaoIntermediario = custoInstalacaoIntermediario; }

    public Double getCustoInstalacaoCompleto() { return custoInstalacaoCompleto; }
    public void setCustoInstalacaoCompleto(Double custoInstalacaoCompleto) { this.custoInstalacaoCompleto = custoInstalacaoCompleto; }

    public Double getEconomiaAnualEstimada() { return economiaAnualEstimada; }
    public void setEconomiaAnualEstimada(Double economiaAnualEstimada) { this.economiaAnualEstimada = economiaAnualEstimada; }

    public Double getRetornoInvestimentoAnos() { return retornoInvestimentoAnos; }
    public void setRetornoInvestimentoAnos(Double retornoInvestimentoAnos) { this.retornoInvestimentoAnos = retornoInvestimentoAnos; }

    public String getLegislacaoEstadual() { return legislacaoEstadual; }
    public void setLegislacaoEstadual(String legislacaoEstadual) { this.legislacaoEstadual = legislacaoEstadual; }

    public Double getAliquotaIcms() { return aliquotaIcms; }
    public void setAliquotaIcms(Double aliquotaIcms) { this.aliquotaIcms = aliquotaIcms; }

    public Boolean getTemIncentivosLocais() { return temIncentivosLocais; }
    public void setTemIncentivosLocais(Boolean temIncentivosLocais) { this.temIncentivosLocais = temIncentivosLocais; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public ContaDeLuz getContaDeLuz() { return contaDeLuz; }
    public void setContaDeLuz(ContaDeLuz contaDeLuz) { this.contaDeLuz = contaDeLuz; }
}
