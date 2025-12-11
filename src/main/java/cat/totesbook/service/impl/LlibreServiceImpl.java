package cat.totesbook.service.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.service.LlibreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import cat.totesbook.domain.Llibre;

/**
 * Classe que implementa el servei dels llibres.
 * 
 * @author Equip TotEsBook
 */
@Service
@Transactional
public class LlibreServiceImpl implements LlibreService {

    /**
     * Repositori de llibres que serveix per accedir a les dades de la BD.
     */
    @Autowired
    private LlibreRepository llibreRepository;

    /**
     * Mètode que retorna tots els llibres.
     *
     * @return llista de llibres
     */
    @Override
    public List<Llibre> getAllLlibres() {
        return llibreRepository.getAllLlibres();
    }

    /**
     * Mètode transaccional que guarda un llibre.
     *
     * @param llibre llibre El llibre.
     */
    @Override
    public void guardarLlibre(Llibre llibre) {
        llibreRepository.addLlibre(llibre);
    }

    /**
     * Mètode que obté un llibre a partir del seu ISBN.
     *
     * @param isbn L'ISBN.
     * @return llibre amb l'ISBN passat per paràmetre.
     */
    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        return llibreRepository.getLlibreByIsbn(isbn);
    }

     /**
     * Retorna els llibres de una biblioteca en concret
     * 
     * @param biblioteca La biblioteca.
     * @return Els llibes d'una biblioteca en concret.
     */
    @Override
    public List<Llibre> findByBiblioteca(Biblioteca biblioteca) {
        return llibreRepository.findByBiblioteca(biblioteca);
    }

    /**
     * Retorna els llibres per categoria
     * 
     * @param categoria La categoria.
     * @return Llibres d'una categoria en concret.
     */
    @Override
    public List<Llibre> getLlibresByCategoria(String categoria) {
        return llibreRepository.findByCategoria(categoria);
    }

    /**
     * Retorna els llibres per un títol determinat
     * 
     * @param titol El títol del libre.
     * @return Una llista amb llibres que continguin aquell títol.
     */
    @Override
    public List<Llibre> getLlibreByTitol(String titol) {
       return llibreRepository.findByTitolContainingIgnoreCase(titol);
    }

    /**
     * Retorna els llisbres per  un autor determinat
     * 
     * @param autor L'autor d'un llibre.
     * @return Una llista amb llibres escrits per l'autor indicat.
     */
    @Override
    public List<Llibre> getLlibresByAutor(String autor) {
       return llibreRepository.findByAutorContainingIgnoreCase(autor);
    }

    /**
     * Retorna els llibres per un idioma determinat
     * 
     * @param idioma L'idioma.
     * @return Una llista amb llibres escrits en aquell idioma.
     */
    @Override
    public List<Llibre> getLlibresByIdioma(String idioma) {
        return llibreRepository.findByIdioma(idioma);
    }

    /**
     * Retorna una llista de llibres aleatoris.
     * 
     * @param limit El límit.
     * @return Una llista de llibres aleatoris.
     */
    @Override
    public List<Llibre> findRandom(int limit) {
        return llibreRepository.findRandom(limit);
    }

    /**
     * Mètode que elimina un llibre segons el seu ISBN..
     * 
     * @param isbn ISBN del llibre.
     */
    @Override
    @Transactional
    public void deleteLlibre(String isbn) {
        llibreRepository.deleteLlibreByIsbn(isbn);
    }
    


}
