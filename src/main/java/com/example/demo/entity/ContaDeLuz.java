package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contas_de_luz")
public class ContaDeLuz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Caminho do arquivo salvo no servidor
    private String caminhoImagem;

    // Dados extraídos pela IA
    private Double consumoMensalKwh;
    private Double valorMensalReais;
    private String distribuidora;
    private String numeroInstalacao;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne(mappedBy = "contaDeLuz", cascade = CascadeType.ALL)
    private AnaliseViabilidade analise;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCaminhoImagem() { return caminhoImagem; }
    public void setCaminhoImagem(String caminhoImagem) { this.caminhoImagem = caminhoImagem; }

    public Double getConsumoMensalKwh() { return consumoMensalKwh; }
    public void setConsumoMensalKwh(Double consumoMensalKwh) { this.consumoMensalKwh = consumoMensalKwh; }

    public Double getValorMensalReais() { return valorMensalReais; }
    public void setValorMensalReais(Double valorMensalReais) { this.valorMensalReais = valorMensalReais; }

    public String getDistribuidora() { return distribuidora; }
    public void setDistribuidora(String distribuidora) { this.distribuidora = distribuidora; }

    public String getNumeroInstalacao() { return numeroInstalacao; }
    public void setNumeroInstalacao(String numeroInstalacao) { this.numeroInstalacao = numeroInstalacao; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public AnaliseViabilidade getAnalise() { return analise; }
    public void setAnalise(AnaliseViabilidade analise) { this.analise = analise; }
}
