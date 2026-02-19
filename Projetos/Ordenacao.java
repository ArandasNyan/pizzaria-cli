package Projetos;

import java.util.Comparator;
import java.util.List;

public class Ordenacao {
  // Bubble Sort
  public static <T> void bubbleSort(List<T> lista, Comparator<T> comparador) {
    int tamanhoDaLista = lista.size();

    for (int i = 0; i < tamanhoDaLista; i++){
      for (int j = 0; j < tamanhoDaLista - 1 - i; j++) {
        T atual = lista.get(j);
        T proximo = lista.get(j + 1);

        // se for maior que 0, significa que precisa trocar
        if (comparador.compare(atual, proximo) > 0) {
          lista.set(j, proximo);
          lista.set(j + 1, atual);
        }
      }
    }
  }


  // Selection Sort
  public static <T> void selectionSort(List<T> lista, Comparator<T> comparador) {
    int tamanhoDaLista = lista.size();

    for (int i = 0; i < tamanhoDaLista; i++) {
      int indiceSelecionado = i;

      for( int j = i + 1; j < tamanhoDaLista; j++) {
        if (comparador.compare(lista.get(indiceSelecionado), lista.get(j)) > 0) {
          indiceSelecionado = j;
        }
      }

      if (indiceSelecionado != i) {
        T temp = lista.get(i);
        lista.set(i, lista.get(indiceSelecionado));
        lista.set(indiceSelecionado, temp);
      }
    }
  }
}
