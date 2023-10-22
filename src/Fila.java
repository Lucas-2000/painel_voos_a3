import java.util.ArrayList;
import java.util.List;

public class Fila {
    static class Nodo {
        Voo data;
        Nodo next;
        Nodo prev;
    }

    Nodo front, rear;

    Fila() {
        front = rear = null;
    }

    void store(Voo elem) {
        Nodo nodo = new Nodo();
        nodo.data = elem;

        if (rear == null) {
            front = rear = nodo;
        } else {
            rear.next = nodo;
            nodo.prev = rear;
            rear = nodo;
        }
    }

    Voo retrieve() {
        if (front == null) {
            rear = null;
            System.out.println("A fila está vazia.");
            System.exit(0);
        }

        Voo aux = front.data;
        front = front.next;

        if (front != null) {
            front.prev = null;
        }

        return aux;
    }

    void destroy() {
        while (front != null) {
            Nodo aux = front;
            front = aux.next;
            aux = null;
        }

        rear = null;
    }

    List<Voo> getVoos() {
        List<Voo> voos = new ArrayList<>();
        Nodo aux = front;

        while (aux != null) {
            voos.add(aux.data);
            aux = aux.next;

        }
        return voos;
    }

    void mostrar() {
        Nodo aux = front;

        while (aux != null) {
            System.out.println(aux.data);
            aux = aux.next;
        }
    }

    public int getPrioridade(String status) {

        int prioridade = 0;

        switch (status) {
            case "Confirmado":
                prioridade = 1;
                break;
            case "Previsto":
                prioridade = 2;
                break;
            case "Atrasado":
                prioridade = 3;
                break;
            case "Cancelado":
                prioridade = 4;
                break;
            default:
                prioridade = 5; // Prioridade padrão para outros estados
                break;
        }
        return prioridade;
    }

    public void priorizarFila() {
        // Crie uma lista para cada nível de prioridade
        List<Voo>[] listasPrioridade = new ArrayList[5];
        for (int i = 0; i < 5; i++) {
            listasPrioridade[i] = new ArrayList<>();
        }

        // Separe os voos nas listas de acordo com a prioridade
        Nodo aux = front;
        while (aux != null) {
            Voo voo = aux.data;
            int prioridade = getPrioridade(voo.getStatus());
            listasPrioridade[prioridade - 1].add(voo);
            aux = aux.next;
        }

        // Limpe a fila atual
        destroy();

        // Recrie a fila a partir das listas de prioridade
        for (int i = 0; i < 5; i++) {
            for (Voo voo : listasPrioridade[i]) {
                store(voo);
            }
        }
        System.out.println(getVoos());
    }



    boolean isEmpty() {
        return rear == null;
    }
}
