package org.example;

import org.example.func.PokemonService;
import org.example.func.TrainerService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = ConnectDb.getConnection()) {
            System.out.println("Successfully connected to database!");




            Scanner scanner = new Scanner(System.in);
            TrainerService trainerService = new TrainerService(connection);
            PokemonService pokemonService = new PokemonService(connection);

            trainerService.printingTrainers();
            pokemonService.printingPokemons();

            System.out.println();

            System.out.println("1 - Print out the Pokemon that belong to the trainer");
            System.out.println("2 – List the Pokemon that don't belong to anyone, they haven't been caught");
            System.out.println("3 – Přidej nového trenéra");
            System.out.print("Choose number: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Vyčištění bufferu po zadání čísla

            switch (choice) {
                case 1:
                    System.out.print("Trainer name: ");
                    String trainerName = scanner.nextLine();
                    trainerService.printPokemonsByTrainer(trainerName);
                    break;
                case 2:
                    pokemonService.printUncaughtPokemons();
                    break;
                default:
                    System.out.println("Filed choice.");

                case 3: // Přidání nového trenéra
                    System.out.print("Zadej jméno nového trenéra: ");
                    String newTrainerName = scanner.nextLine();

                    System.out.print("Zadej jména Pokémonů oddělená čárkou (např. Pikachu,Charmander): ");
                    String pokemonInput = scanner.nextLine();
                    String[] pokemonNames = pokemonInput.split(","); // Rozdělení na pole jmen

                    trainerService.addTrainerWithPokemons(newTrainerName, pokemonNames); // Volání nové metody
                    break;



            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}
