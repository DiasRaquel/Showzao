# Showzão

Showzão é uma aplicação Java para gerenciar shows, permitindo aos usuários visualizar, cadastrar e pesquisar shows por gênero.

## Funcionalidades

Mostrar Todos os Shows: Visualize todos os shows cadastrados.

Pesquisar por Gênero: Busque shows específicos filtrando por gênero.

Cadastro de Shows: Adicione novos shows ao banco de dados.

Cadastro de Gêneros: Registre novos gêneros de shows.

Cadastro de Locais: Cadastre novos locais onde os shows podem ocorrer.

## Pré-requisitos

JDK 8 ou superior

MySQL (ou outro banco de dados compatível)

# Instalação e Configuração

```bash
git clone https://github.com/seu-usuario/showzao.git
```

## Estrutura do Projeto

```css
showzao/
│
├── src/
│   ├── dao/
│   │   └── Conexao.java
│   ├── model/
│   │   ├── Genero.java
│   │   ├── Local.java
│   │   └── Show.java
│   └── view/
│       └── Main.java
│
├── database.sql
└── README.md
```

## Contribuição
Contribuições são bem-vindas! Se deseja melhorar o Showzão, siga estas etapas:

Fork o projeto

Crie um branch (git checkout -b feature/nova-feature)

Commit suas mudanças (git commit -am 'Adiciona nova feature')

Push para o branch (git push origin feature/nova-feature)

Abra um Pull Request

## License

[MIT](https://choosealicense.com/licenses/mit/)