/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.domain;

import jakarta.persistence.*; // Imports JPA

@Entity // Indiquem que és una entitat JPA
@Table(name = "Agents") // Nom de la taula
public class Agent {

    // Enum per al camp 'tipus'
    public enum TipusAgent {
        bibliotecari,
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

    // Constructor buit requerit per JPA
    public Agent() {
    }

    public Agent(String nom, String cognoms, String email, String contrasenya, TipusAgent tipus, Biblioteca biblioteca) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.contrasenya = contrasenya;
        this.tipus = tipus;
        this.biblioteca = biblioteca;
    }

    // --- Getters i Setters ---
    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipusAgent getTipus() {
        return tipus;
    }

    public void setTipus(TipusAgent tipus) {
        this.tipus = tipus;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    // toString 
    @Override
    public String toString() {
        return "Agent{" + "idAgent=" + idAgent + ", nom=" + nom + ", cognoms=" + cognoms + ", email=" + email + ", tipus=" + tipus + '}';
    }
}
