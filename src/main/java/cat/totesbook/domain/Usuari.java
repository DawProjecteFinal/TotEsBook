package cat.totesbook.domain;

import jakarta.persistence.*; // Imports JPA necessaris
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa un usuari.
 * 
 * @author Equip TotEsBook
 */
@Entity // Indiquem que és una entitat JPA
@Table(name = "Usuaris") // Nom de la taula a la BBDD
public class Usuari {

    @Id // Clau primària
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;
    
    @Column(nullable = false, length = 100) // Afegim longitud per consistència amb SQL
    private String nom;
    
    @Column(nullable = false, length = 150)
    private String cognoms;

    @Column(length = 20)
    private String telefon;
    
    @Column(unique = true, length = 150)
    private String email;

    @Lob // Indiquem que pot ser un text llarg
    @Column(columnDefinition = "TEXT")
    private String llibresFavorits;
    
    @Column(nullable = false, length = 255) // Important que sigui nullable = false
    private String contrasenya;
    
    @Column(name = "dataFiSancio")
    private LocalDateTime dataFiSancio;
    
    @Column(name = "motiuSancio", length = 150)
    private String motiuSancio;

    /**
     * Constructor buit requerit per JPA
     */
    public Usuari() {
    }

    // --- Getters i Setters ---
    /**
     * Retorna el id de l'usuari.
     * 
     * @return id de l'usuari.
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Assigna un id a l'usuari.
     * 
     * @param id id de l'usuari.
     */
    public void setId(int id) { 
        this.id = id; 
    }
    
    /**
     * Retorna el nom de l'usuari.
     * 
     * @return nom de l'usuari.
     */
    public String getNom() { 
        return nom; 
    }
    
    /**
     * Assigna un nom a l'usuari.
     * 
     * @param nom nom de l'usuari.
     */
    public void setNom(String nom) {
        this.nom = nom; 
    }
    
    /**
     * Retorna els cognmos de l'usuari.
     * 
     * @return cognoms de l'usuari.
     */
    public String getCognoms() { 
        return cognoms; 
    }
    
    /**
     * Assigna cognoms a l'usuari.
     * 
     * @param cognoms cognoms de l'usuari.
     */
    public void setCognoms(String cognoms) { 
        this.cognoms = cognoms; 
    }
    
    /**
     * Retorna el telèfon de l'usuari.
     * 
     * @return telèfon de l'usuari.
     */
    public String getTelefon() { 
        return telefon; 
    }
    
    /**
     * Assigna un telèfon a l'usuari.
     * 
     * @param telefon telèfon de l'usuari.
     */
    public void setTelefon(String telefon) { 
        this.telefon = telefon; 
    }
    
    /**
     * Retorna el correu electrònic de l'usuari.
     * 
     * @return correu electrònic de l'usuari.
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * Assigna un correu electrònic a l'usuari.
     * 
     * @param email correu electrònic de l'usuari.
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    /**
     * Retorna el llibre preferit de l'usuari.
     * 
     * @return llibre preferit de l'usuari.
     */
    public String getLlibresFavorits() { 
        return llibresFavorits; 
    }
    
    /**
     * Assigna llibres preferits a l'usuari.
     * 
     * @param llibresFavorits llibres preferits de l'usuari.
     */
    public void setLlibresFavorits(String llibresFavorits) { 
        this.llibresFavorits = llibresFavorits; 
    }
    
    /**
     * Retorna la contrasenya de l'usuari.
     * 
     * @return contrasenya de l'usuari.
     */
    public String getContrasenya() { 
        return contrasenya; 
    }
    
    /**
     * Assigna una contrasenya a l'usuari.
     * 
     * @param contrasenya contrasenya de l'usuari.
     */
    public void setContrasenya(String contrasenya) { 
        this.contrasenya = contrasenya; 
    }
    
    /**
     * Retorna la data de fi de sanció de l'usuari.
     * 
     * @return data de fin de sanció de l'usuari.
     */
    public LocalDateTime getDataFiSancio() { 
        return dataFiSancio; 
    }
    
    /**
     * Assigna una data de fi de sanció a l'usuari.
     * 
     * @param dataFiSancio data de fi de sanció.
     */
    public void setDataFiSancio(LocalDateTime dataFiSancio) { 
        this.dataFiSancio = dataFiSancio; 
    }
    /**
     * Retorna el motiu de la sanció de l'usuari.
     * 
     * @return motiu de la sanció de l'usuari.
     */
    public String getMotiuSancio() { 
        return motiuSancio; 
    }
    /**
     * Assigna un motiu de sanció a l'usuari.
     * 
     * @param motiuSancio motiu de sanció de l'usuari.
     */
    public void setMotiuSancio(String motiuSancio) { 
        this.motiuSancio = motiuSancio; 
    }

    /**
     * Mètode que retorna un toString (opcional, útil per a logs).
     * 
     * @return cadena amb id, nom, cognoms i correu electrònic.
     */
    @Override
    public String toString() {
        return "Usuari{" + "id=" + id + ", nom=" + nom + ", cognoms=" + cognoms + ", email=" + email + '}';
    }
    
    /**
     * Mètode que comprova si l'usuari té una sanció.
     * 
     * @return true si l'usuari té sanció, false si no té sanció.
     */
    public boolean teSancioActiva() {
        return dataFiSancio != null && dataFiSancio.isAfter(LocalDateTime.now());
    }
    
    /**
     * Mètode que retorna la data de finalització de la sanció en format
     * "dd/MM/yyyy".
     * 
     * @return data de finalització o una cadena buida.
     */
    public String getDataFiSancioFormatted() {
        if (dataFiSancio == null) return "";
        return dataFiSancio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}