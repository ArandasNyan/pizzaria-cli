# Pizzaria CLI

Pizzaria CLI é uma aplicação de linha de comando, escrita em Java, que simula as operações básicas de uma pizzaria: cadastro de clientes, montagem de pedidos com múltiplas pizzas e sabores, cálculo do preço justo por pizza a partir do cardápio e geração de relatórios básicos. O projeto foi pensado como uma base didática para demonstrar aprendizado durante um curso de algoritmos e estrutura de dados.

## Funcionalidades principais
- **Cadastro de clientes:** permite registrar clientes com nome, endereço, telefone e e-mail.
- **Cardápio integrado:** lista de sabores com preços pré-definidos; cálculo automático do preço de uma pizza composta por 1 a 4 sabores (método de preço justo).
- **Montagem de pedidos:** criação de pedidos compostos por uma ou mais pizzas; escolha de tamanho e sabores por pizza.
- **Soma de valores do pedido:** cálculo do valor total do pedido somando os preços das pizzas.
- **Relatórios e listagem de clientes:** comandos placeholders para geração de relatórios e para listar clientes adicionados (prontos para expansão).

## Arquitetura / Classes
- **`Cardapio`**: mantém um `Map<String, Double>` com sabores e preços e expõe `getPrecoJusto(List<String>)` para calcular o preço de uma pizza por combinação de sabores.
- **`Cliente`**: entidade simples com campos `nome`, `endereco`, `telefone` e `email` e getters/setters.
- **`Pizza`**: representa uma pizza com lista de `sabores`, `preco` e `TamanhoPizza` (enum: `BROTO`, `GRANDE`, `GIGA`). Inclui utilitário `getByIndex` para mapear escolhas numéricas.
- **`Pedido`**: associa `Cliente`, lista de `Pizza` e `valorTotal` (modelo simples para armazenar pedidos).
- **`Pizzaria`**: classe com método `main` que implementa a interface CLI: menu interativo para criar/alterar pedidos, adicionar clientes, gerar relatórios e listar clientes.

### Como compilar e executar
1. Abra um terminal no diretório do projeto.
2. Compile os fontes Java:

```
javac Projeto/*.java
```

3. Execute a aplicação:

```
java -cp Projeto Projeto.Pizzaria
```

Observação: a estrutura do pacote é `Projeto`, portanto o comando de execução usa o nome qualificado `Projeto.Pizzaria`.

## Fluxo de uso (exemplo resumido)
- Ao iniciar, escolha a opção de adicionar um cliente (`3 - Adicionar um cliente`).
- Em seguida, escolha `1 - Fazer um novo pedido`, selecione o cliente e monte uma ou mais pizzas: selecione tamanho, quantidade de sabores (1–4) e os sabores do cardápio.
- Ao finalizar a montagem das pizzas, o pedido é gravado na lista interna de pedidos com o valor total calculado automaticamente.

## Limitações conhecidas e próximos passos sugeridos
- As funções `alterarPedido()` e `gerarRelatorio()` estão como placeholders e precisam ser implementadas para edição de pedidos e relatórios completos.
- Persistência: atualmente todos os dados são mantidos em memória; adicionar persistência (arquivo/DB) permitiria manter clientes e pedidos entre execuções.
- Validações: melhorar tratamento de erros (valores inválidos, seleção fora do intervalo) para maior robustez da CLI.

**Contribuição**
- Fique à vontade para abrir PRs para implementar funcionalidades adicionais (edição de pedido, relatórios detalhados, autenticação, persistência).

Arquivo com a documentação atual: [README.md](README.md)

---
Obrigado por testar — se quiser, eu posso implementar os próximos passos sugeridos (por exemplo, persistência ou edição de pedidos).

