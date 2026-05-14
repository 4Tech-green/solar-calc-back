package com.example.demo.service;

import com.example.demo.dto.DadosContaExtraidos;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class ExtracacaoContaService {

    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String MODEL = "google/gemma-4-31b-it:free";

    private static final String PROMPT = """
            Analise esta conta de luz brasileira e extraia os dados abaixo em JSON.
            Retorne APENAS o objeto JSON, sem markdown, sem explicações.
            Use null para campos não encontrados. Campos numéricos devem ser números, não strings.
            {
              "nome": "nome completo do titular",
              "cpf": "CPF formatado ou null",
              "telefone": "telefone ou null",
              "logradouro": "rua/avenida",
              "numero": "número do endereço",
              "bairro": "bairro",
              "cidade": "cidade",
              "estado": "sigla UF com 2 letras",
              "cep": "CEP ou null",
              "distribuidora": "nome da distribuidora de energia",
              "numeroInstalacao": "número de instalação ou null",
              "consumoMensalKwh": 0.0,
              "valorMensalReais": 0.0
            }
            """;

    @Value("${openrouter.api.key:}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DadosContaExtraidos extrair(MultipartFile arquivo) {
        if (apiKey == null || apiKey.isBlank()) {
            return mockCPFLPaulista();
        }
        try {
            return extrairComOpenRouter(arquivo);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair dados com IA: " + e.getMessage(), e);
        }
    }

    private DadosContaExtraidos extrairComOpenRouter(MultipartFile arquivo) throws Exception {
        String mimeType = arquivo.getContentType() != null ? arquivo.getContentType() : "image/jpeg";
        String base64 = Base64.getEncoder().encodeToString(arquivo.getBytes());
        String dataUrl = "data:" + mimeType + ";base64," + base64;

        Map<String, Object> partTexto = Map.of("type", "text", "text", PROMPT);
        Map<String, Object> partImagem = Map.of("type", "image_url", "image_url", Map.of("url", dataUrl));

        Map<String, Object> message = Map.of("role", "user", "content", List.of(partTexto, partImagem));
        Map<String, Object> body = Map.of("model", MODEL, "messages", List.of(message));

        String resposta = restClient.post()
                .uri(OPENROUTER_URL)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(String.class);

        JsonNode root = objectMapper.readTree(resposta);
        String jsonExtraido = root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        // Remove markdown caso o modelo envolva em ```json ... ```
        jsonExtraido = jsonExtraido.replaceAll("(?s)```json\\s*|```", "").trim();

        return mapearDados(objectMapper.readTree(jsonExtraido));
    }

    private DadosContaExtraidos mapearDados(JsonNode node) {
        DadosContaExtraidos dados = new DadosContaExtraidos();
        dados.setNome(texto(node, "nome"));
        dados.setCpf(texto(node, "cpf"));
        dados.setTelefone(texto(node, "telefone"));
        dados.setLogradouro(texto(node, "logradouro"));
        dados.setNumero(texto(node, "numero"));
        dados.setBairro(texto(node, "bairro"));
        dados.setCidade(texto(node, "cidade"));
        dados.setEstado(texto(node, "estado"));
        dados.setCep(texto(node, "cep"));
        dados.setDistribuidora(texto(node, "distribuidora"));
        dados.setNumeroInstalacao(texto(node, "numeroInstalacao"));
        dados.setConsumoMensalKwh(node.path("consumoMensalKwh").asDouble(0));
        dados.setValorMensalReais(node.path("valorMensalReais").asDouble(0));
        return dados;
    }

    private String texto(JsonNode node, String campo) {
        JsonNode valor = node.path(campo);
        return (valor.isNull() || valor.isMissingNode()) ? null : valor.asText();
    }

    // Fallback local — ativo quando gemini.api.key não está configurada
    private DadosContaExtraidos mockCPFLPaulista() {
        DadosContaExtraidos dados = new DadosContaExtraidos();
        dados.setNome("João Silva Santos");
        dados.setCpf("123.456.789-00");
        dados.setTelefone("(15) 99123-4567");
        dados.setLogradouro("Rua das Paineiras");
        dados.setNumero("245");
        dados.setBairro("Jardim Paulistano");
        dados.setCidade("Sorocaba");
        dados.setEstado("SP");
        dados.setCep("18040-000");
        dados.setDistribuidora("CPFL Paulista");
        dados.setNumeroInstalacao("7001234567");
        dados.setConsumoMensalKwh(320.0);
        dados.setValorMensalReais(285.60);
        return dados;
    }
}
