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
}
