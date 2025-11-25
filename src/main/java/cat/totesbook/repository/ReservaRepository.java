/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Usuari;
import java.util.List;
import java.util.Optional;


public interface ReservaRepository {   

    Optional<Reserva> findReservaPendent(Usuari usuari, Llibre llibre);

    void crearReserva(Reserva reserva);

    List<Reserva> findByUsuari(int idUsuari);
    
    List<Reserva> findReservesPendentsByBiblioteca(Biblioteca biblioteca);
}
