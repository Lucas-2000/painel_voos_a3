import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Voo {
    private String codigo;
    private String companhia;
    private String destino;
    private String portao;
    private String horario;
    private String status;

    public Voo(String codigo, String companhia, String destino, String portao, String horario, String status) {
        this.codigo = codigo;
        this.companhia = companhia;
        this.destino = destino;
        this.portao = portao;
        this.horario = horario;
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCompanhia() {
        return companhia;
    }

    public void setCompanhia(String companhia) {
        this.companhia = companhia;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPortao() {
        return portao;
    }

    public void setPortao(String portao) {
        this.portao = portao;
    }

    public String getHorario() {
        LocalTime localTime = LocalTime.parse(horario, DateTimeFormatter.ofPattern("HH:mm"));
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{Código: " + codigo +
                ", Companhia: " + companhia +
                ", Destino: " + destino +
                ", Portão: " + portao +
                ", Horário: " + horario +
                ", Status: " + status + "}";
    }
}
