import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

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

    public PainelPrincipal() {
        setContentPane(MainPanel);
        setTitle("Painel Principal");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        createTable();
    }

    private void createTable() {
        Object[][] data = {
            {"2581", "Azul", "São Paulo", "Portão 1", "09:00", "Confirmado"},
            {"3900", "Latam", "Alagoas", "Portão 2", "10:30", "Atrasado"},
            {"1445", "Gol", "Sergipe", "Portão 3", "11:45", "Cancelado"},
        };

        tblVoos.setModel(new DefaultTableModel(
                data,
                new String[]{"Código", "Companhia", "Destino", "Portão", "Horário", "Status"}
        ));
    }

    public static void main(String[] args) {
        new PainelPrincipal();
    }
}
