package cat.totesbook.domain;

/**
 *
 * @author jmiro
 */
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Biblioteca() {
    }

    // Constructor
    public Biblioteca(String nom, String adreca, String telefon, String email, Agent bibliotecari) {
        this.nom = nom;
        this.adreca = adreca;
        this.telefon = telefon;
        this.email = email;
        this.bibliotecari = bibliotecari;
    }

    // Getters i Setters
    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
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

    public Agent getBibliotecari() {
        return bibliotecari;
    }

    public void setBibliotecari(Agent bibliotecari) {
        this.bibliotecari = bibliotecari;
    }

    public int getNumLlibres() {
        return numLlibres;
    }

    public void setNumLlibres(int numLlibres) {
        this.numLlibres = numLlibres;
    }

    public int getNumPrestecs() {
        return numPrestecs;
    }

    public void setNumPrestecs(int numPrestecs) {
        this.numPrestecs = numPrestecs;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    @Override
    public String toString() {
        return "Biblioteca{"
                + "idBiblioteca=" + idBiblioteca
                + ", nom='" + nom + '\''
                + ", bibliotecari=" + (bibliotecari != null ? bibliotecari.getNom() : "cap")
                + '}';
    }
}
