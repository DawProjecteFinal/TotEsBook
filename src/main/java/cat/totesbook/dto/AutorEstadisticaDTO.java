package cat.totesbook.dto;

/**
 *
 * @author edinsonioc
 */
public class AutorEstadisticaDTO {
    private String autor;
    private Long totalPrestecs;

    public AutorEstadisticaDTO(String autor, Long totalPrestecs) {
        this.autor = autor;
        this.totalPrestecs = totalPrestecs;
    }

    public String getAutor() { 
        return autor; 
    }
    
    public Long getTotalPrestecs() { 
        return totalPrestecs; 
    }
}