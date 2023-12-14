package com.inpe.auxiliaresservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.annotation.Nullable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.exclude;

@Service
public class MunicipiosService {
    private final MongoCollection<Document> collection;
    private final ObjectMapper objectMapper;

    @Autowired
    public MunicipiosService(MongoClient mongoClient, ObjectMapper objectMapper) {
        this.collection = mongoClient.getDatabase("auxiliares").getCollection("municipios");
        this.objectMapper = objectMapper;
    }

    public String getMunicipios(
            @Nullable Integer id_pais,
            @Nullable Integer id_estado,
            @Nullable String nome_pais,
            @Nullable String nome_estado,
            @Nullable String nome_municipio
    ) throws JsonProcessingException {
        Document query = new Document();
        if(id_pais != null) {
            query.append("id_pais", id_pais);
        }

        if(id_estado != null) {
            query.append("id_estado", id_estado);
        }

        if(nome_pais != null) {
            query.append("nome_pais", new Document("$regex", nome_pais).append("$options", "i"));
        }

        if(nome_estado != null) {
            query.append("nome_estado", new Document("$regex", nome_estado).append("$options", "i"));
        }

        if(nome_municipio != null) {
            query.append("nome_municipio", new Document("$regex", nome_municipio).append("$options", "i"));
        }

        MongoCursor<Document> cursor = this.collection.find(query).projection(exclude("_id")).iterator();
        List<Document> documents = new ArrayList<>();
        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }

        return objectMapper.writeValueAsString(documents);
    }
}
