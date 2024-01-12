package com.inpe.focosservice.services;

import com.inpe.focosservice.database.PostgresDriver;
import com.inpe.focosservice.entities.BiomasRow;
import com.inpe.focosservice.entities.BiomasRowPorDia;
import com.inpe.focosservice.entities.PaisesRow;
import com.inpe.focosservice.entities.PaisesRowPorDia;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BiomasService {
    private final DateTimeFormatter formatter;
    private final PostgresDriver db;

    @Autowired
    public BiomasService(PostgresDriver db) {
        this.db = db;
        this.formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    public List<BiomasRow> getBiomas(
            LocalDate data_inicio,
            LocalDate data_fim,
            String satelite,
            @Nullable String bioma
    ) {
        // Select
        String query = "select vfm.bioma as bioma, sum(vfm.nfocos) as focos\n";

        // From
        query += "from api_dados_abertos.view_focos_munic vfm\n";

        // Where
        query += String.format(
                "where vfm.data >= '%s'\nand vfm.data <= '%s'\n",
                data_inicio.format(this.formatter),
                data_fim.format(this.formatter)
        );

        if(satelite.equals("referencia")) {
            query += "and vfm.eh_satelite_referencia\n";
        } else {
            query += String.format(
                    "and vfm.satelite = '%s'\n",
                    satelite
            );
        }

        if (bioma != null) {
            query += String.format(
                    "and vfm.bioma = '%s'\n",
                    bioma
            );
        }

        query += "and vfm.bioma is not null\ngroup by 1 order by 1 asc;";

        System.out.println(query);

        List<BiomasRow> response_db = this.db.executeQuery(query, BiomasRow.class);

        return response_db;
    }

    public List<BiomasRowPorDia> getBiomasPorDia(
            LocalDate data_inicio,
            LocalDate data_fim,
            String satelite,
            @Nullable String bioma
    ) {
        // Select
        String query = "select vfm.bioma as bioma, sum(vfm.nfocos) as focos, vfm.data\n";

        // From
        query += "from api_dados_abertos.view_focos_munic vfm\n";

        // Where
        query += String.format(
                "where vfm.data >= '%s'\nand vfm.data <= '%s'\n",
                data_inicio.format(this.formatter),
                data_fim.format(this.formatter)
        );

        if(satelite.equals("referencia")) {
            query += "and vfm.eh_satelite_referencia\n";
        } else {
            query += String.format(
                    "and vfm.satelite = '%s'\n",
                    satelite
            );
        }

        if (bioma != null) {
            query += String.format(
                    "and vfm.bioma = '%s'\n",
                    bioma
            );
        }

        query += "and vfm.bioma is not null\ngroup by 1, 3 order by 1, 3 asc;";

        System.out.println(query);

        List<BiomasRowPorDia> response_db = this.db.executeQuery(query, BiomasRowPorDia.class);

        return response_db;
    }
}
