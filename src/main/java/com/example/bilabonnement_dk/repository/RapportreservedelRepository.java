package com.example.bilabonnement_dk.repository;

import com.example.bilabonnement_dk.model.Rapportreservedel;
import com.example.bilabonnement_dk.model.Reservedel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RapportreservedelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Indsætter en ny rapportreservedel i databasen
    public void insertRapportreservedel(Rapportreservedel rapportreservedel) {
        String sql = """
                INSERT INTO rapportreservedel (reservedel_ID, skaderapport_ID, antal)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                rapportreservedel.getReservedel().getReservedel_ID(),
                rapportreservedel.getSkaderapport().getSkaderapport_ID(),
                rapportreservedel.getAntal()
        );
    }

    // Henter alle rapportreservedele tilknyttet en given skaderapport via skaderapport_ID
    public List<Rapportreservedel> findBySkaderapportID(int skaderapport_ID) {
        String sql = """
                SELECT rr.*,
                       r.reservedel_ID, r.type, r.pris
                FROM rapportreservedel rr
                JOIN reservedel r ON rr.reservedel_ID = r.reservedel_ID
                WHERE rr.skaderapport_ID = ?
                """;

        return jdbcTemplate.query(sql, new Object[]{skaderapport_ID}, (rs, rowNum) -> {
            // Opretter en Reservedel-objekt ud fra resultatsættet
            Reservedel reservedel = new Reservedel(
                    rs.getInt("reservedel_ID"),
                    rs.getString("type"),
                    rs.getDouble("pris")
            );

            // Opretter og returnerer en Rapportreservedel med data og tilknyttet Reservedel
            Rapportreservedel rr = new Rapportreservedel();
            rr.setAntalReservedele_ID(rs.getInt("antalreservedele_ID"));
            rr.setReservedel(reservedel);
            rr.setAntal(rs.getInt("antal"));
            return rr;
        });
    }

    // Sletter alle rapportreservedele, der er tilknyttet en given skaderapport via skaderapport_ID
    public void deleteSkaderapportByID(int skaderapport_ID) {
        String sql = "DELETE FROM rapportreservedel WHERE skaderapport_ID = ?";
        jdbcTemplate.update(sql, skaderapport_ID);
    }
}