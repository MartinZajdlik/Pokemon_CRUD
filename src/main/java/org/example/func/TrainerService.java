package org.example.func;
import java.sql.*;

public class TrainerService {
    private Connection connection;
    private PokemonService pokemonService;

    public TrainerService(Connection connection) {
        this.connection = connection;
        this.pokemonService = pokemonService;
    }

    public void printingTrainers() {
        String sql = "SELECT * FROM trainers";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Trainers:");
            while (resultSet.next()) {
                int id = resultSet.getInt("trainer_id");
                String name = resultSet.getString("name");
                int pokemonCount = resultSet.getInt("pokemon_count");

                System.out.printf("ID: %d, Name: %s, Pokemon count: %d%n", id, name, pokemonCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error printing trainers.");
        }
    }

    public void addTrainerWithPokemons(String name, String[] pokemonNames) { // UPDATE
        if (isTrainerExists(name)) { // UPDATE
            System.out.printf("Trainer with name '%s' already exists!%n", name); // UPDATE
            return; // UPDATE
        } // UPDATE

        String trainerSql = "INSERT INTO trainers (name, pokemon_count) VALUES (?, ?)"; // UPDATE

        try (PreparedStatement trainerStatement = connection.prepareStatement(trainerSql, Statement.RETURN_GENERATED_KEYS)) { // UPDATE
            trainerStatement.setString(1, name); // UPDATE
            trainerStatement.setInt(2, pokemonNames.length); // UPDATE
            trainerStatement.executeUpdate(); // UPDATE

            try (ResultSet generatedKeys = trainerStatement.getGeneratedKeys()) { // UPDATE
                if (generatedKeys.next()) { // UPDATE
                    int trainerId = generatedKeys.getInt(1); // UPDATE

                    for (String pokemonName : pokemonNames) { // UPDATE
                        // Instead of checking and updating each Pokémon individually, we'll use the new method
                        pokemonService.addPokemonToTrainer(pokemonName, trainerId); // UPDATE
                    } // UPDATE
                } else { // UPDATE
                    System.out.println("Failed to retrieve trainer ID."); // UPDATE
                } // UPDATE
            } // UPDATE
        } catch (SQLException e) { // UPDATE
            e.printStackTrace(); // UPDATE
            System.out.println("Error adding trainer and their pokemons."); // UPDATE
        } // UPDATE
    }

    private boolean isTrainerExists(String name) { // UPDATE
        String sql = "SELECT COUNT(*) FROM trainers WHERE name = ?"; // UPDATE
        try (PreparedStatement statement = connection.prepareStatement(sql)) { // UPDATE
            statement.setString(1, name); // UPDATE
            try (ResultSet resultSet = statement.executeQuery()) { // UPDATE
                if (resultSet.next()) { // UPDATE
                    return resultSet.getInt(1) > 0; // UPDATE
                } // UPDATE
            } // UPDATE
        } catch (SQLException e) { // UPDATE
            e.printStackTrace(); // UPDATE
            System.out.println("Error checking if trainer exists."); // UPDATE
        } // UPDATE
        return false; // UPDATE
    }

    private boolean assignPokemonToTrainer(String pokemonName, int trainerId) {
        String sql = "UPDATE pokemons SET trainer_id = ? WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trainerId);
            statement.setString(2, pokemonName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Pokemon '%s' successfully assigned to trainer with ID %d.%n", pokemonName, trainerId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error assigning pokemon to trainer.");
        }
        return false;
    }
    public void printPokemonsByTrainer(String trainerName) {
        String sql = "SELECT p.name FROM pokemons p " +
                "JOIN trainers t ON p.trainer_id = t.trainer_id " +
                "WHERE t.name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trainerName);

            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.printf("Pokémon that belong to a trainer '%s':%n", trainerName);
                while (resultSet.next()) {
                    String pokemonName = resultSet.getString("name");
                    System.out.println(pokemonName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error listing Pokemon for trainer.");
        }
    }

}
