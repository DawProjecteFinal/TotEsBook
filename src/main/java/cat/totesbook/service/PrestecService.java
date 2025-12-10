package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Reserva;
import cat.totesbook.dto.AutorEstadisticaDTO;
import cat.totesbook.dto.LlibreEstadisticaDTO;
import cat.totesbook.dto.UsuariEstadisticaDTO;
import java.util.List;

/**
 * Interfície del servei de prèstec.
 * 
 * @author equip TotEsBook
 */
public interface PrestecService {

    /**
     * Registrar un préstec.
     * 
     * @param isbn L'ISBN de l'usari.
     * @param emailUsuari El correu electrònic de l'usuari.
     * @param idAgentBibliotecari L'ID del bibliotecari.
     */
    void registrarPrestec(String isbn, String emailUsuari, int idAgentBibliotecari);

    /**
     * Cerca els préstecs actius segons la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llistat amb els préstecs actius segons la biblioteca.
     */
    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);

    /**
     * Registrar una devolució de llibre.
     * 
     * @param isbn L'ISBN.
     * @param emailUsuari El correu de l'usuari.
     * @param idAgentBibliotecari L'ID del bibliotecari.
     */
    void registrarDevolucio(String isbn, String emailUsuari, Integer idAgentBibliotecari);

    /**
     * Retorna els préstecs actius segons l'usuari indicat.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb els préstecs actius segons l'usuari indicat.
     */
    List<Prestec> findPrestecsActiusByUsuari(int idUsuari);
    
    /**
     * Retorna les devolucions segons a les biblioteques on s'han fet.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llista amb les devolucions segons a les biblioteques on s'han fet.
     */
    List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca);
    
    // Mètodes per renovar préstec
    /**
     * Renovar el préstec.
     * 
     * @param idPrestec L'ID del préstec.
     */
    void renovarPrestec(Integer idPrestec);
    
    /**
     * Retorna els préstecs retornats segons l'usuari indicat.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb els préstecs retornts segons l'usuari indicat.
     */
    List<Prestec> findPrestecsRetornatsByUsuari(Integer idUsuari);
    
    
    // Mètode per registar la devolució d'un prèstec amb el botó
    
    /**
     * Retornar el préstec.
     * 
     * @param idPrestec L'ID del préstec.
     * @param idAgentBibliotecari L'ID del bibliotecari.
     */
    void retornarPrestec(Integer idPrestec, Integer idAgentBibliotecari);
    
    /**
     * Retornar el préstec amb l'ID indicat.
     * 
     * @param idPrestec L'ID del préstec.
     * @return Objecte préstec segons l'ID passat.
     */
    Prestec getPrestecPerId(Integer idPrestec);
    
    // --- ==== SPRINT 3 (TEA 5) =====
    
    // Mètodes per treure estadístiques
    
    /**
     * Retorna les estadístiques dels llibres segons la categoria.
     * 
     * @param autor L'autor.
     * @param categoria La categoria.
     * @return Llista de les estadístiques dels llibres segons la categoria..
     */
    List<LlibreEstadisticaDTO> getEstadistiquesLlibres(String autor, String categoria);
    
    /**
     * Retorna les estadístiques dels autors.
     * 
     * @return Llista de les estadístiques dels autors.
     */
    List<AutorEstadisticaDTO> getEstadistiquesAutors();
    
    /**
     * Retorna les estadístiques dels usuaris.
     * 
     * @return Llista de les estadístiques dels usuaris.
     */
    List<UsuariEstadisticaDTO> getEstadistiquesUsuaris();
    
    /**
     * Crear un préstec des de la reserva.
     * 
     * @param reserva La reseva.
     * @param agentBibliotecari El bibliotecari.
     * @return Objecte Préstec.
     */
    Prestec crearPrestecDesDeReserva(Reserva reserva, Agent agentBibliotecari);

}
