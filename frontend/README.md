# 🚀 **Docker Generator - Configuração Automática de Containers**

Este projeto é uma aplicação web que permite **criar e gerenciar containers Docker** automaticamente, gerando Dockerfiles e executando contêineres com base nas tecnologias selecionadas pelo usuário. A interface amigável oferece uma maneira eficiente de configurar ambientes rapidamente.

## 🔥 **Funcionalidades**
- 📦 Criação automática de containers Docker.
- 📝 Geração de Dockerfiles personalizados.
- 🖥️ Interface intuitiva para seleção de tecnologias e configuração de containers.
- 💬 Feedback visual durante o processo (spinner e mensagens).
- 🔧 Suporte a diversas tecnologias como **Adminer**, **Cockpit**, **PhpLiteAdmin**, **Nginx**, **MySQL**, **RabbitMQ**, **Zabbix**, entre outras.

## 🛠️ **Tecnologias Utilizadas**

### **Frontend:**
- ⚛️ **React** com TypeScript.
- 🎨 **Bootstrap** para estilização responsiva.
- 🔗 **Axios** para comunicação HTTP.

### **Backend:**
- ☕ **Java 17** com **Spring Boot**.
- 🐳 API de cliente **Docker** para gerenciar containers.

### **Tecnologias Docker Suportadas:**
- 🗄️ **Adminer**: Interface para bancos de dados.
- 🖥️ **Cockpit**: Gerenciamento de sistemas via web.
- 💾 **PhpLiteAdmin**: Interface para SQLite. EM CONSTRUÇÃO
- 🌐 **Nginx**: Servidor web.
- 🛢️ **MySQL**: Banco de dados relacional. EM CONSTRUÇÃO
- 🐇 **RabbitMQ**: Sistema de mensageria.
- 📊 **Zabbix**: Monitoramento de infraestrutura.

## 🧰 **Pré-requisitos**

- 🐳 **Docker**.
- ☕ **Java 22**.
- 🟢 **Node.js** e **npm**.
- 📦 **Maven**.

## 🚀 **Como Executar o Projeto**

### **Backend:**

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/docker-generator.git
   cd docker-generator/backend

2. Compile e Rode o Backend:

   ```bash
   mvn clean install
   mvn spring-boot:run

### **Frontend:**


1. Clone o repositório:

   ```bash
   
   cd frontend

2. Compile e Rode o Backend:

   ```bash
   npm install
   npm run dev

## 🔗 ** Endpoints da API**

###   GET  /api/tecnologias
Retorna a lista de tecnologias disponíveis.


### POST /api/configuracoes
Envia a configuração do container para criação.

### GET /api/configuracoes/listar

Retorna uma lista de todos os containers.

## POST /api/configuracoes/iniciar/{containerId}

 Inicia um container específico.

 ## POST /api/configuracoes/parar/{containerId}
 Para um container específico

 ## DELETE /api/configuracoes/parar/{containerId}

 Remove um container específico.

 ## GET /api/configuracoes/status/{containerId}

  Retorna o status de um container específico.