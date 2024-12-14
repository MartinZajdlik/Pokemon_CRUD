package org.example.func;

import java.sql.*;

public class TrainerService {
    private Connection connection;

    public TrainerService(Connection connection) {
        this.connection = connection;
    }

    public void printingTrainers() {

        String sql = "SELECT * FROM trainers";


        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(sql)) {

                System.out.println("Trainers:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("trainer_id");
                    String name = resultSet.getString("name");
                    int pokemonCount = resultSet.getInt("pokemon_count");

                    System.out.printf("ID: %d, Name: %s, Pokemon count: %d%n", id, name, pokemonCount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error printing trainers.");
        }
    }
    public void printPokemonsByTrainer(String trainerName) {
        String sql = "SELECT p.name FROM pokemons p JOIN trainers t ON p.trainer_id = t.trainer_id WHERE t.name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trainerName);
            try (ResultSet resultSet = statement.executeQuery()) {
                // Pokud výsledek neobsahuje žádné Pokémony, vypíšeme zprávu
                if (!resultSet.isBeforeFirst()) { // Kontrola, zda není prázdný
                    System.out.println("Name isn't in database.");
                    return; // Ukončení metody, pokud trenér není v databázi
                }

                System.out.printf("Pokemons belonging to trainer %s:%n", trainerName);
                while (resultSet.next()) {
                    String pokemonName = resultSet.getString("name");
                    System.out.println(pokemonName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error printing pokemons for trainer.");
        }
    }
}