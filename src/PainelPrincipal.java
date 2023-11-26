import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;


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
    private JButton btnAbrirUsuario;
    private JLabel lblHorarioAtual;

    static Fila fila = new Fila();
    static ArvoreBinaria arvore = new ArvoreBinaria();

    public PainelPrincipal() {
        setContentPane(MainPanel);
        setTitle("Painel Principal");
        setMinimumSize(new Dimension(600, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        criarTabela();

        ActionListener atualizaVoos = e -> {
            var voos = fila.getVoosEExcluir();

            for (Voo voo : voos) {
                arvore.excluir(voo.getCodigo());
            }

            if (!fila.isEmpty()) {
                atualizarTabela();
            } else {
                atualizarTabela();
                System.out.println("Nenhum voo disponível na fila.");
            }
            System.out.println("Atualizando...");
        };
        new Timer(30*1000, atualizaVoos).start();

        ActionListener calculaDataHoraAtual = e -> {
            ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
            ZonedDateTime dataAtual = ZonedDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHoraFormatada = dataAtual.format(formatter);
            lblHorarioAtual.setText(dataHoraFormatada);
        };
        new Timer(1*1000, calculaDataHoraAtual).start();

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

        btnAbrirUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PainelUsuario painelUsuario = new PainelUsuario();
                painelUsuario.setVisible(true);
            }
        });
    }

    private void adicionarNaFila() {
        String codigo = txtCodigo.getText().toUpperCase();
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

        if (!validaPortao(portao)) {
            JOptionPane.showMessageDialog(this, "O Portão deve ter de 1 a 3 números inteiros");
            return;
        }

        Voo novoVoo = new Voo(codigo, companhia, destino, portao, horario, status);

        try {
            arvore.inserir(novoVoo);
        } catch(RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao inserir o voo: " + e.getMessage());
            return;
        }

        fila.store(novoVoo);

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
        String codigo = txtCodigo.getText().toUpperCase();
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

        if (!validaPortao(portao)) {
            JOptionPane.showMessageDialog(this, "O Portão deve ter de 1 a 3 números inteiros");
            return;
        }

        Voo vooExistente = arvore.buscar(codigo);

        if (vooExistente != null) {
            Voo novoVoo = new Voo(codigo, companhia, destino, portao, horario, status);

            arvore.excluir(codigo);

            fila.atualizar(codigo, novoVoo);

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
        Fila voos = fila.getFila();

        DefaultTableModel model = (DefaultTableModel) tblVoos.getModel();
        model.setRowCount(0);

        var currentNode = voos.front;
        while (currentNode != null) {
            Voo voo = voos.getVoo(currentNode);
            Object[] vooData = {voo.getCodigo(), voo.getCompanhia(), voo.getDestino(), voo.getPortao(), voo.getHorario(), voo.getStatus()};
            model.addRow(vooData);
            currentNode = voos.getNext(currentNode);
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
            String regex = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";
            if(!horario.matches(regex)) return false;
            LocalTime.parse(horario, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }


    }

    public static boolean validaCodigo(String codigo) {
        // Regex para validar se o código possui duas letras seguidos de 3 números
        String regex = "^[A-Za-z]{2}\\d{3}$";
        return codigo.matches(regex);
    }

    public static boolean validaPortao(String portao) {
        // Regex para validar se o código possui duas letras seguidos de 3 números
        String regex = "^\\d{1,3}$";
        return portao.matches(regex);
    }








    public static void main(String[] args) {
        new PainelPrincipal();
    }
}
