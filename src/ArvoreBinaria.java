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

    public void inserir(Voo voo) {
        root = inserirRec(root, voo);
    }

    private Nodo inserirRec(Nodo root, Voo voo) {
        if (root == null) {
            root = new Nodo(voo);
            return root;
        }

        int comparacao = compareCodigos(voo.getCodigo(), root.data.getCodigo());

        if (comparacao < 0) {
            root.left = inserirRec(root.left, voo);
        } else if (comparacao > 0) {
            root.right = inserirRec(root.right, voo);
        }

        return root;
    }

    public Voo buscar(String codigo) {
        return buscarRec(root, codigo);
    }

    private Voo buscarRec(Nodo root, String codigo) {
        if (root == null) {
            System.out.println("Nó nulo encontrado.");
            return null;
        }

        System.out.println("Comparando códigos: " + codigo + " com " + root.data.getCodigo());

        if (root.data.getCodigo().equals(codigo)) {
            System.out.println("Código encontrado: " + codigo);
            return root.data;
        }

        int comparacao = compareCodigos(codigo, root.data.getCodigo());

        if (comparacao < 0) {
            System.out.println("Indo para o nó esquerdo.");
            return buscarRec(root.left, codigo);
        } else if (comparacao > 0) {
            System.out.println("Indo para o nó direito.");
            return buscarRec(root.right, codigo);
        } else {
            System.out.println("Códigos são iguais.");
            return root.data;
        }
    }


    public void excluir(String codigo) {
        root = excluirRec(root, codigo);
    }

    private Nodo excluirRec(Nodo root, String codigo) {
        if (root == null) {
            return root;
        }

        int comparacao = compareCodigos(codigo, root.data.getCodigo());

        if (comparacao < 0) {
            root.left = excluirRec(root.left, codigo);
        } else if (comparacao > 0) {
            root.right = excluirRec(root.right, codigo);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.data = minValue(root.right);
            root.right = excluirRec(root.right, root.data.getCodigo());
        }

        return root;
    }

    private Voo minValue(Nodo node) {
        Voo minValue = node.data;
        while (node.left != null) {
            minValue = node.left.data;
            node = node.left;
        }
        return minValue;
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
                // Se for uma letra, adicione o valor da letra no total
                total += Character.toUpperCase(c) - 'A' + 1;
            } else if (Character.isDigit(c)) {
                // Se for um número, adicione o valor do dígito no total
                total += Character.getNumericValue(c);
            }
        }
        return total;
    }

}
