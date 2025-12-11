/**
 *
 * @author Equip TotEsBook
 */
package cat.totesbook.service.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Reserva.EstatReserva;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.BibliotecaLlibreRepository;
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.repository.ReservaRepository;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.ReservaService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Classe que implementa el servei de les reserves.
 * 
 * @author Equip TotEsBook
 */
@Service
@Transactional // Transaccio entre totes les operacions (crear reserva, actualitzar llibre) es fan juntes o cap.

public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private LlibreRepository llibreRepository;

    @Autowired
    private BibliotecaLlibreRepository bibliotecaLlibreRepository;

    /**
     * Crea una nova reserva per a un usuari i un llibre
     *
     * @param idUsuari L'ID de l'usuari que reserva.
     * @param isbn L'ISBN del llibre a reservar.
     */
    @Override
    public void crearReserva(int idUsuari, String isbn) throws Exception {

        // Usuari
        Usuari usuari = usuariRepository.findUsuariById(idUsuari);
        if (usuari == null) {
            throw new Exception("L'usuari amb ID " + idUsuari + " no existeix.");
        }
/*
        if (usuari.teSancioActiva()) {
            throw new Exception(
                    "No pots fer la reserva. Tens una sanció activa fins al "
                    + usuari.getDataFiSancioFormatted()
                    + " per " + usuari.getMotiuSancio()
                    + ". No podràs fer cap reserva fins aquesta data."
            );
        }
*/
        // Llibre
        Llibre llibre = llibreRepository.getLlibreByIsbn(isbn)
                .orElseThrow(() -> new Exception("El llibre amb ISBN " + isbn + " no existeix."));

        // Validació: reserva pendent existent
        reservaRepository.findReservaPendent(usuari, llibre)
                .ifPresent(r -> {
                    throw new RuntimeException("Ja tens una reserva pendent per a aquest llibre.");
                });

        // Buscar totes les biblioteques on existeix el llibre
        List<BibliotecaLlibre> biblios = bibliotecaLlibreRepository.findByLlibreIsbn(isbn);
        if (biblios.isEmpty()) {
            throw new Exception("Aquest llibre no existeix a cap biblioteca.");
        }

        // Triem la primera biblioteca que té un exemplar del llibre a reservar
        BibliotecaLlibre blDisponible = null;
        for (BibliotecaLlibre bl : biblios) {
            if (bl.getDisponibles() > 0) {
                blDisponible = bl;
                break;
            }
        }

        if (blDisponible == null) {
            throw new Exception("No queden exemplars disponibles en cap biblioteca.");
        }

        // Crear reserva
        Reserva nova = new Reserva();
        nova.setUsuari(usuari);
        nova.setLlibre(llibre);
        nova.setDataReserva(LocalDateTime.now());
        nova.setEstat(Reserva.EstatReserva.pendent);

        reservaRepository.crearReserva(nova);

        // Restar 1 exemplar
        blDisponible.setDisponibles(blDisponible.getDisponibles() - 1);
    }

    /**
     * Cerca una llista de reserves segons l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les reserves segons l'usuari.
     */
    @Override
    public List<Reserva> findReservaByUsuari(int idUsuari) {
        return reservaRepository.findByUsuari(idUsuari);
    }

    /**
     * Cerca les reserves pendents segons la biblioteca indicada.
     * 
     * @param biblioteca La biblioteca.
     * @return Llista amb les rserves pendents segons la biblioteca indicada.
     */
    @Override
    public List<Reserva> findReservesPendentsByBiblioteca(Biblioteca biblioteca) {
        return reservaRepository.findReservesPendentsByBiblioteca(biblioteca);
    }

    /**
     * El biblitecari pot cancel·lar la reserva de l'usuari.
     * 
     * @param reserva Reserva.
     */
    @Override
    public void cancelReserva(Reserva reserva) {
        reserva.setEstat(EstatReserva.cancelada);
        // Si la reserva estava "disponible", alliberem l'exemplar

        reservaRepository.crearReserva(reserva);
    }

    /**
     * Eliminar la reserva segons el seu ID.
     * 
     * @param idReserva ID de la reserva.
     */
    @Override
    public void eliminarReserva(int idReserva) {
        reservaRepository.deleteById(idReserva);
    }

    /**
     * Cerca la reserva segons el seu ID.
     * 
     * @param idReserva L'ID de al reserva.
     * @return Objecte Reserva amb el seu ID.
     */
    @Override
    public Reserva findByIdReserva(int idReserva) {
        return reservaRepository.findByIdReserva(idReserva);
    }

}
