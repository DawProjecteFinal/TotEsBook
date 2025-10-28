package cat.totesbook.service.impl;

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
 * @author equip TotEsBook
 */
@Service
public class LlibreServiceImpl implements LlibreService {

    /**
     * Repositori de llibres que serveix per accedir a les dades de la BD.
     */
    @Autowired
    private LlibreRepository llibreRepository;

    /**
     * Mètode qe retorna tots els llibres.
     * 
     * @return llista de llibres
     */
    @Override
    public List<Llibre> getAllLlibres() {
        return llibreRepository.getAllLlibres();
    }
<<<<<<< HEAD
    
    /**
     * Mètode que transaccional que guarda un llibre.
     * 
     * @param llibre 
     */
    @Override
=======

>>>>>>> feature-login
    @Transactional
    public void addLlibre(Llibre llibre) {
        llibreRepository.addLlibre(llibre);
    }
    
    /**
    * Mètode que obté un llibre a partir del seu ISBN.
    * 
    * @param isbn
    * @return el llibre amb l'ISBN demanat.
    */
    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn){
        return llibreRepository.getLlibreByIsbn(isbn);
    }

    @Override
    public void guardarLlibre(Llibre llibre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
