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
}
