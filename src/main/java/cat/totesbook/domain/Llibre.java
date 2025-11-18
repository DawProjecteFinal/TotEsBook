package cat.totesbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

/**
 * Classe que representa l'entitat Llibre a la BD.
 *
 * @author equip TotEsBook
 */
@Entity
@Table(name = "Llibres")
public class Llibre {

    @Id
    private String isbn;    //Codi dels llibres

    @Column(nullable = false)
    private String titol;

    @Column(nullable = false)
    private String autor;
    private String editorial;
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    private String imatgeUrl;
    private int exemplars;
    private int disponibles;

    @Column(nullable = false)
    private String idioma;   // La Api respòn:(es, ca, en, fr, …)


    /**
     * Constructor buit requerit per JPA.
     */
    public Llibre() {

    }

    /**
     * Constructor que crea llibre venint de la API
     *
     * @param isbn
     * @param titol
     * @param autor
     * @param editorial
     * @param categoria
     * @param sinopsis
     * @param imatgeUrl
     * @param idioma
     */
    public Llibre(String isbn, String titol, String autor, String editorial, String categoria, String sinopsis, String imatgeUrl, String idioma) {
        this.isbn = isbn;
        this.titol = titol;
        this.autor = autor;
        this.editorial = editorial;
        this.categoria = categoria;
        this.sinopsis = sinopsis;
        this.imatgeUrl = imatgeUrl;
        this.idioma = idioma;
    }

    // Getters i Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImatgeUrl() {
        return imatgeUrl;
    }

    public void setImatgeUrl(String imatgeUrl) {
        this.imatgeUrl = imatgeUrl;
    }

    public int getExemplars() {
        return exemplars;
    }

    public void setExemplars(int exemplars) {
        this.exemplars = exemplars;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "Llibre{"
                + "isbn='" + isbn + '\''
                + ", titol='" + titol + '\''
                + ", autor='" + autor + '\''
                + ", sinopsis='" + sinopsis + '\''
                + ", imatgeUrl='" + imatgeUrl + '\''
                + '}';
    }
}
