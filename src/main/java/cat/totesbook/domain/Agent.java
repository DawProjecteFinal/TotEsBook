package cat.totesbook.domain;

import jakarta.persistence.*; // Imports JPA

/**
 * Classe que representa un agent.
 * 
 * @author Equip TotEsBook
 */
@Entity // Indiquem que és una entitat JPA
@Table(name = "Agents") // Nom de la taula
public class Agent {

    /**
     * Enum per al camp 'tipus'.
     */
    public enum TipusAgent {
        /** Agent del tipus bibliotecari.*/
        bibliotecari,
        
        /** Agent del tipus administrador.*/
        administrador
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAgent;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 150)
    private String cognoms;

    @Column(length = 20)
    private String telefon;

    @Column(unique = true, length = 150) // Fem l'email unique
    private String email;

    @Enumerated(EnumType.STRING) // Guardem l'Enum com a String a la BBDD
    @Column(nullable = false)
    private TipusAgent tipus;

    @Column(nullable = false, length = 255)
    private String contrasenya;

    
    // Relació amb la biblioteca on treballa aquest agent
    @ManyToOne(fetch = FetchType.LAZY)  // Una biblioteca pot tenir molts Agents
    @JoinColumn(name = "idBiblioteca") // nom de la columna FK a la taula Agents
    private Biblioteca biblioteca;

    /**
     * Constructor buit requerit per JPA
     */
    public Agent() {
    }

    /**
     * Constructor de la classe Agent.
     * 
     * @param nom Nom de l'agent.
     * @param cognoms Cognoms de l'agent.
     * @param email Correu electrònic de l'agent.
     * @param contrasenya Contrasenya de l'agent.
     * @param tipus Tipus d'agent.
     * @param biblioteca Biblioteca a la qual està assignat l'agent.
     */
    public Agent(String nom, String cognoms, String email, String contrasenya, TipusAgent tipus, Biblioteca biblioteca) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.contrasenya = contrasenya;
        this.tipus = tipus;
        this.biblioteca = biblioteca;
    }

    // --- Getters i Setters ---
    
    /**
     * Retorna l'ID de l'agent.
     * 
     * @return L'ID de l'agent.
     */
    public int getIdAgent() {
        return idAgent;
    }
    
    /**
     * Assigna un id a l'agent.
     * 
     * @param idAgent L'ID de l'agent.
     */
    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    /**
     * Retorna el nom de l'agent.
     * 
     * @return nom de l'agent.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Assigna un nom a l'agent.
     * 
     * @param nom nom de l'agent.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna els cognoms de l'agent.
     * 
     * @return cognoms de l'agent.
     */
    public String getCognoms() {
        return cognoms;
    }

    /**
     * Assigna els cognoms a l'agent.
     * 
     * @param cognoms cognoms a l'agent.
     */
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    /**
     * Retorna el telèfon de l'agent.
     * 
     * @return telèfon de l'agent.
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Assigna un telèfon de l'agent.
     * 
     * @param telefon telèfon de l'agent.
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Retorna el correu electrònic de l'agent.
     * 
     * @return correu electrònic de l'agent.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Assigna un correu electrònic de l'agent.
     * 
     * @param email correu electrònic de l'agent.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna el tipus d'agent.
     * 
     * @return tipus d'agent.
     */
    public TipusAgent getTipus() {
        return tipus;
    }

    /**
     * Assigna un tipus d'agent.
     * 
     * @param tipus tipus d'agent.
     */
    public void setTipus(TipusAgent tipus) {
        this.tipus = tipus;
    }

    /**
     * Retorna la contrasenya l'agent.
     * 
     * @return contrasenya de l'agent.
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Assigna una contrasenya l'agent.
     * 
     * @param contrasenya contrasenya l'agent.
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Retorna una biblioteca assignada de l'agent.
     * 
     * @return una biblioteca assignada de l'agent.
     */
    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    /**
     * Assigna una biblioteca assignada de l'agent.
     * 
     * @param biblioteca una biblioteca assignada de l'agent.
     */
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    /**
     * Mètode que retorna un toString (opcional, útil per a logs).
     * 
     * @return cadena amb id, nom, cognoms, correu electrònic i tipus d'agent.
     */
    @Override
    public String toString() {
        return "Agent{" + "idAgent=" + idAgent + ", nom=" + nom + ", cognoms=" + cognoms + ", email=" + email + ", tipus=" + tipus + '}';
    }
}
