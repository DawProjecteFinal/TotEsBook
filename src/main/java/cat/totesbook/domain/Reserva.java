
package cat.totesbook.domain;

/**
 *
 * @author jmiro
 */
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Reserves")
public class Reserva {

    public enum EstatReserva {
        pendent, disponible, caducada, cancelada
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

    public Reserva() {
    }
    
    // --- MÈTODE PER A FORMATAR LA DATA ---
    public String getDataReservaFormatted() {
        if (dataReserva == null) return "";
        return dataReserva.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    // Getters i Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public EstatReserva getEstat() {
        return estat;
    }

    public void setEstat(EstatReserva estat) {
        this.estat = estat;
    }
}
