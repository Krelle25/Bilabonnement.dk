package com.example.bilabonnement_dk.repository;

import com.example.bilabonnement_dk.model.Leasing;
import com.example.bilabonnement_dk.model.Medarbejder;
import com.example.bilabonnement_dk.model.Skaderapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkadeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Indsætter en ny Skaderapport og returnerer den genererede ID
    public int insertAndReturnID(Skaderapport skaderapport) {
        String sql = """
                INSERT INTO skaderapport (leasing_ID, medarbejder_ID, pris, arbejdstid)
                VALUES (?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, skaderapport.getLeasing().getLeasing_ID());
            ps.setInt(2, skaderapport.getMedarbejder().getMedarbejder_ID());
            ps.setDouble(3, skaderapport.getPris());
            ps.setInt(4, skaderapport.getArbejdstid());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // Opdaterer prisen på en eksisterende Skaderapport
    public void updatePrice(int skaderapport_ID, double nyPris) {
        String sql = "UPDATE skaderapport SET pris = ? WHERE skaderapport_ID = ?";
        jdbcTemplate.update(sql, nyPris, skaderapport_ID);
    }

    // Henter alle Skaderapporter med tilhørende Leasing og Medarbejder information
    public List<Skaderapport> fetchAll() {
        String sql = """
                SELECT s.*,
                       l.leasing_ID,
                       m.medarbejder_ID, m.fornavn, m.efternavn
                FROM skaderapport s
                JOIN leasing l ON s.leasing_ID = l.leasing_ID
                JOIN medarbejder m ON s.medarbejder_ID = m.medarbejder_ID
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Leasing leasing = new Leasing();
            leasing.setLeasing_ID(rs.getInt("leasing_ID"));

            Medarbejder medarbejder = new Medarbejder();
            medarbejder.setMedarbejder_ID(rs.getInt("medarbejder_ID"));
            medarbejder.setFornavn(rs.getString("fornavn"));
            medarbejder.setEfternavn(rs.getString("efternavn"));

            return new Skaderapport(
                    rs.getInt("skaderapport_ID"),
                    rs.getDouble("pris"),
                    rs.getInt("arbejdstid"),
                    leasing,
                    medarbejder,
                    new ArrayList<>()
            );
        });
    }

    // Finder en Skaderapport ud fra ID med tilhørende Leasing og Medarbejder data
    public Skaderapport findByID(int skaderapport_ID) {
        String sql = """
                SELECT s.*, l.leasing_ID,
                       m.medarbejder_ID, m.fornavn, m.efternavn
                FROM skaderapport s
                JOIN leasing l ON s.leasing_ID = l.leasing_ID
                JOIN medarbejder m ON s.medarbejder_ID = m.medarbejder_ID
                WHERE s.skaderapport_ID = ?
                """;

        List<Skaderapport> result = jdbcTemplate.query(sql, new Object[]{skaderapport_ID}, (rs, rowNum) -> {
            Leasing leasing = new Leasing();
            leasing.setLeasing_ID(rs.getInt("leasing_ID"));

            Medarbejder medarbejder = new Medarbejder();
            medarbejder.setMedarbejder_ID(rs.getInt("medarbejder_ID"));
            medarbejder.setFornavn(rs.getString("fornavn"));
            medarbejder.setEfternavn(rs.getString("efternavn"));

            return new Skaderapport(
                    rs.getInt("skaderapport_ID"),
                    rs.getDouble("pris"),
                    rs.getInt("arbejdstid"),
                    leasing,
                    medarbejder,
                    new ArrayList<>()
            );
        });

        return result.isEmpty() ? null : result.get(0);
    }

    // Opdaterer arbejdstid og pris på en eksisterende Skaderapport
    public void updateSkaderapport(int skaderapport_ID, int arbejdstid, double pris) {
        String sql = "UPDATE skaderapport SET arbejdstid = ?, pris = ? WHERE skaderapport_ID = ?";
        jdbcTemplate.update(sql, arbejdstid, pris, skaderapport_ID);
    }

    // Sletter en Skaderapport ud fra ID
    public void deleteSkaderapport(int skaderapport_ID) {
        String sql = "DELETE FROM skaderapport WHERE skaderapport_ID = ?";
        jdbcTemplate.update(sql, skaderapport_ID);
    }
}