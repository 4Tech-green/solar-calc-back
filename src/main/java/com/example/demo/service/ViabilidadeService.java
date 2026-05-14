package com.example.demo.service;

import com.example.demo.dto.DadosContaExtraidos;
import com.example.demo.dto.ResultadoViabilidade;
import org.springframework.stereotype.Service;

@Service
public class ViabilidadeService {

    // Irradiação solar média de Sorocaba/SP — fonte: CRESESB/INMET
    private static final double IRRADIACAO_SOROCABA = 4.8; // kWh/m²/dia
    private static final double EFICIENCIA_SISTEMA = 0.80; // 80%
    private static final double CUSTO_POR_KWP = 4500.0;    // R$ médio mercado 2024
    private static final double TARIFA_CPFL = 0.89;        // R$/kWh CPFL Paulista 2024

    public ResultadoViabilidade calcular(DadosContaExtraidos dados) {
        double consumoKwh = dados.getConsumoMensalKwh();

        // Potência necessária do sistema (kWp)
        double potenciaKwp = consumoKwh / (IRRADIACAO_SOROCABA * 30 * EFICIENCIA_SISTEMA);

        // Três opções de sistema
        double kwpBasico = potenciaKwp * 0.75;       // cobre 75% do consumo
        double kwpIntermediario = potenciaKwp;        // cobre 100%
        double kwpCompleto = potenciaKwp * 1.25;     // cobre 125% (gera crédito)

        double custoBasico = kwpBasico * CUSTO_POR_KWP;
        double custoIntermediario = kwpIntermediario * CUSTO_POR_KWP;
        double custoCompleto = kwpCompleto * CUSTO_POR_KWP;

        double economiaAnual = consumoKwh * 12 * TARIFA_CPFL;
        double payback = custoIntermediario / economiaAnual;

        ResultadoViabilidade resultado = new ResultadoViabilidade();

        resultado.setPotenciaEstimadaKwp(round(kwpIntermediario));
        resultado.setIrradiacaoMediaAnual(IRRADIACAO_SOROCABA);

        resultado.setCustoBasico(round(custoBasico));
        resultado.setCustoIntermediario(round(custoIntermediario));
        resultado.setCustoCompleto(round(custoCompleto));

        resultado.setEconomiaAnualEstimada(round(economiaAnual));
        resultado.setPaybackAnos(round(payback));

        // Legislação SP — Lei 17.557/2021 e resolução ANEEL 1059/2023
        resultado.setLegislacaoEstadual("SP — Lei 17.557/2021: isenção de ICMS sobre energia gerada e injetada na rede.");
        resultado.setAliquotaIcms(0.0); // isento para micro/minigeração
        resultado.setTemIncentivosLocais(true);
        resultado.setObservacoes("Sorocaba possui alto índice de irradiação. Retorno médio estimado entre 4 e 6 anos.");

        return resultado;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
