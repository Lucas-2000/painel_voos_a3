import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class PainelPrincipal extends JFrame {
    private JPanel MainPanel;
    private JPanel NovoVooPanel;
    private JPanel DashboardPanel;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JLabel lblHorario;
    private JTextField txtHorario;
    private JButton btnSalvar;
    private JLabel lblDestino;
    private JTextField txtDestino;
    private JComboBox cbStatus;
    private JLabel lblStatus;
    private JLabel lblVoos;
    private JSeparator separator;
    private JTextField txtCompanhia;
    private JTextField txtPortao;
    private JLabel lblCompanhia;
    private JLabel lblPortao;
    private JTable tblVoos;
    private JComboBox cbTipo;
    private JLabel lblTitulo;

    static Fila fila = new Fila();
    static ArvoreBinaria arvore = new ArvoreBinaria();

    public PainelPrincipal() {
        setContentPane(MainPanel);
        setTitle("Painel Principal");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        criarTabela();

        ActionListener atualizaVoos = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Voo proximoVoo = fila.getProximoVooEExcluir();
                if (proximoVoo != null) {
                    System.out.println("Próximo voo: " + proximoVoo.getCodigo());
                    atualizarTabela();
                } else {
                    System.out.println("Nenhum voo disponível na fila.");
                }
                System.out.println("Atualizando...");
            };
        };
        new Timer(30*1000, atualizaVoos).start();

        cbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcaoSelecionada = cbTipo.getSelectedItem().toString();
                switch (opcaoSelecionada) {
                    case "Cadastrar":
                        txtCodigo.setVisible(true);
                        lblCodigo.setVisible(true);
                        txtHorario.setVisible(true);
                        lblHorario.setVisible(true);
                        txtDestino.setVisible(true);
                        lblDestino.setVisible(true);
                        txtCompanhia.setVisible(true);
                        lblCompanhia.setVisible(true);
                        txtPortao.setVisible(true);
                        lblPortao.setVisible(true);
                        cbStatus.setVisible(true);
                        lblStatus.setVisible(true);
                        btnSalvar.setVisible(true);
                        lblTitulo.setText("Cadastre o voo");
                        break;
                    case "Atualizar":
                        txtCodigo.setVisible(true);
                        lblCodigo.setVisible(true);
                        txtHorario.setVisible(true);
                        lblHorario.setVisible(true);
                        txtDestino.setVisible(true);
                        lblDestino.setVisible(true);
                        txtCompanhia.setVisible(true);
                        lblCompanhia.setVisible(true);
                        txtPortao.setVisible(true);
                        lblPortao.setVisible(true);
                        cbStatus.setVisible(true);
                        lblStatus.setVisible(true);
                        btnSalvar.setVisible(true);
                        lblTitulo.setText("Atualize o voo");
                        break;
                    case "Excluir":
                        txtCodigo.setVisible(true);
                        lblCodigo.setVisible(true);
                        txtHorario.setVisible(false);
                        lblHorario.setVisible(false);
                        txtDestino.setVisible(false);
                        lblDestino.setVisible(false);
                        txtCompanhia.setVisible(false);
                        lblCompanhia.setVisible(false);
                        txtPortao.setVisible(false);
                        lblPortao.setVisible(false);
                        cbStatus.setVisible(false);
                        lblStatus.setVisible(false);
                        btnSalvar.setVisible(true);
                        lblTitulo.setText("Exclua o voo");
                        break;
                }
            }
        });


        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcaoSelecionada = cbTipo.getSelectedItem().toString();
                switch (opcaoSelecionada) {
                    case "Cadastrar":
                        adicionarNaFila();
                        break;
                    case "Atualizar":
                        atualizarVoo();
                        break;
                    case "Excluir":
                        excluirVoo();
                        break;
                }
            }
        });
    }

    private void adicionarNaFila() {
        String codigo = txtCodigo.getText();
        String companhia = txtCompanhia.getText();
        String destino = txtDestino.getText();
        String portao = txtPortao.getText();
        String horario = txtHorario.getText();
        String status = cbStatus.getSelectedItem().toString();

        if (codigo.isEmpty() || companhia.isEmpty() || destino.isEmpty() || portao.isEmpty() || horario.isEmpty() || status.equals("Selecione")) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de cadastrar.");
            return;
        }

        if (!validaFormatoHorario(horario)) {
            JOptionPane.showMessageDialog(this, "Formato incorreto do horario, utilize o padrão HH:mm");
            return;
        }

        if (!validaCodigo(codigo)) {
            JOptionPane.showMessageDialog(this, "Formato incorreto do codigo, utilize o padrão AA111");
            return;
        }

        Voo novoVoo = new Voo(codigo.toUpperCase(), companhia, destino, portao, horario, status);
        fila.store(novoVoo);
        arvore.inserir(novoVoo);

        txtCodigo.setText("");
        txtCompanhia.setText("");
        txtDestino.setText("");
        txtPortao.setText("");
        txtHorario.setText("");
        cbStatus.setSelectedIndex(0);

        // Chama métodos
        fila.priorizarFila();
        atualizarTabela();
    }

    public void atualizarVoo() {
        String codigo = txtCodigo.getText();
        String companhia = txtCompanhia.getText();
        String destino = txtDestino.getText();
        String portao = txtPortao.getText();
        String horario = txtHorario.getText();
        String status = cbStatus.getSelectedItem().toString();

        if (codigo.isEmpty() || companhia.isEmpty() || destino.isEmpty() || portao.isEmpty() || horario.isEmpty() || status.equals("Selecione")) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de cadastrar.");
            return;
        }

        if (!validaFormatoHorario(horario)) {
            JOptionPane.showMessageDialog(this, "Formato incorreto do horario, utilize o padrão HH:mm");
            return;
        }

        if (!validaCodigo(codigo)) {
            JOptionPane.showMessageDialog(this, "Formato incorreto do codigo, utilize o padrão AA111");
            return;
        }

        Voo vooExistente = arvore.buscar(codigo);

        if (vooExistente != null) {
            Voo novoVoo = new Voo(codigo.toUpperCase(), companhia, destino, portao, horario, status);
            arvore.excluir(codigo);

            fila.atualizar(codigo, novoVoo);

            arvore.inserir(novoVoo);

            // Chama métodos
            fila.priorizarFila();
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Voo não encontrado.");
        }


    }

    public void excluirVoo() {
        String codigo = txtCodigo.getText().toUpperCase();

        Voo vooExistente = arvore.buscar(codigo);

        if (vooExistente != null) {
            arvore.excluir(codigo);

            fila.excluir(codigo);

            // Chama métodos
            fila.priorizarFila();
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Voo não encontrado.");
        }
    }

    private void atualizarTabela() {
        List<Voo> voos = fila.getVoos();

        DefaultTableModel model = (DefaultTableModel) tblVoos.getModel();

        model.setRowCount(0);
        for (Voo voo : voos) {
            Object[] vooData = {voo.getCodigo(), voo.getCompanhia(), voo.getDestino(), voo.getPortao(), voo.getHorario(), voo.getStatus()};
            model.addRow(vooData);
        }
    }

    private void criarTabela() {
        tblVoos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Companhia", "Destino", "Portão", "Horário", "Status"}
        ));
    }

    private boolean validaFormatoHorario(String horario) {
        // Validar formatação do horário
        try {
            LocalTime.parse(horario, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validaCodigo(String codigo) {
        // Regex para validar se o código possui duas letras seguidos de 3 números
        String regex = "^[A-Za-z]{2}\\d{3}$";
        return codigo.matches(regex);
    }



    public static void main(String[] args) {
        new PainelPrincipal();
    }
}
