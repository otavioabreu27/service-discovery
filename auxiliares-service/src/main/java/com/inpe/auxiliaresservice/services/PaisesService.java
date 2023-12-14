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
public class PaisesService {
    private final MongoCollection<Document> collection;
    private final ObjectMapper objectMapper;

    @Autowired
    public PaisesService(MongoClient mongoClient, ObjectMapper objectMapper) {
        this.collection = mongoClient.getDatabase("auxiliares").getCollection("paises");
        this.objectMapper = objectMapper;
    }

    public String getPaises(
            @Nullable String nome_pais
    ) throws JsonProcessingException {
        if(nome_pais != null) {
            Document query = new Document("nome", new Document("$regex", nome_pais).append("$options", "i"));
            MongoCursor<Document> cursor = this.collection.find(query).projection(exclude("_id")).iterator();
            List<Document> documents = new ArrayList<>();
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }

            return objectMapper.writeValueAsString(documents);
        } else {
            MongoCursor<Document> cursor = this.collection.find().projection(exclude("_id")).iterator();
            List<Document> documents = new ArrayList<>();
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }

            return objectMapper.writeValueAsString(documents);
        }
    }
}