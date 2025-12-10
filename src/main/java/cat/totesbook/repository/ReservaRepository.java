package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Usuari;
import java.util.List;
import java.util.Optional;

/**
 * Interfície per a les operacions de dades de la taula Reserva.
 * 
 * @author Equip TotEsBook
 */
public interface ReservaRepository {   
    
    /**
     * Cerca les reserves pendents segons l'usuari i el llibre.
     * 
     * @param usuari L'usuari.
     * @param llibre El llibre.
     * @return Un optional amb la reserva pendent.
     */
    Optional<Reserva> findReservaPendent(Usuari usuari, Llibre llibre);

    /**
     * Crear una reserva.
     * @param reserva Una reserva.
     */
    void crearReserva(Reserva reserva);

    /**
     * Cerca una llista de reserves segons l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les reserves segons l'usuari.
     */
    List<Reserva> findByUsuari(int idUsuari);
    
    /**
     * Cerca les reserves pendents segons la biblioteca indicada.
     * 
     * @param biblioteca La biblioteca.
     * @return Llista amb les rserves pendents segons la biblioteca indicada.
     */
    List<Reserva> findReservesPendentsByBiblioteca(Biblioteca biblioteca);
    
    /**
     * Eliminar la reserva segons el seu ID.
     * 
     * @param idReserva ID de la reserva.
     */
    void deleteById(Integer idReserva);
    
    /**
     * Cerca la reserva segons el seu ID.
     * 
     * @param idReserva L'ID de al reserva.
     * @return Objecte Reserva amb el seu ID.
     */
    Reserva findByIdReserva(int idReserva);
}
