package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.dto.AutorEstadisticaDTO;
import cat.totesbook.dto.LlibreEstadisticaDTO;
import cat.totesbook.dto.UsuariEstadisticaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interfície per a les operacions de dades de la taula Prestec.
 * 
 * @author Equip TotEsBook
 */
public interface PrestecRepository {
    
    /**
     * Registrar un préstec.
     * 
     * @param prestec Un préstec.
     */
    void registrarPrestec(Prestec prestec);
    
    /**
     * Cerca els préstecs actius segons la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llistat amb els préstecs actius segons la biblioteca.
     */
    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);

    /**
     * Cerca un préstec actiu segons l'usuari i el llibre.
     * 
     * @param isbn L'ISBN.
     * @param idUsuari L'ID de l'usuari.
     * @return Un optional Prestec on hi ha el préstec actiu amb l'usuari
     *         i el llibre.
     */
    Optional<Prestec> findPrestecActiu(String isbn, Integer idUsuari);

    /**
     * Retorna les devolucions segons a les biblioteques on s'han fet.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llista amb les devolucions segons a les biblioteques on s'han fet.
     */
    List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca);
    
    /**
     * Actualitzar la informació dels préstecs.
     * 
     * @param prestec El préstec.
     */
    void updatePrestec(Prestec prestec);
    
    /**
     * Retorna els préstecs actius segons l'usuari indicat.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb els préstecs actius segons l'usuari indicat.
     */
    List<Prestec> findPrestecsActiusByUsuari(int idUsuari);

    /**
     * Retorna els préstecs retornats segons l'usuari indicat.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb els préstecs retornts segons l'usuari indicat.
     */
    List<Prestec> findPrestecsRetornatsByUsuari(Integer idUsuari);
    
    // Mètodes per la lògica per renovar préstec
    /**
     * Retorna un préstec amb l'ID indicat.
     * 
     * @param idPrestec L'ID del préstec.
     * @return Un opcional de Prestec amb el préstec de l'ID indicat.
     */
    Optional<Prestec> findById(Integer idPrestec);
    
    /**
     * Desar la informació del préstec.
     * 
     * @param prestec El préstec.
     */
    void save(Prestec prestec);
    
    // ----
    
    // SPRINT 3 (TEA 5): Mètodes per estadístiques
    
    /**
     * Retorna les estadístiques dels llibres segons la categoria.
     * 
     * @param autor L'autor.
     * @param categoria La categoria.
     * @return Llista de les estadístiques dels llibres segons la categoria..
     */
    List<LlibreEstadisticaDTO> findEstadistiquesLlibres(String autor, String categoria);
    
    /**
     * Retorna les estadístiques dels autors.
     * 
     * @return Llista de les estadístiques dels autors.
     */
    List<AutorEstadisticaDTO> findEstadistiquesAutors();
    
    /**
     * Retorna les estadístiques dels usuaris.
     * 
     * @return Llista de les estadístiques dels usuaris.
     */
    List<UsuariEstadisticaDTO> findEstadistiquesUsuaris();
    // ----
}
