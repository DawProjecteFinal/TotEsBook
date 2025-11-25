/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "PropostesAdquisicio")
public class PropostaAdquisicio {

    public enum EstatProposta {
        pendent, acceptada, rebutjada, comprat
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProposta;

    @Column(name = "idUsuari", nullable = false)
    private Integer idUsuari;

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

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
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

    /**
     * Per a formatar les dates
     * @return
     */
    public String getDataPropostaFormatted() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataProposta != null ? dataProposta.format(fmt) : "";
    }

}
