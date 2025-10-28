package cat.totesbook.domain;

/**
 * Classe que representa l'entitat Prestec a la BD.
 * 
 * @author equip TotEsBook
 */
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Prestecs")
public class Prestec {

    public enum EstatPrestec {
        actiu, retornat, retard, perdut
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestec;

    @ManyToOne
    @JoinColumn(name = "idUsuari", nullable = false)
    private Usuari usuari;

    @ManyToOne
    @JoinColumn(name = "isbn", nullable = false)
    private Llibre llibre;

    @ManyToOne
    @JoinColumn(name = "idBiblioteca", nullable = false)
    private Biblioteca biblioteca;

    @Column(nullable = false)
    private LocalDateTime dataPrestec;

    private LocalDateTime dataDevolucio;

    @ManyToOne
    @JoinColumn(name = "idAgentPrestec")
    private Agent agentPrestec;

    @ManyToOne
    @JoinColumn(name = "idAgentDevolucio")
    private Agent agentDevolucio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstatPrestec estat = EstatPrestec.actiu;

    public Prestec() {
    }

    // Getters i Setters
    public int getIdPrestec() {
        return idPrestec;
    }

    public void setIdPrestec(int idPrestec) {
        this.idPrestec = idPrestec;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public LocalDateTime getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(LocalDateTime dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public LocalDateTime getDataDevolucio() {
        return dataDevolucio;
    }

    public void setDataDevolucio(LocalDateTime dataDevolucio) {
        this.dataDevolucio = dataDevolucio;
    }

    public Agent getAgentPrestec() {
        return agentPrestec;
    }

    public void setAgentPrestec(Agent agentPrestec) {
        this.agentPrestec = agentPrestec;
    }

    public Agent getAgentDevolucio() {
        return agentDevolucio;
    }

    public void setAgentDevolucio(Agent agentDevolucio) {
        this.agentDevolucio = agentDevolucio;
    }

    public EstatPrestec getEstat() {
        return estat;
    }

    public void setEstat(EstatPrestec estat) {
        this.estat = estat;
    }
}
