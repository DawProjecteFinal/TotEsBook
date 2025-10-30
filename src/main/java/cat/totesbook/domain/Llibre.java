package cat.totesbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
// import jakarta.persistence.EnumType; // No es fa servir aquí
// import jakarta.persistence.Enumerated; // No es fa servir aquí
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Lob; // Per a TEXT

/**
 * Classe que representa l'entitat Llibre a la BD.
 * 
 * @author equip TotEsBook
 */
@Entity
@Table(name = "Llibres")
public class Llibre {

    @Id // Clau primària (ISBN)
    @Column(length = 20) // Ajustem longitud
    private String isbn;

    @Column(nullable = false, length = 255)
    private String titol;

    @Column(nullable = false, length = 150)
    private String autor;
    
    @Column(length = 150)
    private String editorial;
    
    @Column(length = 100)
    private String categoria;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    @Column(length = 255) // URL pot ser llarga
    private String imatgeUrl;
    
    @Column(nullable = false) // Afegim nullable=false com a init.sql
    private int exemplars = 0; // Valor per defecte
    
    @Column(nullable = false) // Afegim nullable=false com a init.sql
    private int disponibles = 0; // Valor per defecte

    @Column(length = 10) // Eliminem nullable=false per permetre valors desconeguts
    private String idioma;

    /**
     * Constructor buit requerit per JPA.
     */
    public Llibre() {
    }
    public Llibre(String isbn, String titol, String autor, String editorial, String categoria, String sinopsis, String imatgeUrl, String idioma) {
        this.isbn = isbn;
        this.titol = titol;
        this.autor = autor;
        this.editorial = editorial;
        this.categoria = categoria;
        this.sinopsis = sinopsis;
        this.imatgeUrl = imatgeUrl;
        this.idioma = idioma;
        // Valors inicials per a exemplars/disponibles quan ve de l'API? Potser 0?
        this.exemplars = 0; 
        this.disponibles = 0;
    }

    // --- Getters i Setters ---
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitol() { return titol; }
    public void setTitol(String titol) { this.titol = titol; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }
    public String getImatgeUrl() { return imatgeUrl; }
    public void setImatgeUrl(String imatgeUrl) { this.imatgeUrl = imatgeUrl; }
    public int getExemplars() { return exemplars; }
    public void setExemplars(int exemplars) { this.exemplars = exemplars; }
    public int getDisponibles() { return disponibles; }
    public void setDisponibles(int disponibles) { this.disponibles = disponibles; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    @Override
    public String toString() {
        return "Llibre{" + "isbn='" + isbn + '\'' + ", titol='" + titol + '\'' + ", autor='" + autor + '\'' + '}';
    }
}
