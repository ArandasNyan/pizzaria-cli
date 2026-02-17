package Projetos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pedido {
  private int id;
  private Cliente cliente;
  private List<Pizza> pizzas;
  private double valorTotal;

  public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal) {
    this.id = id;
    this.cliente = cliente;
    this.pizzas = pizzas;
    this.valorTotal = valorTotal;
  }

  public void setId(int id) {
    this.id = id;
  }
  public int getId() {
    return id;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }
  public Cliente getCliente() {
    return cliente;
  }

  public void setPizzas(List<Pizza> pizzas) {
    this.pizzas = pizzas;
  }
  public List<Pizza> getPizzas() {
    return pizzas;
  }

  public void setValorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
  }
  public double getValorTotal() {
    return valorTotal;
  }

  public void adicionarPizza(Scanner scanner) {
    System.out.println();
    System.out.println("ADICIONAR PIZZA");

    int x = 1;
    System.out.println("Qual o tamanho da pizza?");
    System.out.println("Selecione um tamanho: ");
    for (Pizza.TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
      System.out.println(x + " - " + tamanhos);
      x++;
    }
    System.out.print("Opção: ");
    int tamanho = scanner.nextInt();
    scanner.nextLine();

    int quantiSabores = 0;
    while (quantiSabores < 1 || quantiSabores > 4) {
      System.out.println("Digite a quantidade de sabores: 1 - 4");
      System.out.print("Opção: ");
      quantiSabores = scanner.nextInt();
      scanner.nextLine();
    }

    Cardapio cardapio = new Cardapio();
    List<String> saboresList = new ArrayList<>();
    List<String> saboresSelect = new ArrayList<>();

    for (int i = 0; i < quantiSabores; i++) {
      System.out.println("Selecione um sabor: ");
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
        Pizza.TamanhoPizza.getByIndex(tamanho - 1));
    pizzas.add(pizza);
  }

  public void removerPizza(Scanner scanner) {
    if (pizzas.isEmpty()) {
      System.out.println("Não há pizzas para remover!");
      return;
    }

    System.out.println();
    System.out.println("REMOVER PIZZA");
    System.out.println("Qual pizza deseja remover?");
    for (int i = 0; i < pizzas.size(); i++) {
      Pizza pizza = pizzas.get(i);
      System.out.println((i + 1) + " - Tamanho: " + pizza.getTamanho() + ", Sabores: " + pizza.getSabores() + ", Preço: R$ " + pizza.getPreco());
    }
    System.out.print("Opção: ");
    int indice = scanner.nextInt();
    scanner.nextLine();

    if (indice > 0 && indice <= pizzas.size()) {
      pizzas.remove(indice - 1);
      System.out.println("Pizza removida com sucesso!");
    } else {
      System.out.println("Opção inválida!");
    }
  }

  public void alterarSaborPizza(Scanner scanner) {
    if (pizzas.isEmpty()) {
      System.out.println("Não há pizzas para alterar!");
      return;
    }

    System.out.println();
    System.out.println("ALTERAR SABOR DA PIZZA");
    System.out.println("Qual pizza deseja alterar?");
    for (int i = 0; i < pizzas.size(); i++) {
      Pizza pizza = pizzas.get(i);
      System.out.println((i + 1) + " - Tamanho: " + pizza.getTamanho() + ", Sabores: " + pizza.getSabores() + ", Preço: R$ " + pizza.getPreco());
    }
    System.out.print("Opção: ");
    int indice = scanner.nextInt();
    scanner.nextLine();

    if (indice < 1 || indice > pizzas.size()) {
      System.out.println("Opção inválida!");
      return;
    }

    Pizza pizza = pizzas.get(indice - 1);
    System.out.println("Sabores atuais: " + pizza.getSabores());
    System.out.println("Qual sabor deseja alterar?");
    List<String> saboresAtuais = pizza.getSabores();
    for (int i = 0; i < saboresAtuais.size(); i++) {
      System.out.println((i + 1) + " - " + saboresAtuais.get(i));
    }
    System.out.print("Opção: ");
    int saborIndice = scanner.nextInt();
    scanner.nextLine();

    if (saborIndice < 1 || saborIndice > saboresAtuais.size()) {
      System.out.println("Opção inválida!");
      return;
    }

    Cardapio cardapio = new Cardapio();
    System.out.println("Selecione o novo sabor:");
    List<String> saboresList = new ArrayList<>(cardapio.getCardapio().keySet());
    for (int i = 0; i < saboresList.size(); i++) {
      System.out.println((i + 1) + " - " + saboresList.get(i));
    }
    System.out.print("Opção: ");
    int novoSaborIndice = scanner.nextInt();
    scanner.nextLine();

    if (novoSaborIndice < 1 || novoSaborIndice > saboresList.size()) {
      System.out.println("Opção inválida!");
      return;
    }

    saboresAtuais.set(saborIndice - 1, saboresList.get(novoSaborIndice - 1));
    pizza.setSabores(saboresAtuais);
    pizza.setPreco(cardapio.getPrecoJusto(saboresAtuais));
    System.out.println("Sabor alterado com sucesso!");
  }
}
