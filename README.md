# SolarCalc

Aplicação de análise de viabilidade de energia solar. Recebe uma imagem de conta de luz, extrai os dados via IA e retorna opções de sistema solar com custo, retorno financeiro e legislação local.

---

## Pré-requisitos

- Java 17+
- Maven (ou use o `./mvnw` incluído)
- Node.js 18+
- PostgreSQL 16 rodando localmente

---

## Banco de dados

Na primeira vez, crie o banco e o usuário:

```bash
sudo -u postgres psql
```

```sql
CREATE DATABASE solar_calc;
CREATE USER solar_user WITH PASSWORD 'solar123';
GRANT ALL PRIVILEGES ON DATABASE solar_calc TO solar_user;
GRANT ALL ON SCHEMA public TO solar_user;
\q
```

---

## Rodando o backend

```bash
cd /home/gabriel/4tech/solar-calc
./mvnw spring-boot:run
```

O Hibernate cria as tabelas automaticamente na primeira execução.

API disponível em: `http://localhost:8080`

---

## Rodando o frontend

```bash
cd /home/gabriel/4tech/solar-calc-front
npm run dev
```

Interface disponível em: `http://localhost:5173`

---

## Testando o fluxo completo

Com backend e frontend rodando, acesse `http://localhost:5173`, envie qualquer imagem e veja o resultado.

Ou via curl:

```bash
curl -X POST http://localhost:8080/api/analise/upload -F "imagem=@/tmp/teste.jpg"
```

---

## Estrutura do projeto

```
solar-calc/                  # Backend Spring Boot
├── src/main/java/com/example/demo/
│   ├── controller/          # AnaliseController — endpoints REST
│   ├── entity/              # Cliente, ContaDeLuz, AnaliseViabilidade
│   ├── repository/          # Interfaces JPA
│   ├── service/             # ExtracacaoContaService (mock/IA), ViabilidadeService
│   └── dto/                 # DadosContaExtraidos, ResultadoViabilidade
└── src/main/resources/
    └── application.properties

solar-calc-front/            # Frontend React + Vite + Tailwind
└── src/
    ├── App.jsx
    └── pages/
        ├── LandingPage.jsx  # Upload da conta de luz
        └── ResultadoPage.jsx # Opções de sistema solar
```

---

## Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/analise/upload` | Recebe imagem, processa e retorna análise |
| GET | `/api/analise/{id}` | Retorna análise salva por ID |
| GET | `/api/analise/cliente/{id}` | Retorna dados do cliente por ID |

---

## Próximos passos

- [ ] Integrar IA real (Gemini Vision ou OpenAI Vision)
- [ ] Criar frontend de resultado mais detalhado
- [ ] Adicionar geocoding para coordenadas do endereço
- [ ] Deploy no Railway ou Render
