import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PainelUsuario extends JFrame {
    private JTable tblVoosUser;
    private JTextField txtBuscar;
    private JPanel MainPanelUser;
    private JButton btnBuscar;
    private JButton btnLimpar;
    private JLabel lblHorarioAtual;


    public PainelUsuario(){
        setContentPane(MainPanelUser);
        setTitle("Painel Usuário");
        setMinimumSize(new Dimension(600, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);

        criarTabela();
        preencheTabela();

        ActionListener atualizaVoos = e -> {
            var voos = PainelPrincipal.fila.getVoosEExcluir();

            for (Voo voo : voos) {
                PainelPrincipal.arvore.excluir(voo.getCodigo());
            }

            if (!PainelPrincipal.fila.isEmpty()) {
                preencheTabela();
            } else {
                preencheTabela();

                System.out.println("Nenhum voo disponível na fila.");
            }
            txtBuscar.setText("");
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

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preencheTabela();
                txtBuscar.setText("");
            }
        });
    }

    private void buscar(){
        String codigo = txtBuscar.getText().toUpperCase();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o código para realizar a busca!");
            return;
        }

        if (!PainelPrincipal.validaCodigo(codigo)) {
            JOptionPane.showMessageDialog(this, "Formato incorreto do codigo, utilize o padrão AA111");
            return;
        }

        Voo vooExistente = PainelPrincipal.arvore.buscar(codigo);
        DefaultTableModel model = (DefaultTableModel) tblVoosUser.getModel();
        model.setRowCount(0);

        if (vooExistente != null) {
            String companhia = vooExistente.getCompanhia();
            String destino = vooExistente.getDestino();
            String portao = vooExistente.getDestino();
            String horario = vooExistente.getHorario();
            String status = vooExistente.getStatus();

            Voo novoVoo = new Voo(codigo, companhia, destino, portao, horario, status);

            Object[] vooData = {novoVoo.getCodigo(), novoVoo.getCompanhia(), novoVoo.getDestino(), novoVoo.getPortao(), novoVoo.getHorario(), novoVoo.getStatus()};
            model.addRow(vooData);
        } else {
            JOptionPane.showMessageDialog(this, "Voo não encontrado.");
        }


    }


    private void preencheTabela() {
        Fila voos = PainelPrincipal.fila.getFila();

        DefaultTableModel model = (DefaultTableModel) tblVoosUser.getModel();
        model.setRowCount(0);

        var currentNode = voos.front;
        while (currentNode != null) {
            Voo voo = voos.getVoo(currentNode);
            Object[] vooData = {voo.getCodigo(), voo.getCompanhia(), voo.getDestino(), voo.getPortao(), voo.getHorario(), voo.getStatus()};
            model.addRow(vooData);
            currentNode = voos.getNext(currentNode);
        }

        System.out.println(voos);
    }

    private void criarTabela() {
        tblVoosUser.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Companhia", "Destino", "Portão", "Horário", "Status"}
        ));

    }

}
