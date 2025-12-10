package cat.totesbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Classe que representa un llibre dintre d'una biblioteca.
 * 
 * @author Equip TotEsBook
 */
@Entity
@Table(name = "BibliotecaLlibres")
public class BibliotecaLlibre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Clau primària simple

    @ManyToOne
    @JoinColumn(name = "idBiblioteca", nullable = false)
    private Biblioteca biblioteca;

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName="isbn", nullable = false)
    private Llibre llibre;

    private int exemplars;
    private int disponibles;

    /**
     * Constructor buit requerit per JPA
     */
    public BibliotecaLlibre() {
    }

    /**
     * Constructor de la classe BibliotecaLlibre.
     * 
     * @param biblioteca Biblioteca.
     * @param llibre Llibre.
     * @param exemplars Nombre d'exemplars.
     * @param disponibles Nombre d'exemplars disponibles.
     */
    public BibliotecaLlibre(Biblioteca biblioteca, Llibre llibre, int exemplars, int disponibles) {
        this.biblioteca = biblioteca;
        this.llibre = llibre;
        this.exemplars = exemplars;
        this.disponibles = disponibles;
    }

    /**
     * Assigna un id de de la biblioteca
     * 
     * @param id id de de la biblioteca
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Assigna un llibre de la biblioteca. 
     * 
     * @param llibre llibre de la biblioteca.
     */
    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    /**
     * Assigna una biblioteca.
     * 
     * @param biblioteca biblioteca.
     */
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    // Getters i setters
    
    /**
     * Retorna el id de de la biblioteca.
     * 
     * @return id de de la biblioteca
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna la biblioteca.
     * 
     * @return biblioteca.
     */
    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    /**
     * Retorna el llibre de la biblioteca.
     * 
     * @return llibre de la biblioteca.
     */
    public Llibre getLlibre() {
        return llibre;
    }

    /**
     * Retorna els exemplars del llibre.
     * 
     * @return exemplars del llibre.
     */
    public int getExemplars() {
        return exemplars;
    }

    /**
     * Retorna el nombre de llibres disponibles a la biblioteca.
     * 
     * @return nombre de llibres disponibles a la biblioteca.
     */
    public int getDisponibles() {
        return disponibles;
    }

    /**
     * Assigna uns exemplars del llibre.
     * 
     * @param exemplars exemplars del llibre.
     */
    public void setExemplars(int exemplars) {
        this.exemplars = exemplars;
    }

    /**
     * Assigna un nombre de llibres disponibles a la biblioteca.
     * 
     * @param disponibles nombre de llibres disponibles a la biblioteca.
     */
    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }
}
