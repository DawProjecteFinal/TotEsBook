/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


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

    public BibliotecaLlibre() {
    }

    public BibliotecaLlibre(Biblioteca biblioteca, Llibre llibre, int exemplars, int disponibles) {
        this.biblioteca = biblioteca;
        this.llibre = llibre;
        this.exemplars = exemplars;
        this.disponibles = disponibles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    // Getters i setters
    public int getId() {
        return id;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public int getExemplars() {
        return exemplars;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setExemplars(int exemplars) {
        this.exemplars = exemplars;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }
}
