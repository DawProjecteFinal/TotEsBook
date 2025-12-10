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
 * Classe que representa un llibre.
 * 
 * @author Equip TotEsBook
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
     * @param isbn ISBN del llibre.
     * @param titol títol del llibre.
     * @param autor autor del llibre.
     * @param editorial editorial del llibre.
     * @param categoria categoria del llibre.
     * @param sinopsis sipnosis del llibre.
     * @param imatgeUrl imatgeUrl del llibre.
     * @param idioma idioma del llibre.
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
    /**
     * Retorna el isbn del llibre.
     * 
     * @return isbn del llibre.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Assigna un isbn d'un llibre.
     * 
     * @param isbn isbn del llibre.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Retorna el títol del llibre.
     * 
     * @return títol del llibre.
     */
    public String getTitol() {
        return titol;
    }

    /**
     * Assigna un títol del llibre.
     * 
     * @param titol títol del llibre.
     */
    public void setTitol(String titol) {
        this.titol = titol;
    }

    /**
     * Retorna el autor del llibre.
     * 
     * @return autor del llibre.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Assigna un autor del llibre.
     * 
     * @param autor autor del llibre.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Retorna la editorial del llibre.
     * 
     * @return editorial del llibre.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Assigna una editorial del llibre. 
     * 
     * @param editorial editorial del llibre.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Retorna la categoria del llibre.
     * 
     * @return categoria del llibre.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Assigna una categoria del llibre.
     * 
     * @param categoria categoria del llibre.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna la sinopsis del llibre.
     * 
     * @return sinopsis del llibre.
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Assigna una sinopsis del llibre.
     * 
     * @param sinopsis La sinopsis del llibre.
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Retorna la imatge de la portada del llibre.
     * 
     * @return imatge de la portada del llibre.
     */
    public String getImatgeUrl() {
        return imatgeUrl;
    }

    /**
     * Assigna una imatge de la portada del llibre.
     * 
     * @param imatgeUrl imatge de la portada del llibre.
     */
    public void setImatgeUrl(String imatgeUrl) {
        this.imatgeUrl = imatgeUrl;
    }

    /**
     * Retorna el nombre d'exemplars del libre.
     * 
     * @return nombre d'exemplars del libre.
     */
    public int getExemplars() {
        return exemplars;
    }

    /**
     * Assigna un nombre d'exemplars del libre.
     * 
     * @param exemplars nombre d'exemplars del libre.
     */
    public void setExemplars(int exemplars) {
        this.exemplars = exemplars;
    }

    /**
     * Retorna el nombre de llibres disponibles.
     * 
     * @return nombre de llibres disponibles.
     */
    public int getDisponibles() {
        return disponibles;
    }

    /**
     * Assigna un nombre de llibres disponibles.
     * 
     * @param disponibles nombre de llibres disponibles.
     */
    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    /**
     * Retorna el idioma del llibre.
     * 
     * @return idioma del llibre.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Assigna un idioma del llibre.
     * 
     * @param idioma idioma del llibre.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Mètode que retorna un toString (opcional, útil per a logs).
     * 
     * @return cadena amb isbn, titol, autor, sinopsis i imatge.
     */
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
