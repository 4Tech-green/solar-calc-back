package com.example.demo.dto;

public class DadosContaExtraidos {

    private String nome;
    private String cpf;
    private String telefone;

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private String distribuidora;
    private String numeroInstalacao;
    private Double consumoMensalKwh;
    private Double valorMensalReais;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getDistribuidora() { return distribuidora; }
    public void setDistribuidora(String distribuidora) { this.distribuidora = distribuidora; }

    public String getNumeroInstalacao() { return numeroInstalacao; }
    public void setNumeroInstalacao(String numeroInstalacao) { this.numeroInstalacao = numeroInstalacao; }

    public Double getConsumoMensalKwh() { return consumoMensalKwh; }
    public void setConsumoMensalKwh(Double consumoMensalKwh) { this.consumoMensalKwh = consumoMensalKwh; }

    public Double getValorMensalReais() { return valorMensalReais; }
    public void setValorMensalReais(Double valorMensalReais) { this.valorMensalReais = valorMensalReais; }
}
