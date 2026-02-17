package Projetos;

import java.util.List;

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
}
