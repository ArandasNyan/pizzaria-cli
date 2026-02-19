package Projetos;

import java.util.*;

import Projetos.Pizza.TamanhoPizza;

public class Pizzaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        exibirBemVindo();
        boolean continuar = true;
        while (continuar) {
            exibirMenuPrincipal(listaClientes.size(), listaPedidos.size());
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    if (listaClientes.isEmpty()) {
                        exibirErro("Nenhum cliente cadastrado. Adicione um cliente primeiro!");
                    } else {
                        fazerPedido(scanner, listaPedidos, listaClientes);
                    }
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 3:
                    listaClientes.add(adicionarCliente(scanner));
                    exibirSucesso("Cliente adicionado com sucesso!");
                    break;
                case 4:
                    gerarRelatorio(listaPedidos);
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 9:
                    exibirSaida();
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    exibirErro("Opção inválida!");
                    break;
            }
        }
    }

    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        List<Pizza> pizzas = new ArrayList<>();
        exibirCabecalho("FAZER PEDIDO");

        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.println(x + " - " + cliente.getNome());
            x++;
        }
        System.out.print("Opção: ");
        int cliente = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        boolean continuar = true;
        int pizzaCount = 0;
        while (continuar) {
            pizzaCount++;
            exibirSecao("PIZZA #" + pizzaCount);
            x = 1;
            System.out.println("Qual o tamanho da pizza? ");
            for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
                System.out.println(x + " - " + tamanhos);
                x++;
            }
            System.out.print("Opção: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.println("\n[!] Digite a quantidade de sabores (1-4): ");
                System.out.print("Opção: ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            Cardapio cardapio = new Cardapio();
            List<String> saboresList = new ArrayList<>();
            List<String> saboresSelect = new ArrayList<>();

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("\nSabor " + (i + 1) + ":");
                x = 1;
                saboresList.clear();
                for (String sabor : cardapio.getCardapio().keySet()) {
                    saboresList.add(sabor);
                    System.out.println(x + " - " + sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                saboresSelect.add(saboresList.get(opcao - 1));
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect),
                    TamanhoPizza.getByIndex(tamanho - 1));
            pizzas.add(pizza);

            System.out.println();
            exibirSucesso("Pizza #" + pizzaCount + " cadastrada - " + pizza.getTamanho() + " - R$ " + String.format("%.2f", pizza.getPreco()));
            System.out.println();
            System.out.println("Deseja adicionar mais uma pizza? (1-Sim / 2-Não): ");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao != 1) {
                continuar = false;
            }
            System.out.println();
        }
        
        double totalPizzas = somarPizzas(pizzas);
        
        System.out.println();
        System.out.print("Digite a distância em kms para o endereço do cliente: ");
        double distanciaEmKm = scanner.nextDouble();
        scanner.nextLine();
        System.out.println();
        
        double valorFrete = calcularFrete(distanciaEmKm, pizzas.size());
        double totalComFrete = totalPizzas + valorFrete;
        
        Pedido pedido = new Pedido(listaPedidos.size() + 1, listaClientes.get(cliente - 1), pizzas, totalComFrete);
        listaPedidos.add(pedido);
        
        System.out.println();
        exibirSeparador();
        exibirSucesso("PEDIDO #" + pedido.getId() + " CRIADO COM SUCESSO!");
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Total de pizzas: " + pizzas.size());
        System.out.println("Valor das pizzas: R$ " + String.format("%.2f", totalPizzas));
        System.out.println("Distância: " + String.format("%.2f", distanciaEmKm) + " km");
        System.out.println("Valor do frete: R$ " + String.format("%.2f", valorFrete));
        System.out.println("Valor total: R$ " + String.format("%.2f", totalComFrete));
        exibirSeparador();
        System.out.println();
    }

    private static double somarPizzas(List<Pizza> pizzas) {
        double valorTotal = 0;
        for (Pizza pizza : pizzas) {
            valorTotal += pizza.getPreco();
        }
        return valorTotal;
    }

    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        // verifica se antes de tudo a lista de pedidos está vazia!
        if (listaPedidos.isEmpty()) {
            exibirErro("Nenhum pedido disponível para alterar!");
            return;
        }

        // exibe o menu de alterar pedido
        exibirCabecalho("ALTERAR PEDIDO");

        System.out.println("Buscar pedido por:");
        System.out.println("1 - ID do pedido"); // 
        System.out.println("2 - Nome do cliente");
        System.out.print("Opção: ");
        int opcaoBusca = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        Pedido pedidoSelecionado = null;

        // busca por id ou por nome
        if (opcaoBusca == 1) {
            System.out.print("Digite o ID do pedido: ");
            int idPedido = scanner.nextInt();
            scanner.nextLine();

            for (Pedido pedido : listaPedidos) {
                if (pedido.getId() == idPedido) {
                    pedidoSelecionado = pedido;
                    break;
                }
            }
        } else if (opcaoBusca == 2) {
            System.out.print("Digite o nome do cliente: ");
            String nomeCliente = scanner.nextLine();

            for (Pedido pedido : listaPedidos) {
                if (pedido.getCliente().getNome().equalsIgnoreCase(nomeCliente)) {
                    pedidoSelecionado = pedido;
                    break;
                }
            }
        }

        if (pedidoSelecionado == null) {
            exibirErro("Pedido não encontrado!");
            return;
        }

        System.out.println();
        exibirSeparador();
        System.out.println("ID do Pedido: #" + pedidoSelecionado.getId());
        System.out.println("Cliente: " + pedidoSelecionado.getCliente().getNome());
        System.out.println("Telefone: " + pedidoSelecionado.getCliente().getTelefone());
        System.out.println("-".repeat(50));
        System.out.println("Pizzas no pedido:");

        List<Pizza> pizzas = pedidoSelecionado.getPizzas();

        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            System.out.println((i + 1) + ") Tamanho: " + pizza.getTamanho() + " | Sabores: " + pizza.getSabores() + " | R$ " + String.format("%.2f", pizza.getPreco()));
        }

        System.out.println("Valor total: R$ " + String.format("%.2f", pedidoSelecionado.getValorTotal()));
        exibirSeparador();
        System.out.println();

        boolean continuar = true;
        while (continuar) {
            exibirSecao("OPÇÕES DE ALTERAÇÃO");
            System.out.println("1 - Adicionar pizza");
            System.out.println("2 - Remover pizza");
            System.out.println("3 - Alterar sabor de uma pizza");
            System.out.println("4 - Finalizar alterações");
            System.out.print("Opção: ");
            int opcaoAlteracao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcaoAlteracao) {
                case 1:
                    pedidoSelecionado.adicionarPizza(scanner);
                    exibirSucesso("Pizza adicionada com sucesso!");
                    System.out.println();
                    break;
                case 2:
                    pedidoSelecionado.removerPizza(scanner);
                    System.out.println();
                    break;
                case 3:
                    pedidoSelecionado.alterarSaborPizza(scanner);
                    System.out.println();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    exibirErro("Opção inválida!");
                    System.out.println();
                    break;
            }
        }

        pedidoSelecionado.setValorTotal(somarPizzas(pedidoSelecionado.getPizzas()));
        System.out.println();
        exibirSeparador();
        exibirSucesso("PEDIDO #" + pedidoSelecionado.getId() + " ALTERADO COM SUCESSO!");
        System.out.println("Total de pizzas: " + pedidoSelecionado.getPizzas().size());
        System.out.println("Valor total atualizado: R$ " + String.format("%.2f", pedidoSelecionado.getValorTotal()));
        exibirSeparador();
        System.out.println();
    }

    private static Cliente adicionarCliente(Scanner scanner) {
        exibirCabecalho("ADICIONAR CLIENTE");
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println();

        Cliente cliente = new Cliente(nome, endereco, telefone, email);
        return cliente;
    }

    private static void gerarRelatorio(List<Pedido> listaPedidos) {
        exibirCabecalho("RELATÓRIO DE VENDAS");

        // Verifica se a lista de pedidos possui pedidos
        if (listaPedidos == null || listaPedidos.isEmpty()) {
            exibirErro("Nenhum pedido registrado.");
            System.out.println();
            return;
        }

        double faturamentoTotal = 0.0;
        Map<String, Integer> contagemPorSabor = new HashMap<String, Integer>();
        Map<String, Map<String, Integer>> saboresAdjacentes = new HashMap<String, Map<String, Integer>>();

        for (Pedido pedido : listaPedidos) {
            faturamentoTotal += pedido.getValorTotal();
            for (Pizza pizza : pedido.getPizzas()) {
                List<String> listaSabores = pizza.getSabores();

                // contar cada sabor
                for (String sabor : listaSabores) {
                    if (contagemPorSabor.containsKey(sabor)) {
                        contagemPorSabor.put(sabor, contagemPorSabor.get(sabor) + 1); // se já existir, somar em mais 1
                    } else {
                        contagemPorSabor.put(sabor, 1); // se não existir, inicializar em 1
                    }
                }

                // contar co-ocorrências entre pares de sabores na mesma pizza
                for (int i = 0; i < listaSabores.size(); i++) {
                    for (int j = i + 1; j < listaSabores.size(); j++) {
                        String saborPrimeiro = listaSabores.get(i);
                        String saborSegundo = listaSabores.get(j);

                        if (!saboresAdjacentes.containsKey(saborPrimeiro)) {
                            saboresAdjacentes.put(saborPrimeiro, new HashMap<String, Integer>());
                        }
                        Map<String, Integer> mapaPrimeiro = saboresAdjacentes.get(saborPrimeiro);
                        if (mapaPrimeiro.containsKey(saborSegundo)) {
                            mapaPrimeiro.put(saborSegundo, mapaPrimeiro.get(saborSegundo) + 1);
                        } else {
                            mapaPrimeiro.put(saborSegundo, 1);
                        }

                        if (!saboresAdjacentes.containsKey(saborSegundo)) {
                            saboresAdjacentes.put(saborSegundo, new HashMap<String, Integer>());
                        }
                        Map<String, Integer> mapaSegundo = saboresAdjacentes.get(saborSegundo);
                        if (mapaSegundo.containsKey(saborPrimeiro)) {
                            mapaSegundo.put(saborPrimeiro, mapaSegundo.get(saborPrimeiro) + 1);
                        } else {
                            mapaSegundo.put(saborPrimeiro, 1);
                        }
                    }
                }
            }
        }

        System.out.println("Faturamento total: R$ " + String.format("%.2f", faturamentoTotal));
        System.out.println();

        // preparar lista de sabores ordenada por contagem (maior para menor) usando ordenação bubble sort.
        List<Map.Entry<String, Integer>> listaSaboresOrdenada = new ArrayList<Map.Entry<String, Integer>>(contagemPorSabor.entrySet());

        Ordenacao.bubbleSort(listaSaboresOrdenada, (a, b) -> Integer.compare(b.getValue(), a.getValue())); // ordenação bubble sort

        System.out.println("Sabores mais pedidos:");
        int posicao = 1;
        for (Map.Entry<String, Integer> entradaSabor : listaSaboresOrdenada) {
            System.out.println(posicao + " - " + entradaSabor.getKey() + " (" + entradaSabor.getValue() + " pedidos)");
            posicao++;
        }
        System.out.println();

        List<Aresta> listaArestas = new ArrayList<Aresta>();

        for (Map.Entry<String, Map<String, Integer>> entradaCo : saboresAdjacentes.entrySet()) {
            String saborChave = entradaCo.getKey();
            Map<String, Integer> mapaAdjacentes = entradaCo.getValue();
            
            for (Map.Entry<String, Integer> entradaAdj : mapaAdjacentes.entrySet()) {
                String saborAdjacente = entradaAdj.getKey();

                int pesoAdjacente = entradaAdj.getValue();

                if (saborChave.compareTo(saborAdjacente) < 0) {
                    listaArestas.add(new Aresta(saborChave, saborAdjacente, pesoAdjacente));
                }
            }
        }

        // ordenar arestas por peso decrescente usando ordenação selection sort.
        Ordenacao.selectionSort(listaArestas, (a, b) -> Integer.compare(b.peso, a.peso));

        System.out.println("Ligações entre os sabores (co-ocorrências em pizzas meio a meio):");
        if (listaArestas.isEmpty()) {
            System.out.println("Nenhuma ligação encontrada (pizzas com 1 sabor ou sem pedidos).");
        } else {
            for (Aresta aresta : listaArestas) {
                System.out.println("- " + aresta.saborOrigem + " -- " + aresta.saborDestino + " : " + aresta.peso + " vezes");
            }
        }

        System.out.println();
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        exibirCabecalho("LISTA DE CLIENTES");
        
        if (listaClientes.isEmpty()) {
            exibirErro("Nenhum cliente cadastrado.");
            System.out.println();
            return;
        }
        
        int x = 1;
        for (Cliente cliente : listaClientes) {
            exibirSecao("Cliente #" + x);
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Endereço: " + cliente.getEndereco());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println();
            x++;
        }
        System.out.println("Total de clientes: " + listaClientes.size());
        System.out.println();
    }

    private static double calcularFrete(double distanciaEmKm, int quantidadePizzas) {
        // Tabela de preços para cálculo de frete
        double valorPorKm = 2.5; // R$ 2,50 por km
        double valorPorPizza = 3.0; // R$ 3,00 por pizza

        // Calcular frete por distância
        double freteDistancia = distanciaEmKm * valorPorKm;

        // Calcular frete por peso (quantidade de pizzas)
        double fretePeso = quantidadePizzas * valorPorPizza;

        // Frete total é a soma dos dois componentes
        double freteTotal = freteDistancia + fretePeso;

        return freteTotal;
    }

    // Métodos auxiliares para melhorar feedback de ação e retorno
    private static void exibirMenuPrincipal(int totalClientes, int totalPedidos) {
        System.out.println();
        System.out.println("MENU PRINCIPAL:");
        System.out.println("1 - Fazer um novo pedido");
        System.out.println("2 - Alterar um pedido");
        System.out.println("3 - Adicionar um cliente");
        System.out.println("4 - Gerar relatório de vendas");
        System.out.println("5 - Listar clientes");
        System.out.println("9 - Sair");
        exibirSeparador();
    }

    private static void exibirCabecalho(String titulo) {
        System.out.println();
        exibirSeparador();
        System.out.println(titulo);
        exibirSeparador();
    }

    private static void exibirSecao(String titulo) {
        System.out.println("-".repeat(50));
        System.out.println(titulo);
        System.out.println("-".repeat(50));
    }

    private static void exibirSeparador() {
        System.out.println("=".repeat(50));
    }

    private static void exibirSucesso(String mensagem) {
        System.out.println("[✓] " + mensagem);
        System.out.println();
    }

    private static void exibirErro(String mensagem) {
        System.out.println("[✗] " + mensagem);
    }

    private static void exibirBemVindo() {
        System.out.println();
        exibirSeparador();
        System.out.println("        BEM-VINDO À PIZZARIA CLI");
        exibirSeparador();
        System.out.println();
    }

    private static void exibirSaida() {
        System.out.println();
        exibirSeparador();
        System.out.println("                 Até Amanhã...");
        exibirSeparador();
        System.out.println();
    }
}
