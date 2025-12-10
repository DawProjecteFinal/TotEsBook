package cat.totesbook.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa una biblioteca.
 * 
 * @author Equip TotEsBook
 */
@Entity
@Table(name = "Biblioteques")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBiblioteca;

    @Column(nullable = false)
    private String nom;

    private String adreca;
    private String telefon;
    private String email;

    @Transient  // indica que no és una columna a la BD
    private int numLlibres;

    @Transient
    private int numPrestecs;

    // Relació amb el bibliotecari responsable
    @ManyToOne
    @JoinColumn(name = "idBibliotecari")
    private Agent bibliotecari;

    // Una biblioteca pot tenir MOLTS agents
    @OneToMany(mappedBy = "biblioteca", fetch = FetchType.LAZY)
    private List<Agent> agents = new ArrayList<>();

    /**
     * Constructor buit requerit per JPA
     */
    public Biblioteca() {
    }

    /**
     * Constructor de la classe Biblioteca.
     * 
     * @param nom Nom de la biblioteca.
     * @param adreca Adreça on es troba la biblioteca.
     * @param telefon Teléfon de la biblioteca.
     * @param email Correu electrònic de la biblioteca.
     * @param bibliotecari Bibliotecari assignat a la biblioteca.
     */
    public Biblioteca(String nom, String adreca, String telefon, String email, Agent bibliotecari) {
        this.nom = nom;
        this.adreca = adreca;
        this.telefon = telefon;
        this.email = email;
        this.bibliotecari = bibliotecari;
    }

    // Getters i Setters
    
    /**
     * Retorna el id de la biblioteca.
     * 
     * @return id de la biblioteca.
     */
    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    /**
     * Assigna un id a una biblioteca.
     * 
     * @param idBiblioteca id d'una biblioteca.
     */
    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    /**
     * Retorna el nom de la biblioteca.
     * 
     * @return nom de la biblioteca.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Assigna un nom a una biblioteca.
     * 
     * @param nom nom a una biblioteca.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna l'adreça de la biblioteca.
     * 
     * @return adreça de la biblioteca.
     */
    public String getAdreca() {
        return adreca;
    }

    /**
     * Assigna adreça de la biblioteca.
     * 
     * @param adreca adreça de la biblioteca.
     */
    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    /**
     * Retorna el telèfon de la biblioteca.
     * 
     * @return telèfon de la biblioteca.
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Assigna un telèfon de la biblioteca.
     * 
     * @param telefon telèfon de la biblioteca.
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Retorna el correu electrònic de la biblioteca.
     * 
     * @return correu electrònic de la biblioteca.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Assigna un correu electrònic de la biblioteca.
     * 
     * @param email correu electrònic de la biblioteca.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna el bibliotecari assignat a la biblioteca.
     * 
     * @return bibliotecari assignat a la biblioteca.
     */
    public Agent getBibliotecari() {
        return bibliotecari;
    }

    /**
     * Assigna un bibliotecari assignat a la biblioteca.
     * 
     * @param bibliotecari bibliotecari assignat a la biblioteca.
     */
    public void setBibliotecari(Agent bibliotecari) {
        this.bibliotecari = bibliotecari;
    }

    /**
     * Retorna el nombre de llibres que hi ha a la biblioteca.
     * 
     * @return nombre de llibres que hi ha a la biblioteca.
     */
    public int getNumLlibres() {
        return numLlibres;
    }

    /**
     * Assigna un nombre de llibres que hi ha a la biblioteca.
     * 
     * @param numLlibres nombre de llibres que hi ha a la biblioteca.
     */
    public void setNumLlibres(int numLlibres) {
        this.numLlibres = numLlibres;
    }

    /**
     * Retorna el nombre de prèstecs que hi ha a la biblioteca.
     * 
     * @return nombre de prèstecs que hi ha a la biblioteca.
     */
    public int getNumPrestecs() {
        return numPrestecs;
    }

    /**
     * Assigna un nombre de prèstecs que hi ha a la biblioteca.
     * 
     * @param numPrestecs nombre de prèstecs que hi ha a la biblioteca.
     */
    public void setNumPrestecs(int numPrestecs) {
        this.numPrestecs = numPrestecs;
    }

    /**
     * Retorna la llista de tots els agents.
     * 
     * @return llista de tots els agents.
     */
    public List<Agent> getAgents() {
        return agents;
    }

    /**
     * Assigna un llistat de tots els agents.
     * 
     * @param agents llista de tots els agents.
     */
    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    /**
     * Mètode que retorna un toString (opcional, útil per a logs).
     * 
     * @return cadena amb id, nom, bibliotecari assignat (en cas que hi hagi un d'assignat).
     */
    @Override
    public String toString() {
        return "Biblioteca{"
                + "idBiblioteca=" + idBiblioteca
                + ", nom='" + nom + '\''
                + ", bibliotecari=" + (bibliotecari != null ? bibliotecari.getNom() : "cap")
                + '}';
    }
}
