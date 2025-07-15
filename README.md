# 🎬 ScreenMatch

Projeto em Java para consultar e exibir informações de séries a partir de uma API externa.

## 📚 Descrição

O **ScreenMatch** é uma aplicação que consome dados do OMDb API e organiza informações sobre séries e episódios. O projeto utiliza Java, Maven e boas práticas de orientação a objetos para realizar a conversão de dados JSON em objetos Java, além de tratá-los e exibi-los no terminal e armazenar as séries buscadas em um banco de dados PostgreSQL.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Maven
- Spring Boot (base do projeto)
- Jackson (para conversão de JSON)
- OMDb API

## 🧱 Estrutura do Projeto

```
src/
├── main/
│ ├── java/br/com/alura/screenmatch/
│ │ ├── model/ # Modelos de dados: DadosSerie, DadosTemporada, Episodio, etc.
│ │ ├── service/ # Serviços de consumo de API e conversão de dados
│ │ └── principal/ # Classe principal de execução
│ │ └── repository/ # Gerencia as séries
│ └── resources/
│ └── application.properties
└── test/
└── java/br/com/alura/screenmatch/
```

## ⚙️ Como executar

1. **Clone o projeto:**

```
git clone https://github.com/marcus-viniciusdev/screenmatch.git
cd screenmatch

    Configure as variáveis de ambiente: DB_HOST, DB_NAME, DB_USER,
    DB_PASSWORD, OPENAI_KEY e API_KEY com a chave do OMDb

    Compile e execute:
```

## ✅ Funcionalidades

    Buscar dados de séries por nome e guarda-los em um banco de dados.
    Resgata os dados do banco de dados.

## 👨‍💻 Autor

Projeto baseado nos cursos da Alura. Adaptado por Marcus Vinicius.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
