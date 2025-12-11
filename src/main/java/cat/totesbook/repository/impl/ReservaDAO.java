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

/**
 * Classe DAO que gestiona l'accés a la base de dades per a les entitats Reserva.
 * 
 * @author Equip TotEsBook
 */

@Repository

/**
 * Implementació JPA del repositori de Reserva.
 */
public class ReservaDAO implements ReservaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Troba només una rserva pendent
     *
     * @param usuari L'usuari.
     * @param llibre El llibre.
     * @return Un optional amb la reserva pendent.
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

    /**
     * Crear una reserva.
     * @param reserva Una reserva.
     */
    @Override
    public void crearReserva(Reserva reserva) {
        if (reserva.getIdReserva() == 0) {
            entityManager.persist(reserva);
        } else {
            entityManager.merge(reserva);
        }
    }

    /**
     * Cerca una llista de reserves segons l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les reserves segons l'usuari.
     */
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

    /**
     * Cerca les reserves pendents segons la biblioteca indicada.
     * 
     * @param biblioteca La biblioteca.
     * @return Llista amb les rserves pendents segons la biblioteca indicada.
     */
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

    /**
     * Eliminar la reserva segons el seu ID.
     * 
     * @param idReserva ID de la reserva.
     */
    @Override
    public void deleteById(Integer idReserva) {
        Reserva r = entityManager.find(Reserva.class, idReserva);
        if (r != null) {
            entityManager.remove(r);
        }
    }

    /**
     * Cerca la reserva segons el seu ID.
     * 
     * @param idReserva L'ID de al reserva.
     * @return Objecte Reserva amb el seu ID.
     */
    @Override
    public Reserva findByIdReserva(int idReserva) {
        return entityManager.find(Reserva.class, idReserva);
    }

}
