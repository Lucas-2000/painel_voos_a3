import javax.swing.*;

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
    private JList listVoosConfirmados;
    private JComboBox cbStatus;
    private JLabel lblStatus;
    private JList listVoosAtrasadosCancelados;
    private JLabel lblVoosConfirmados;
    private JLabel lblVoosAtrasadosCancelados;
    private JSeparator separator;
    private JTextField txtCompania;
    private JTextField txtPortao;
    private JLabel lblCompania;
    private JLabel lblPortao;

    public PainelPrincipal() {
        setContentPane(MainPanel);
        setTitle("Painel Principal");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PainelPrincipal();
    }
}
