# Descomplicando Kafka com Java, Docker e Kafdrop (Parte 1)

Este repositório contém o código-fonte do guia "Descomplicando Kafka (Parte 1)", que demonstra os conceitos fundamentais do Apache Kafka de forma prática.

## 🎯 Caso de Uso: Monitoramento de Temperatura de um Data Center

O projeto simula um sistema que monitora a temperatura de uma sala de servidores em tempo real. Sensores enviam dados de temperatura para o Kafka, e o sistema processa esses dados para tomar ações automatizadas, como alertar a equipe de manutenção se a temperatura ultrapassar um limite crítico.

### Benefícios
- **Alertas em Tempo Real**: Notificação imediata sobre anomalias de temperatura.
- **Automação**: Capacidade de acionar sistemas de resfriamento automaticamente.

---

## 🐘 O que é Apache Kafka?

É uma plataforma de streaming de eventos distribuída, open-source, projetada para lidar com grandes volumes de dados em tempo real. Atua como um sistema de mensagens robusto, escalável e de baixa latência.

### Conceitos Fundamentais
- **Broker**: Um servidor Kafka que armazena os dados (mensagens).
- **Cluster**: Um conjunto de brokers trabalhando juntos para garantir escalabilidade, alta disponibilidade e tolerância a falhas.
- **Partições e Keys**: Tópicos podem ser divididos em partições para permitir paralelismo. O uso de uma `key` (como o ID de um sensor) garante que todas as mensagens com a mesma chave sejam enviadas para a mesma partição, mantendo a ordem.
- **Consumers e Rebalanceamento**: Cada partição só pode ser lida por um consumidor por vez dentro de um mesmo grupo. O Kafka gerencia a distribuição das partições entre os consumidores de forma automática.

---

## 🛠️ Tecnologias Utilizadas
- **Apache Kafka**: Plataforma de streaming de eventos.
- **Java**: Linguagem para desenvolver os produtores e consumidores.
- **Docker**: Para configurar e gerenciar o ambiente de forma simples.
- **Kafdrop**: Interface web para visualizar e gerenciar o cluster Kafka.

---

## 🚀 Configurando o Ambiente

O ambiente é orquestrado usando Docker Compose. O arquivo `docker-compose.yml` configura os seguintes serviços:
- **`broker`**: broker kraft
- **`kafdrop`**: A interface de visualização, acessível em `http://localhost:9000`.

Para iniciar o ambiente, execute:
```sh
docker-compose up -d
