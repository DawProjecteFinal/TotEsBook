package cat.totesbook.repository.impl;

import cat.totesbook.config.DBConnection;
import java.util.List;
import org.springframework.stereotype.Repository;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.LlibreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery; // Import necessari
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mètode que obté tots els llibres de la BD mitjançant EntityManager.
 * 
 * @author equip TotEsBook
 */
@Repository // Li diem a Spring que gestioni aquest Bean
public class LlibreDAO implements LlibreRepository {

    // Aquest DAO ara fa servir JDBC manual, igual que UsuariDAO i AgentDAO
    
    @Override
    public List<Llibre> getAllLlibres() {
        List<Llibre> llibres = new ArrayList<>();
        // Assegura't que els noms de columna (titol, autor, etc.) coincideixen amb init.sql
        String sql = "SELECT isbn, titol, autor, editorial, categoria, sinopsis, imatgeUrl, exemplars, disponibles, idioma FROM Llibres"; 
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                llibres.add(mapRowToLlibre(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error a LlibreDAO.getAllLlibres: " + e.getMessage());
            e.printStackTrace();
        }
        return llibres;
    }

    @Override
    public void addLlibre(Llibre llibre) {
        // Aquest mètode s'encarrega d'INSERIR o ACTUALITZAR (UPSERT)
        // Primer, comprovem si el llibre (per ISBN) ja existeix
        if (getLlibreByIsbn(llibre.getIsbn()).isPresent()) {
            // Si existeix, fem UPDATE
            String sql = "UPDATE Llibres SET titol = ?, autor = ?, editorial = ?, categoria = ?, " +
                         "sinopsis = ?, imatgeUrl = ?, idioma = ?, exemplars = ?, disponibles = ? WHERE isbn = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, llibre.getTitol());
                ps.setString(2, llibre.getAutor());
                ps.setString(3, llibre.getEditorial());
                ps.setString(4, llibre.getCategoria());
                ps.setString(5, llibre.getSinopsis());
                ps.setString(6, llibre.getImatgeUrl());
                ps.setString(7, llibre.getIdioma());
                ps.setInt(8, llibre.getExemplars());
        		ps.setInt(9, llibre.getDisponibles());
                ps.setString(10, llibre.getIsbn()); // ISBN pel WHERE
                ps.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Error a LlibreDAO.addLlibre (UPDATE): " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Si no existeix, fem INSERT
            String sql = "INSERT INTO Llibres (isbn, titol, autor, editorial, categoria, sinopsis, imatgeUrl, exemplars, disponibles, idioma) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, llibre.getIsbn());
                ps.setString(2, llibre.getTitol());
                ps.setString(3, llibre.getAutor());
                ps.setString(4, llibre.getEditorial());
                ps.setString(5, llibre.getCategoria());
                ps.setString(6, llibre.getSinopsis());
                ps.setString(7, llibre.getImatgeUrl());
                ps.setInt(8, llibre.getExemplars());
                ps.setInt(9, llibre.getDisponibles());
                ps.setString(10, llibre.getIdioma());
                ps.executeUpdate();
                
            } catch (SQLException e) {
                System.err.println("Error a LlibreDAO.addLlibre (INSERT): " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        String sql = "SELECT * FROM Llibres WHERE isbn = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, isbn);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToLlibre(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error a LlibreDAO.getLlibreByIsbn: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty(); // No trobat
    }

    /**
     * Mètode privat d'ajuda per convertir una fila de ResultSet a un objecte Llibre.
     * Això evita repetir codi.
     */
    private Llibre mapRowToLlibre(ResultSet rs) throws SQLException {
        Llibre llibre = new Llibre();
        llibre.setIsbn(rs.getString("isbn"));
        llibre.setTitol(rs.getString("titol"));
        llibre.setAutor(rs.getString("autor"));
        llibre.setEditorial(rs.getString("editorial"));
        llibre.setCategoria(rs.getString("categoria"));
        llibre.setSinopsis(rs.getString("sinopsis"));
        llibre.setImatgeUrl(rs.getString("imatgeUrl"));
        llibre.setExemplars(rs.getInt("exemplars"));
        llibre.setDisponibles(rs.getInt("disponibles"));
        llibre.setIdioma(rs.getString("idioma"));
        return llibre;
    }
}
