public class Fila {
    private int tamanho, front, rear;
    private Voo[] vet;

    public Fila(){
        this.tamanho = 5;
        this.vet = new Voo[5];
        this.front = 0;
        this.rear = 0;
    }

    public Fila(int tamanho) {
        this.tamanho = tamanho;
        this.vet = new Voo[tamanho];
        this.front = 0;
        this.rear = 0;
    }

    public int next(int n) {
        if(n == tamanho-1)
            return 0;
        else
            return n+1;
        // return (n+1)%tamanho;
    }

    public boolean isFull(){
        if (next(rear) == front)
            return true;
        else
            return false;
    }

    public boolean isEmpty(){
        if (rear == front)
            return true;
        else
            return false;
    }

    public void store(Voo elem){
        if(isFull()){
            System.out.println("Overflow!");
            System.exit(1);
        }
        else {
            vet[rear]=elem;
            rear = next(rear);
        }
    }

    public Voo retrieve(){
        if(isEmpty()){
            System.out.println("Underflow!");
            System.exit(1);
            //return '*';
        }
        //else
        Voo aux = vet[front];
        front = next(front);
        return aux;
    }

    public void destroy(){
        front = rear;
    }
}
