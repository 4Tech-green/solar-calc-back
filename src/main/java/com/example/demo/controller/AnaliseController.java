package com.example.demo.controller;

import com.example.demo.dto.DadosContaExtraidos;
import com.example.demo.dto.ResultadoViabilidade;
import com.example.demo.entity.AnaliseViabilidade;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.ContaDeLuz;
import com.example.demo.repository.AnaliseViabilidadeRepository;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ContaDeLuzRepository;
import com.example.demo.service.ExtracacaoContaService;
import com.example.demo.service.ViabilidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/analise")
@CrossOrigin(origins = "*")
public class AnaliseController {

    private final ClienteRepository clienteRepository;
    private final ContaDeLuzRepository contaDeLuzRepository;
    private final AnaliseViabilidadeRepository analiseRepository;
    private final ExtracacaoContaService extracacaoService;
    private final ViabilidadeService viabilidadeService;

    private static final String UPLOAD_DIR = "uploads/";

    public AnaliseController(ClienteRepository clienteRepository,
                             ContaDeLuzRepository contaDeLuzRepository,
                             AnaliseViabilidadeRepository analiseRepository,
                             ExtracacaoContaService extracacaoService,
                             ViabilidadeService viabilidadeService) {
        this.clienteRepository = clienteRepository;
        this.contaDeLuzRepository = contaDeLuzRepository;
        this.analiseRepository = analiseRepository;
        this.extracacaoService = extracacaoService;
        this.viabilidadeService = viabilidadeService;
    }

    // POST /api/analise/upload
    // Recebe imagem, extrai dados, calcula viabilidade e persiste tudo
    @PostMapping("/upload")
    public ResponseEntity<AnaliseResponse> upload(@RequestParam("imagem") MultipartFile imagem) throws IOException {
        // 1. Salva imagem
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        Path caminho = Paths.get(UPLOAD_DIR + nomeArquivo);
        Files.write(caminho, imagem.getBytes());

        // 2. Extrai dados via IA (ou mock)
        DadosContaExtraidos dados = extracacaoService.extrair(imagem);

        // 3. Calcula viabilidade
        ResultadoViabilidade resultado = viabilidadeService.calcular(dados);

        // 4. Persiste cliente
        Cliente cliente = new Cliente();
        cliente.setNome(dados.getNome());
        cliente.setCpf(dados.getCpf());
        cliente.setTelefone(dados.getTelefone());
        cliente.setLogradouro(dados.getLogradouro());
        cliente.setNumero(dados.getNumero());
        cliente.setBairro(dados.getBairro());
        cliente.setCidade(dados.getCidade());
        cliente.setEstado(dados.getEstado());
        cliente.setCep(dados.getCep());
        clienteRepository.save(cliente);

        // 5. Persiste conta de luz
        ContaDeLuz conta = new ContaDeLuz();
        conta.setCaminhoImagem(nomeArquivo);
        conta.setConsumoMensalKwh(dados.getConsumoMensalKwh());
        conta.setValorMensalReais(dados.getValorMensalReais());
        conta.setDistribuidora(dados.getDistribuidora());
        conta.setNumeroInstalacao(dados.getNumeroInstalacao());
        conta.setCliente(cliente);
        contaDeLuzRepository.save(conta);

        // 6. Persiste análise
        AnaliseViabilidade analise = new AnaliseViabilidade();
        analise.setIrradiacaoMediaAnual(resultado.getIrradiacaoMediaAnual());
        analise.setPotenciaEstimadaKwp(resultado.getPotenciaEstimadaKwp());
        analise.setCustoInstalacaoBasico(resultado.getCustoBasico());
        analise.setCustoInstalacaoIntermediario(resultado.getCustoIntermediario());
        analise.setCustoInstalacaoCompleto(resultado.getCustoCompleto());
        analise.setEconomiaAnualEstimada(resultado.getEconomiaAnualEstimada());
        analise.setRetornoInvestimentoAnos(resultado.getPaybackAnos());
        analise.setLegislacaoEstadual(resultado.getLegislacaoEstadual());
        analise.setAliquotaIcms(resultado.getAliquotaIcms());
        analise.setTemIncentivosLocais(resultado.getTemIncentivosLocais());
        analise.setObservacoes(resultado.getObservacoes());
        analise.setContaDeLuz(conta);
        analiseRepository.save(analise);

        // 7. Retorna tudo para o frontend
        return ResponseEntity.ok(new AnaliseResponse(cliente.getId(), analise.getId(), dados, resultado));
    }

    // GET /api/analise/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AnaliseViabilidade> getAnalise(@PathVariable Long id) {
        return analiseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Record interno para resposta do upload
    public record AnaliseResponse(
        Long clienteId,
        Long analiseId,
        DadosContaExtraidos dadosConta,
        ResultadoViabilidade viabilidade
    ) {}
}
