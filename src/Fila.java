import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public Fila getFila() {
        return this;
    }

    public Voo getVoo(Nodo nodo) {
        return nodo.data;
    }

    public Nodo getNext(Nodo nodo) {
        return nodo.next;
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

//    public List<Voo> getVoosEExcluir() {
//        LocalTime horarioAtual = LocalTime.now();
//
//        // Lista para armazenar os voos a serem excluídos
//        List<Voo> voosExcluidos = new ArrayList<>();
//
//        // Percorre toda a fila
//        while (front != null && horarioAtual.isAfter(LocalTime.parse(front.data.getHorario(), DateTimeFormatter.ofPattern("HH:mm")))) {
//            Voo vooASerExcluido = front.data;
//            excluir(vooASerExcluido.getCodigo());
//            voosExcluidos.add(vooASerExcluido);
//        }
//
//        return voosExcluidos;
//    }

    public List<Voo> getVoosEExcluir() {
        LocalTime horarioAtual = LocalTime.now();
        LocalTime horarioLimite = horarioAtual.minusMinutes(60);
        LocalTime horarioLimiteParaDecolando = horarioAtual.minusMinutes(5);

        // Lista para armazenar os voos a serem excluídos
        List<Voo> voosExcluidos = new ArrayList<>();

        // Cópia da fila para iterar sobre ela
        Nodo current = front;

        while (current != null) {
            LocalTime horarioDoVoo = LocalTime.parse(current.data.getHorario(), DateTimeFormatter.ofPattern("HH:mm"));

            if (horarioDoVoo.isBefore(horarioLimite) && !current.data.getStatus().equals("Decolando") && !current.data.getStatus().equals("Atrasado")) {
                current.data.setStatus("Atrasado");
            }

            if (horarioDoVoo.isBefore(horarioLimiteParaDecolando) && current.data.getStatus().equals("Decolando")) {
                Voo vooASerExcluido = current.data;
                excluir(vooASerExcluido.getCodigo());
                voosExcluidos.add(vooASerExcluido);
            }


            current = current.next;
        }

        return voosExcluidos;
    }

    boolean isEmpty() {
        return rear == null;
    }
}
