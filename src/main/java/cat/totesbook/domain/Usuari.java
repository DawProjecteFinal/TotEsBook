/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.domain;

import jakarta.persistence.*; // Imports JPA necessaris
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    // Constructor buit requerit per JPA
    public Usuari() {
    }

    // --- Getters i Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCognoms() { return cognoms; }
    public void setCognoms(String cognoms) { this.cognoms = cognoms; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLlibresFavorits() { return llibresFavorits; }
    public void setLlibresFavorits(String llibresFavorits) { this.llibresFavorits = llibresFavorits; }
    public String getContrasenya() { return contrasenya; }
    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }
    public LocalDateTime getDataFiSancio() { return dataFiSancio; }
    public void setDataFiSancio(LocalDateTime dataFiSancio) { this.dataFiSancio = dataFiSancio; }
    public String getMotiuSancio() { return motiuSancio; }
    public void setMotiuSancio(String motiuSancio) { this.motiuSancio = motiuSancio; }

    // toString (opcional, útil per a logs)
    @Override
    public String toString() {
        return "Usuari{" + "id=" + id + ", nom=" + nom + ", cognoms=" + cognoms + ", email=" + email + '}';
    }
    
    public boolean teSancioActiva() {
        return dataFiSancio != null && dataFiSancio.isAfter(LocalDateTime.now());
    }
    
    public String getDataFiSancioFormatted() {
        if (dataFiSancio == null) return "";
        return dataFiSancio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}