package cat.totesbook.domain;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Import per formatar la data

/**
 * Classe que representa un préstec.
 * 
 * @author Equip TotEsBook
 */
@Entity
@Table(name = "Prestecs")
public class Prestec {

    /**
     * Enum per al camp 'estat'.
     */
    public enum EstatPrestec {
        /**Estat actiu*/
        actiu, 
        /**Estat retornat*/
        retornat, 
        
        /**Estat retard*/
        retard, 
        
        /**Estat perdut*/
        perdut
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

    // Aquest camp és BUIT/NULL fins que l'usuari retorna el llibre.
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

    /**
     * Constructor buit requerit per JPA
     */
    public Prestec() {
    }

    /**
     * Constructor de la classe Prestec.
     * 
     * @param usuari usuari.
     * @param llibre llibre.
     * @param biblioteca biblioteca.
     * @param agentPrestec agent que fa el préstec.
     */
    public Prestec(Usuari usuari, Llibre llibre, Biblioteca biblioteca, Agent agentPrestec) {
        this.usuari = usuari;
        this.llibre = llibre;
        this.biblioteca = biblioteca;
        this.agentPrestec = agentPrestec;
        this.dataPrestec = LocalDateTime.now();
        this.estat = EstatPrestec.actiu;
    }

    // --- MÈTODE PER A FORMATAR LA DATA ---
    
    /**
     * Mètode que formata la data de prèstec.
     * 
     * @return data de prèstec.
     */
    public String getDataPrestecFormatted() {
        if (dataPrestec == null) return "";
        return dataPrestec.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    /**
     * Mètode que formata la data de venciment.
     * 
     * @return data de venciment.
     */
    public String getDataVencimentCalculada() {
        if (dataPrestec != null) {
            return dataPrestec.plusDays(30).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "";
    }
    /*
    public String getDataVencimentFormatted() {
        if (dataDevolucio == null) return "";
        LocalDateTime venciment = dataPrestec.plusDays(30);
        return venciment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    */
    
    /**
     * Mètode que formata la data de devolució.
     * 
     * @return data de devolució
     */
    public String getDataDevolucioFormatted() {
        if (dataDevolucio == null) return "";
        //LocalDateTime devolucio = dataPrestec.plusDays(30);
        return dataDevolucio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    // ---------------------------------------------------
    
    // Getters i Setters
    
    /**
     * Retorna el id el préstec.
     * @return id el préstec.
     */
    public int getIdPrestec() {
        return idPrestec;
    }

    /**
     * Assigna un id el préstec.
     * 
     * @param idPrestec id el préstec.
     */
    public void setIdPrestec(int idPrestec) {
        this.idPrestec = idPrestec;
    }

    /**
     * Retorna el usuari.
     * 
     * @return usuari.
     */
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Assigna un usuari.
     * 
     * @param usuari usuari.
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Retorna el llibre.
     * 
     * @return llibre.
     */
    public Llibre getLlibre() {
        return llibre;
    }

    /**
     * Assigna un llibre.
     * 
     * @param llibre llibre.
     */
    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    /**
     * Retorna la biblioteca.
     * 
     * @return biblioteca.
     */
    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    /**
     * Assigna una biblioteca.
     * 
     * @param biblioteca biblioteca.
     */
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    /**
     * Retorna la data del préstec.
     * 
     * @return data del préstec.
     */
    public LocalDateTime getDataPrestec() {
        return dataPrestec;
    }

    /**
     * Assigna una data del préstec.
     * 
     * @param dataPrestec data del préstec.
     */
    public void setDataPrestec(LocalDateTime dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    /**
     * Retorna la data de devolució.
     * 
     * @return data de devolució.
     */
    public LocalDateTime getDataDevolucio() {
        return dataDevolucio;
    }

    /**
     * Assigna una data de devolució.
     * 
     * @param dataDevolucio data de devolució.
     */
    public void setDataDevolucio(LocalDateTime dataDevolucio) {
        this.dataDevolucio = dataDevolucio;
    }

    /**
     * Retorna el agent que fa el préstec.
     * 
     * @return agent que fa el préstec.
     */
    public Agent getAgentPrestec() {
        return agentPrestec;
    }

    /**
     * Assigna un agent que fa el préstec.
     * 
     * @param agentPrestec agent que fa el préstec.
     */
    public void setAgentPrestec(Agent agentPrestec) {
        this.agentPrestec = agentPrestec;
    }

    /**
     * Retorna agent que fa la devolució.
     * 
     * @return agent que fa la devolució.
     */
    public Agent getAgentDevolucio() {
        return agentDevolucio;
    }

    /**
     * Assigna un agent que fa la devolució.
     * 
     * @param agentDevolucio agent que fa la devolució.
     */
    public void setAgentDevolucio(Agent agentDevolucio) {
        this.agentDevolucio = agentDevolucio;
    }

    /**
     * Retorna el estat del préstec.
     * 
     * @return estat del préstec.
     */
    public EstatPrestec getEstat() {
        return estat;
    }

    /**
     * Assigna un estat del préstec.
     * 
     * @param estat estat del préstec.
     */
    public void setEstat(EstatPrestec estat) {
        this.estat = estat;
    }

    /**
     * Retorna les dates dels préstecs formatades a String
     * 
     * @return dades formatades dels préstecs.
     */
    public String getDataPrestecFormatada() {
        return dataPrestec == null ? ""
                : dataPrestec.toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Retorna les dates de les devolucions formatades a String
     * 
     * @return dates de les devolucions
     */
    public String getDataDevolucioFormatada() {
        return dataDevolucio == null ? ""
                : dataDevolucio.toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Retorna les dates dels venciments dels llibres formatades a String
     * 
     * @return dates dels venciments dels llibres
     */
    public String getDataVencimentFormatada() {
        if (dataPrestec == null) {
            return "";
        }

        LocalDate venciment = dataPrestec.plusDays(30).toLocalDate();
        return venciment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
