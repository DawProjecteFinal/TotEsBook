package cat.totesbook.domain;

import jakarta.persistence.*;

/**
 * Classe que representa l'entitat Usuari a la BD.
 * 
 * @author equip TotEsBook
 */

@Entity
@Table(name = "Usuaris")
public class Usuari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String cognoms;

    private String telefon;
    
    @Column(unique = true)
    private String email;

    private String llibresFavorits;

    public Usuari() {
    }

    // Getters i Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLlibresFavorits() {
        return llibresFavorits;
    }

    public void setLlibresFavorits(String llibresFavorits) {
        this.llibresFavorits = llibresFavorits;
    }
}
