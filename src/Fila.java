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

    boolean isEmpty() {
        return rear == null;
    }
}
