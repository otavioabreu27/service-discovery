package com.inpe.focosservice.services;

import com.inpe.focosservice.database.PostgresDriver;
import com.inpe.focosservice.entities.EstadosRow;
import com.inpe.focosservice.entities.EstadosRowPorDia;
import com.inpe.focosservice.entities.PaisesRow;
import com.inpe.focosservice.entities.PaisesRowPorDia;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EstadosService {
    private final DateTimeFormatter formatter;
    private final PostgresDriver db;

    @Autowired
    public EstadosService(PostgresDriver db) {
        this.db = db;
        this.formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    public List<EstadosRow> getEstados(
            LocalDate data_inicio,
            LocalDate data_fim,
            String satelite,
            Long id_pais,
            @Nullable Long id_estado
    ) {
        // Select
        String query = "select vfm.name_1 as estado, sum(vfm.nfocos) as focos\n";

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
                "and vfm.id_0 = %d\n",
                id_pais
        );

        if (id_estado != null) {
            query += String.format(
                    "and vfm.id_1 = %d\n",
                    id_estado
            );
        }

        query += "group by 1 order by 1 asc;";

        System.out.println(query);

        List<EstadosRow> response_db = this.db.executeQuery(query, EstadosRow.class);

        return response_db;
    }

    public List<EstadosRowPorDia> getEstadosPorDia(
            LocalDate data_inicio,
            LocalDate data_fim,
            String satelite,
            Long id_pais,
            @Nullable Long id_estado
    ) {
        // Select
        String query = "select vfm.name_1 as estado, sum(vfm.nfocos) as focos, vfm.data\n";

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
                "and vfm.id_0 = %d\n",
                id_pais
        );

        if (id_estado != null) {
            query += String.format(
                    "and vfm.id_1 = %d\n",
                    id_estado
            );
        }

        query += "group by 1, 3 order by 1, 3 asc;";

        System.out.println(query);

        List<EstadosRowPorDia> response_db = this.db.executeQuery(query, EstadosRowPorDia.class);

        return response_db;
    }
}
