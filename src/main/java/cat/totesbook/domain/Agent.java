/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.domain;

/**
 *
 * @author jmiro
 */
import jakarta.persistence.*;

@Entity
@Table(name = "Agents")
public class Agent {

    public enum TipusAgent {
        bibliotecari,
        administrador
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAgent;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String cognoms;

    private String telefon;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipusAgent tipus;

    public Agent() {
    }

    // Getters i Setters
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
}
