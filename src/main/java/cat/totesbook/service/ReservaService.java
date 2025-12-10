package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Reserva;

import java.util.List;

/**
 * Interfície del servei de reserves.
 * 
 * @author equip TotEsBook
 */
public interface ReservaService {

    /**
     * Crea una nova reserva per a un usuari i un llibre
     * 
     * @param idUsuari L'ID de l'usuari que reserva.
     * @param isbn L'ISBN del llibre a reservar.
     * @throws Exception Si hi ha un error.
     */
    void crearReserva(int idUsuari, String isbn) throws Exception;

    /**
     * Cerca una llista de reserves segons l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les reserves segons l'usuari.
     */
    List<Reserva> findReservaByUsuari(int idUsuari);

    /**
     * Cerca les reserves pendents segons la biblioteca indicada.
     * 
     * @param biblioteca La biblioteca.
     * @return Llista amb les rserves pendents segons la biblioteca indicada.
     */
    List<Reserva> findReservesPendentsByBiblioteca(Biblioteca biblioteca);

    /**
     * El biblitecari pot cancel·lar la reserva de l'usuari.
     * 
     * @param reserva Reserva.
     */
    void cancelReserva(Reserva reserva);

    /**
     * Eliminar la reserva segons el seu ID.
     * 
     * @param idReserva ID de la reserva.
     */
    void eliminarReserva(int idReserva);

    /**
     * Cerca la reserva segons el seu ID.
     * 
     * @param idReserva L'ID de al reserva.
     * @return Objecte Reserva amb el seu ID.
     */
    Reserva findByIdReserva(int idReserva);
}
