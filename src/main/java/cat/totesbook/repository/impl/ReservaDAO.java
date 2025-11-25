/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.ReservaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservaDAO implements ReservaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Troba només una rserva pendent
     *
     * @param usuari
     * @param llibre
     * @return
     */
    @Override
    public Optional<Reserva> findReservaPendent(Usuari usuari, Llibre llibre) {

        List<Reserva> resultats = entityManager.createQuery(
                "SELECT r FROM Reserva r "
                + "WHERE r.usuari = :usuari "
                + "AND r.llibre = :llibre "
                + "AND r.estat = :estat",
                Reserva.class)
                .setParameter("usuari", usuari)
                .setParameter("llibre", llibre)
                .setParameter("estat", Reserva.EstatReserva.pendent)
                .getResultList();

        return resultats.isEmpty()
                ? Optional.empty()
                : Optional.of(resultats.get(0));
    }

    @Override
    public void crearReserva(Reserva reserva) {
        if (reserva.getIdReserva() == 0) {
            entityManager.persist(reserva);
        } else {
            entityManager.merge(reserva);
        }
    }

    @Override
    public List<Reserva> findByUsuari(int idUsuari) {

        return entityManager.createQuery(
                "SELECT r FROM Reserva r "
                + "WHERE r.usuari.id = :idUsuari "
                + "ORDER BY r.dataReserva DESC",
                Reserva.class
        )
                .setParameter("idUsuari", idUsuari)
                .getResultList();
    }

    @Override
    public List<Reserva> findReservesPendentsByBiblioteca(Biblioteca biblioteca) {
        return entityManager.createQuery(
                "SELECT r FROM Reserva r "
                + "JOIN BibliotecaLlibre bl ON bl.llibre = r.llibre "
                + "WHERE bl.biblioteca = :biblio "
                + "AND r.estat = :estat",
                Reserva.class)
                .setParameter("biblio", biblioteca)
                .setParameter("estat", Reserva.EstatReserva.pendent)
                .getResultList();
    }

}
