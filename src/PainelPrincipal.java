import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PainelPrincipal extends JFrame {
    private JPanel MainPanel;
    private JPanel NovoVooPanel;
    private JPanel DashboardPanel;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JLabel lblHorario;
    private JTextField txtHorário;
    private JButton btnCadastrar;
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

    static Fila fila = new Fila();

    public PainelPrincipal() {
        setContentPane(MainPanel);
        setTitle("Painel Principal");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        criarTabela();

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarNaFila();
            }
        });
    }

    private void adicionarNaFila() {
        String codigo = txtCodigo.getText();
        String companhia = txtCompanhia.getText();
        String destino = txtDestino.getText();
        String portao = txtPortao.getText();
        String horario = txtHorário.getText();
        String status = cbStatus.getSelectedItem().toString();

        if (!codigo.isEmpty() && !companhia.isEmpty() && !destino.isEmpty() &&
                !portao.isEmpty() && !horario.isEmpty() && status != "Selecione")  {

            Voo novoVoo = new Voo(codigo, companhia, destino, portao, horario, status);
            fila.store(novoVoo);

            txtCodigo.setText("");
            txtCompanhia.setText("");
            txtDestino.setText("");
            txtPortao.setText("");
            txtHorário.setText("");
            cbStatus.setSelectedIndex(0);

            //chama métodos
            fila.priorizarFila();
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de cadastrar.");
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

    public static void main(String[] args) {
        new PainelPrincipal();
    }
}
