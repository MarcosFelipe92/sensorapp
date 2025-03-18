# Módulo de Monitoramento

## Descrição
Este projeto consiste em um módulo Java baseado em Spring Boot que recebe dados de temperatura, luminosidade e umidade enviados por um Arduino. Após processar esses dados, o sistema os transmite para o frontend via WebSocket, utilizando o protocolo STOMP.

## Tecnologias Utilizadas
- **Java**
- **Spring Boot**
- **WebSockets com STOMP**
- **Arduino (como fonte de dados)**

## Funcionalidades
- Recebimento de dados de sensores (temperatura, umidade, luminosidade) via porta SERIAL.
- Processamento dos dados recebidos.
- Envio de mensagens para o frontend em tempo real via WebSocket.