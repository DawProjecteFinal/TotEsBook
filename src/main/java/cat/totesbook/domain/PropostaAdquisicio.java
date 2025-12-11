
package cat.totesbook.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa una proposta d'adquisició.
 * 
 * @author Equip TotEsBook
 */
@Entity
@Table(name = "PropostesAdquisicio")
public class PropostaAdquisicio {

    /**
     * Enum per al camp 'estat'.
     */
    public enum EstatProposta {
        /** Estat de la proposta pendent*/
        pendent, 
        
        /** Estat de la proposta acceptada.*/
        acceptada, 
        
        /** Estat de la proposta rebutjada*/
        rebutjada, 
        
        /** Estat de la proposta comprada*/
        comprat
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


    @Column(nullable = false)
    private String nomUsuari;


    public PropostaAdquisicio() {
    }

    // Getters i Setters
    /**
     * Retorna el id de la proposta.
     * 
     * @return id de la proposta.
     */
    public int getIdProposta() {
        return idProposta;
    }
    
    /**
     * Assigna un id de la proposta.
     * 
     * @param idProposta id de la proposta.
     */
    public void setIdProposta(int idProposta) {
        this.idProposta = idProposta;
    }

    /**
     * Retorna el id de l'usuari.
     * 
     * @return id de l'usuari.
     */
    public int getIdUsuari() {
        return idUsuari;
    }

    /**
     * Assigna un id de l'usuari.
     * 
     * @param idUsuari id de l'usuari.
     */
    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    /**
     * Retorna el títol del llibre.
     * 
     * @return títol del llibre.
     */
    public String getTitol() {
        return titol;
    }

    /**
     * Assigna un títol del llibre.
     * 
     * @param titol títol del llibre.
     */
    public void setTitol(String titol) {
        this.titol = titol;
    }

    /**
     * Retorna l'autor del llibre.
     * 
     * @return autor del llibre.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Assigna un autor del llibre.
     * 
     * @param autor autor del llibre.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Retorna el ISBN del llibre.
     * 
     * @return ISBN del llibre.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Assigna un ISBN del llibre.
     * 
     * @param isbn ISBN del llibre.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Retorna l'editorial del llibre.
     * 
     * @return editorial del llibre.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Assigna una editorial del llibre.
     * 
     * @param editorial editorial del llibre.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Retorna el motiu de la proposta.
     * 
     * @return motiu de la proposta.
     */
    public String getMotiu() {
        return motiu;
    }

    /**
     * Assigna un motiu de la proposta.
     * 
     * @param motiu motiu de la proposta.
     */
    public void setMotiu(String motiu) {
        this.motiu = motiu;
    }

    /**
     * Retorna la data de la proposta.
     * 
     * @return data de la proposta.
     */
    public LocalDateTime getDataProposta() {
        return dataProposta;
    }

    /**
     * Assigna una data de la proposta.
     * 
     * @param dataProposta data de la proposta.
     */
    public void setDataProposta(LocalDateTime dataProposta) {
        this.dataProposta = dataProposta;
    }

    /**
     * Retorna l'estat de la proposta.
     * 
     * @return estat de la proposta.
     */
    public EstatProposta getEstat() {
        return estat;
    }

    /**
     * Assigna un estat de la proposta.
     * 
     * @param estat estat de la proposta.
     */
    public void setEstat(EstatProposta estat) {
        this.estat = estat;
    }

    /**
     * Retorna la resposta a la proposta.
     * 
     * @return resposta de la proposta.
     */
    public String getResposta() {
        return resposta;
    }

    /**
     * Assigna una resposta a la proposta.
     * 
     * @param resposta resposta a la proposta.
     */
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    /**
     * Mètode que formata les dates de la proposta.
     * @return data de la proposta.
     */
    public String getDataPropostaFormatted() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataProposta != null ? dataProposta.format(fmt) : "";
    }

}
