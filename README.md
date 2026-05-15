# SolarCalc — Backend

API REST de análise de viabilidade de energia solar. Recebe uma imagem de conta de luz, extrai os dados via IA e retorna opções de sistema solar com custo, retorno financeiro e legislação local.

**Deploy:** https://solar-calc-back.onrender.com  
**Frontend:** https://solar-calc-front.vercel.app

---

## Pré-requisitos

- Java 21+
- Maven (ou use o `./mvnw` incluído no projeto)
- PostgreSQL 16 rodando localmente

---

## Configuração do banco de dados

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

## Clonando e rodando

```bash
git clone https://github.com/4Tech-green/solar-calc-back.git
cd solar-calc-back
./mvnw spring-boot:run
```

API disponível em: `http://localhost:8080`

O Hibernate cria as tabelas automaticamente na primeira execução.

---

## Variáveis de ambiente (opcional)

Por padrão o projeto usa o banco local configurado acima. Para usar outras configurações, exporte as variáveis antes de rodar:

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/solar_calc
export DATABASE_USERNAME=solar_user
export DATABASE_PASSWORD=solar123
export OPENROUTER_API_KEY=sua_chave_aqui   # obtenha grátis em openrouter.ai
```

Sem a `OPENROUTER_API_KEY` o sistema roda com dados mockados (CPFL Paulista / Sorocaba-SP).

---

## Estrutura do projeto

```
src/main/java/com/example/demo/
├── controller/    # AnaliseController — endpoints REST
├── entity/        # Cliente, ContaDeLuz, AnaliseViabilidade
├── repository/    # Interfaces JPA
├── service/       # ExtracacaoContaService (IA/mock), ViabilidadeService
└── dto/           # DadosContaExtraidos, ResultadoViabilidade
```

---

## Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/analise/upload` | Recebe imagem, processa e retorna análise completa |
| GET | `/api/analise/{id}` | Retorna análise salva por ID |
