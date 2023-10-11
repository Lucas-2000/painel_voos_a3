import java.util.ArrayList;
import java.util.List;

public class Fila {
    static class Nodo {
        Voo data;
        Nodo link;
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
            rear.link = nodo;
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
        front = front.link;

        return aux;
    }

    void destroy() {
        while (front != null) {
            Nodo aux = front;
            front = aux.link;
            aux = null;
        }

        rear = null;
    }

    List<Voo> getVoos() {
        List<Voo> voos = new ArrayList<>();
        Nodo aux = front;

        while (aux != null) {
            voos.add(aux.data);
            aux = aux.link;
        }

        return voos;
    }

    void mostrar() {
        Nodo aux = front;

        while (aux != null) {
            System.out.println(aux.data);
            aux = aux.link;
        }
    }

    boolean isFull() {
        Nodo nextRear = rear.link;
        return nextRear == front;
    }

    boolean isEmpty() {
        return rear == null;
    }

}
