# API de Transferências Bancárias Internas


---

## Tecnologias Utilizadas
- Java 23
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (em memória)
- Gradle
- Junit
- Mockito
- Reflection

---

## Como Executar Localmente

### 1. Clone o repositório

```bash
git clone https://github.com/seubanco/api-transferencias.git
cd api-transferencias
```

### 2. Rode a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

### 3. Console do H2

* URL: http://localhost:8080/h2-console
* JDBC URL: jdbc:h2:mem:bankdb
* User: sa
* Password: (em branco)

## Endpoints

### 1. Post /transferencias

Realiza uma nova transferência entre contas internas.

Request Body:
```json
{
  "contaOrigem": "123456",
  "contaDestino": "654321",
  "valor": 100.00
}
```

Response:
```json
{
  "id": 1,
  "contaOrigem": "123456",
  "contaDestino": "654321",
  "valor": 100.00,
  "dataTransferencia": "2023-10-01T12:00:00"
}
```

### 2. Get /transferencias/{id}

Retorna os detalhes de uma transferência específica.
Request:
```http
GET /transferencias/1
```
Response:
```json
{
  "id": 1,
  "contaOrigem": "123456",
  "contaDestino": "654321",
  "valor": 100.00,
  "dataTransferencia": "2023-10-01T12:00:00"
}
```

## Dados Iniciais no Banco (H2)

Ao subir a aplicação, duas contas fictícias são cadastradas automaticamente (via `data.sql` ou script Java opcional):

| Número | Conta | Titular        | Saldo Inicial |
|--------|-------|----------------|---------------|
| 1      | 1234  | João Silva     | 1000.00       |
| 2      | 5678  | Maria Oliveira | 2000.00       |

## Objetivo do Workshop

Este projeto foi criado para exercitar:

* Escrita de testes unitários (JUnit, Mockito)
* Validação de regras de negócio (saldo, valores, contas)

## Equipe

* Time Alice* 
* Instrutores/Facilitadores: Renato Dal Zot, Emanuel Bispo Curralinho, Matheus de Almeida Muller

## Próximos Passos no Workshop

1. Escrever testes unitários para TransferenciaService
2. Testar controladores com @WebMvcTest
3. Criar cenários de falha e exceções para validar regras
4. Usar Testcontainers ou H2 para testes de integração (opcional)