package com.inpe.focosservice.services;

import com.inpe.focosservice.database.PostgresDriver;
import com.inpe.focosservice.entities.MunicipiosRow;
import com.inpe.focosservice.entities.MunicipiosRowPorDia;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MunicipiosService {
    private final DateTimeFormatter formatter;
    private final PostgresDriver db;

    @Autowired
    public MunicipiosService(PostgresDriver db) {
        this.db = db;
        this.formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    public List<MunicipiosRow> getMunicipios(
            LocalDate data_inicio,
            LocalDate data_fim,
            String satelite,
            Long id_pais,
            Long id_estado,
            @Nullable Long id_municipio
    ) {
        // Select
        String query = "select vfm.name_2 as municipio, vfm.name_1 as estado, sum(vfm.nfocos) as focos\n";

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

        query += String.format(
                "and vfm.id_0 = %d\nand vfm.id_1 = %d\n",
                id_pais,
                id_estado
        );

        if (id_municipio != null) {
            query += String.format(
                    "and vfm.id_2 = %d\n",
                    id_municipio
            );
        }

        query += "group by 1, 2 order by 1, 2 asc;";

        System.out.println(query);

        List<MunicipiosRow> response_db = this.db.executeQuery(query, MunicipiosRow.class);

        return response_db;
    }

    public List<MunicipiosRowPorDia> getMunicipiosPorDia(
            LocalDate data_inicio,
            LocalDate data_fim,
            String satelite,
            Long id_pais,
            Long id_estado,
            @Nullable Long id_municipio
    ) {
        // Select
        String query = "select vfm.name_2 as municipio, vfm.name_1 as estado, sum(vfm.nfocos) as focos, vfm.data\n";

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

        query += String.format(
                "and vfm.id_0 = %d\nand vfm.id_1 = %d\n",
                id_pais,
                id_estado
        );

        if (id_municipio != null) {
            query += String.format(
                    "and vfm.id_2 = %d\n",
                    id_estado
            );
        }

        query += "group by 1, 2, 4 order by 1, 2, 4 asc;";

        System.out.println(query);

        List<MunicipiosRowPorDia> response_db = this.db.executeQuery(query, MunicipiosRowPorDia.class);

        return response_db;
    }
}
