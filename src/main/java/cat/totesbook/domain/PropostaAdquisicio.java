package cat.totesbook.domain;

/**
 * Classse que representa l'entitat PropostaAdquisició a la BD.
 * 
 * @author equip TotEsBook
 */
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PropostesAdquisicio")
public class PropostaAdquisicio {

    public enum EstatProposta {
        pendent, acceptada, rebutjada, comprat
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProposta;

    @ManyToOne
    @JoinColumn(name = "idUsuari", nullable = false)
    private Usuari usuari;

    @Column(nullable = false)
    private String titol;

    private String autor;
    private String isbn;
    private String editorial;
    private String motiu;

    @Column(nullable = false)
    private LocalDateTime dataProposta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstatProposta estat = EstatProposta.pendent;

    private String resposta;

    public PropostaAdquisicio() {
    }

    // Getters i Setters
    public int getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(int idProposta) {
        this.idProposta = idProposta;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getMotiu() {
        return motiu;
    }

    public void setMotiu(String motiu) {
        this.motiu = motiu;
    }

    public LocalDateTime getDataProposta() {
        return dataProposta;
    }

    public void setDataProposta(LocalDateTime dataProposta) {
        this.dataProposta = dataProposta;
    }

    public EstatProposta getEstat() {
        return estat;
    }

    public void setEstat(EstatProposta estat) {
        this.estat = estat;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
