import java.util.Stack;

public class ArvoreBinaria {
    private static class Nodo {
        Voo data;
        Nodo left;
        Nodo right;

        public Nodo(Voo data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    Nodo root;

    public ArvoreBinaria() {
        root = null;
    }

    public void inserir(Voo voo) throws RuntimeException {
        Nodo novoNodo = new Nodo(voo);
        if (root == null) {
            root = novoNodo;
            return;
        }

        Nodo currentNode = root;
        while (true) {
            int comparacao = compareCodigos(voo.getCodigo(), currentNode.data.getCodigo());
            if (comparacao < 0) {
                if (currentNode.left == null) {
                    currentNode.left = novoNodo;
                    return;
                }
                currentNode = currentNode.left;
            } else if (comparacao > 0) {
                if (currentNode.right == null) {
                    currentNode.right = novoNodo;
                    return;
                }
                currentNode = currentNode.right;
            } else {
                throw new RuntimeException("Código do voo já existe.");
            }
        }
    }


    public Voo buscar(String codigo) {
        Nodo currentNode = root;
        while (currentNode != null) {
            int comparacao = compareCodigos(codigo, currentNode.data.getCodigo());
            if (comparacao == 0) {
                return currentNode.data;
            } else if (comparacao < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return null;
    }

    public void excluir(String codigo) {
        Nodo parentNode = null;
        Nodo currentNode = root;

        while (currentNode != null) {
            int comparacao = compareCodigos(codigo, currentNode.data.getCodigo());
            if (comparacao == 0) {
                // Código encontrado
                if (currentNode.left == null) {
                    if (parentNode == null) {
                        root = currentNode.right;
                    } else if (currentNode == parentNode.left) {
                        parentNode.left = currentNode.right;
                    } else {
                        parentNode.right = currentNode.right;
                    }
                    return;
                } else if (currentNode.right == null) {
                    if (parentNode == null) {
                        root = currentNode.left;
                    } else if (currentNode == parentNode.left) {
                        parentNode.left = currentNode.left;
                    } else {
                        parentNode.right = currentNode.left;
                    }
                    return;
                }

                Nodo minValueNode = minValue(currentNode.right);
                currentNode.data = minValueNode.data;
                codigo = minValueNode.data.getCodigo();
                parentNode = currentNode;
                currentNode = currentNode.right;
            } else if (comparacao < 0) {
                parentNode = currentNode;
                currentNode = currentNode.left;
            } else {
                parentNode = currentNode;
                currentNode = currentNode.right;
            }
        }

        // Código não foi encontrado na árvore
        System.out.println("Código não encontrado na árvore: " + codigo);
    }

    private Nodo minValue(Nodo node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private int compareCodigos(String codigo1, String codigo2) {
        int total1 = calcularTotalCodigo(codigo1);
        int total2 = calcularTotalCodigo(codigo2);

        return Integer.compare(total1, total2);
    }

    private int calcularTotalCodigo(String codigo) {
        int total = 0;
        for (int i = 0; i < codigo.length(); i++) {
            char c = codigo.charAt(i);
            if (Character.isLetter(c)) {
                total += Character.toUpperCase(c) - 'A' + 1;
            } else if (Character.isDigit(c)) {
                total += Character.getNumericValue(c);
            }
        }
        return total;
    }
}
