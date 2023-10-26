import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Fila {
    private static class Nodo {
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

    public void priorizarFila() {
        List<Voo> voos = getVoos();

        destroy();

        Collections.sort(voos, new Comparator<Voo>() {
            @Override
            public int compare(Voo voo1, Voo voo2) {
                return voo1.getHorario().compareTo(voo2.getHorario());
            }
        });

        for (Voo voo : voos) {
            store(voo);
        }

        System.out.println(getVoos());
    }


    public boolean atualizar(String codigo, Voo novoVoo) {
        Nodo aux = front;

        while (aux != null) {
            if (aux.data.getCodigo().equals(codigo)) {
                aux.data = novoVoo;

                return true;
            }
            aux = aux.next;
        }

        return false;
    }

    public boolean excluir(String codigo) {
        Nodo aux = front;
        Nodo prev = null;

        while (aux != null) {
            if (aux.data.getCodigo().equals(codigo)) {
                if (prev == null) {
                    front = aux.next;
                } else {
                    prev.next = aux.next;
                }

                if (aux == rear) {
                    rear = prev;
                }

                return true;
            }

            prev = aux;
            aux = aux.next;
        }

        return false;
    }

    boolean isEmpty() {
        return rear == null;
    }
}
