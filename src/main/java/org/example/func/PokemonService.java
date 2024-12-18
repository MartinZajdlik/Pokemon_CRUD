package org.example.func;

import java.sql.*;

public class PokemonService {
    private Connection connection;

    public PokemonService(Connection connection) {
        this.connection = connection;
    }

    public void printingPokemons() {

        String sql = "SELECT * FROM pokemons";


        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(sql)) {

                System.out.println("Pokemons:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("pokemon_id");
                    String name = resultSet.getString("name");
                    int trainerId = resultSet.getInt("trainer_id");

                    System.out.printf("ID: %d, Name: %s, Trainer ID: %d%n", id, name, trainerId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error printing pokemon.");
        }
    }
    public void printUncaughtPokemons() {
        String sql = "SELECT name FROM pokemons WHERE trainer_id IS NULL OR trainer_id = 0";

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                System.out.println("Uncaught Pokemons:");
                while (resultSet.next()) {
                    String pokemonName = resultSet.getString("name");
                    System.out.println(pokemonName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error printing uncaught pokemons.");
        }
    }
    public void addPokemonToTrainer(String pokemonName, int trainerId) {
        String sql = "INSERT INTO pokemons (name, trainer_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pokemonName);
            statement.setInt(2, trainerId);
            statement.executeUpdate();
            System.out.printf("Pokemon '%s' added and assigned to trainer with ID %d.%n", pokemonName, trainerId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding pokemon to trainer.");
        }
    }
}
