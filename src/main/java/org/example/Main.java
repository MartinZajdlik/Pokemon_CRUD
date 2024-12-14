package org.example;

import org.example.func.PokemonService;
import org.example.func.TrainerService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = ConnectDb.getConnection()) {
            System.out.println("Successfully connected to database!");

            TrainerService trainerService = new TrainerService(connection);

            trainerService.printingTrainers();

            PokemonService pokemonService = new PokemonService(connection);

            pokemonService.printingPokemons();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}
