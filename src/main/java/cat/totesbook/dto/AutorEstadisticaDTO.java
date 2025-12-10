package cat.totesbook.dto;

/**
 * DTO que representa les estadístiques d'un autor.
 * 
 * @author equip TotEsBook
 */
public class AutorEstadisticaDTO {
    private String autor;
    private Long totalPrestecs;

    /**
     * Creació d'un objecte amb l'autor i el total de préstecs. 
     * 
     * @param autor L'autor del llibre.
     * @param totalPrestecs El total de préstecs.
     */
    public AutorEstadisticaDTO(String autor, Long totalPrestecs) {
        this.autor = autor;
        this.totalPrestecs = totalPrestecs;
    }

    /**
     * Retorna el nom de l'autor.
     * 
     * @return El nom de l'autor.
     */
    public String getAutor() { 
        return autor; 
    }
    
    /**
     * Retorna el total de préstecs.
     * 
     * @return El total dels préstecs que s'han fet a nom de l'autor.
     */
    public Long getTotalPrestecs() { 
        return totalPrestecs; 
    }
}