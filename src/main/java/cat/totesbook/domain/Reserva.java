package cat.totesbook.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Classe que representa una reserva d'un llibre.
 * 
 * @author Equip TotEsBook
 */
@Entity
@Table(name = "Reserves")
public class Reserva {

    /**
     * Enum per al camp 'estat'
     */
    public enum EstatReserva {
        /** Estat reserva pendent.*/
        pendent, 
        
        /** Estat reserva cancel·lada.*/
        cancelada, 
        
        /** Estat reserva caducada.*/
        caducada
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReserva;

    @ManyToOne
    @JoinColumn(name = "idUsuari", nullable = false)
    private Usuari usuari;

    @ManyToOne
    @JoinColumn(name = "isbn", nullable = false)
    private Llibre llibre;

    @Column(nullable = false)
    private LocalDateTime dataReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstatReserva estat = EstatReserva.pendent;

    /**
     * Constructor buit requerit per JPA
     */
    public Reserva() {
    }
    
    // --- MÈTODE PER A FORMATAR LA DATA ---
    
    /**
     * Mètode per a formatar la data de la reserva.
     * 
     * @return data de la reserva formatada.
     */
    public String getDataReservaFormatted() {
        if (dataReserva == null) return "";
        return dataReserva.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    // Getters i Setters
    
    /**
     * Retorna el id de la reserva.
     * 
     * @return id de la reserva.
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Assigna un id de la reserva.
     * 
     * @param idReserva id de la reserva.
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    
    /**
     * Retorna l'usuari.
     * 
     * @return usuari.
     */
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Assigna un usuari.
     * 
     * @param usuari usuari.
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Retorna el llibre.
     * 
     * @return llibre.
     */
    public Llibre getLlibre() {
        return llibre;
    }
    
    /**
     * Assigna un llibre.
     * 
     * @param llibre llibre.
     */
    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    /**
     * Retorna la data de la reserva.
     * 
     * @return data de la reserva.
     */
    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    /**
     * Assigna una data de la reserva.
     * 
     * @param dataReserva data de la reserva.
     */
    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    /**
     * Retorna l'estat de la reserva.
     * 
     * @return estat de la reserva.
     */
    public EstatReserva getEstat() {
        return estat;
    }

    /**
     * Assigna un estat de la reserva.
     * 
     * @param estat estat de la reserva.
     */
    public void setEstat(EstatReserva estat) {
        this.estat = estat;
    }
}
