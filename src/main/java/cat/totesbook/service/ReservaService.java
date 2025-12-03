/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Reserva;

import java.util.List;

public interface ReservaService {

    /**
     * Crea una nova reserva per a un usuari i un llibre
     *
     * @param idUsuari L'ID de l'usuari que reserva.
     * @param isbn L'ISBN del llibre a reservar.
     */
    void crearReserva(int idUsuari, String isbn) throws Exception;

    List<Reserva> findReservaByUsuari(int idUsuari);

    List<Reserva> findReservesPendentsByBiblioteca(Biblioteca biblioteca);

    void cancelReserva(Reserva reserva);

    void eliminarReserva(int idReserva);

    Reserva findByIdReserva(int idReserva);
}
