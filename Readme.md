# API de Transferências Bancárias Internas

## Objetivo

Api desenvolvida para o workshop de testes unitários. O cenário é um programa que realiza transferências entre contas bancárias internas. 
O projeto tem como foco a prática de testes unitários, validação de regras de negócio e boas práticas de desenvolvimento.

## Sumário

1. [API de Transferências Bancárias Internas](#api-de-transferências-bancárias-internas)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Como Executar Localmente](#como-executar-localmente)
    - [1. Clone o repositório](#1-clone-o-repositório)
    - [2. Rode a aplicação](#2-rode-a-aplicação)
    - [3. Console do H2](#3-console-do-h2)
4. [Endpoints](#endpoints)
    - [1. Post /transferencias](#1-post-transferencias)
    - [2. Get /transferencias/{id}](#2-get-transferenciasid)
5. [Dados Iniciais no Banco H2](#dados-iniciais-no-banco-h2)
6. [Objetivo do Workshop](#objetivo-do-workshop)
7. [Equipe](#equipe)
7. [Próximos Passos no Workshop](#próximos-passos-no-workshop)
8. [Dicas para os Testes](#dicas-para-os-testes)
    - [1. Escrever Testes Pequenos e Específicos](#1-escrever-testes-pequenos-e-específicos)
    - [2. Escrever Testes Auto-suficientes](#2-escrever-testes-auto-suficientes)
    - [3. Testes Burros são Ótimos](#3-testes-burros-são-ótimos)
    - [4. Testar Perto da Realidade](#4-testar-perto-da-realidade)
    - [5. Práticas Gerais](#5-práticas-gerais)
    - [6. Práticas Java/JVM](#6-práticas-javajvm)
    - [7. Tornar a Implementação Testável](#7-tornar-a-implementação-testável)
    - [8. Recomendações de Limpeza do Código de Teste](#8-recomendações-de-limpeza-do-código-de-teste)


---

## [Tecnologias Utilizadas](#sumário)
- Java 23
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (em memória)
- Gradle
- Junit
- Mockito
- Reflection

---

## [Como Executar Localmente](#sumário)

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

## [Endpoints](#sumário)

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

## [Dados Iniciais no Banco H2](#sumário)

Ao subir a aplicação, duas contas fictícias são cadastradas automaticamente (via `data.sql` ou script Java opcional):

| Número | Conta | Titular        | Saldo Inicial |
|--------|-------|----------------|---------------|
| 1      | 1234  | João Silva     | 1000.00       |
| 2      | 5678  | Maria Oliveira | 2000.00       |

## [Objetivo do Workshop](#sumário)

Este projeto foi criado para exercitar:

* Escrita de testes unitários (JUnit, Mockito)
* Validação de regras de negócio (saldo, valores, contas)

## [Equipe](#sumário)

* Time Alice* 
* Instrutores/Facilitadores: Renato Dal Zot, Emanuel Bispo Curralinho, Matheus de Almeida Muller

## [Próximos Passos no Workshop](#sumário)

1. Escrever testes unitários para TransferenciaService
2. Criar cenários de falha e exceções para validar regras
3. 

## [Dicas para os Testes](#sumário)*

### 1. **Escrever Testes Pequenos e Específicos**
   - Usar Funções Auxiliares
   - Não Usar Blocos de Variáveis
   - KISS + DRY
   - Não Escrever Testes Existentes
   - Asertar Apenas o Que Quer Testar


### 2. **Escrever Testes Auto-suficientes**
   - Não Esconder Parâmetros Relevantes
   - Inserir Dados de Teste no Mundo de Teste
   - Promover Compreensão sobre Herança
   - KISS + DRY


### 3. **Testes Burros são Ótimos**
   - Comparar Saídas com Valores Fixos
   - Não Reescrever Código de Produção
   - Não Reescrever Lógica de Produção
   - Não Escrever Metateste


### 4. **Testar Perto da Realidade**
   - Focar em Testar um Fluxo Vertical Completo
   - Não Usar Bancos de Dados em Memória


### 5. **Práticas Gerais**
   - Given, When, Then
   - Prefira `assertThat()` a `assertEquals()`
   - Usar Dados Fixos em vez de Aleatórios


### 6. **Práticas Java/JVM**
   - Usar Opycle/JVM para Inicialização Rápida
   - Usar `assertJ`
   - Evitar `assertTrue()` e `assertFalse()`
   - Usar `Flakist`
   - Mockar Serviços Remotos (OkHttp/WebMockServer, WireMock, Testcontainers)
   - Usar Awaitility para Código Assíncrono
   - Não Precisar Fazer Bootstrap de DI (Spring)


### 7. **Tornar a Implementação Testável**
   - Não Usar Acesso Estático
   - Parametrizar Partes Relevantes
   - Usar Injeção de Dependência
   - Não Usar `Instant.now()` (usar `Clock` ou uma instância fixa)
   - Separar Execução Assíncrona e Lógica Real


### 8. **Recomendações de Limpeza do Código de Teste**
   - Funções de Ajuda (Helper Functions): extraia implementações para funções privadas, tornando o código mais enxuto e descritível. 
   - Testes Parametrizados (@MethodSource): O uso de testes parametrizados com @MethodSource no JUnit 5 é recomendado para testar múltiplos cenários de forma eficiente, passando parâmetros de entrada e a saída esperada.
   - Nomes de Teste Legíveis
   - Injeção por Construtor

