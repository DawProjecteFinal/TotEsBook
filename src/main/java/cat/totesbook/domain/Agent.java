package cat.totesbook.domain;

/**
 *  Classe que representa l'entitat Agent a la BD.
 * 
 * @author equip TotEsBook
 */

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

    // Constructor buit requerit per JPA
    public Agent() {
    }

    // --- Getters i Setters ---
    public int getIdAgent() { return idAgent; }
    public void setIdAgent(int idAgent) { this.idAgent = idAgent; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCognoms() { return cognoms; }
    public void setCognoms(String cognoms) { this.cognoms = cognoms; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public TipusAgent getTipus() { return tipus; }
    public void setTipus(TipusAgent tipus) { this.tipus = tipus; }
    public String getContrasenya() { return contrasenya; }
    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }

     // toString
    @Override
    public String toString() {
        return "Agent{" + "idAgent=" + idAgent + ", nom=" + nom + ", cognoms=" + cognoms + ", email=" + email + ", tipus=" + tipus + '}';
    }
}
