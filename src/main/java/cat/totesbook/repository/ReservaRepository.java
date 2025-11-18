package cat.totesbook.repository;

import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Usuari;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jmiro
 */
public interface ReservaRepository {   

    Optional<Reserva> findReservaPendent(Usuari usuari, Llibre llibre);

    void crearReserva(Reserva reserva);

    List<Reserva> findByUsuari(int idUsuari);
}
