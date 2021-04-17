package com.github.kozosjavak.asteroidmining.console;

import com.github.kozosjavak.asteroidmining.console.adapter.CreateAsteroidAdapter;
import com.github.kozosjavak.asteroidmining.console.adapter.StartCommandAdapter;
import com.github.kozosjavak.asteroidmining.console.adapter.StringCommandAdapter;
import com.github.kozosjavak.asteroidmining.core.Game;
import com.github.kozosjavak.asteroidmining.core.commands.Command;
import com.github.kozosjavak.asteroidmining.core.commands.CommandExecutor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console communicator
 */
public class ConsoleCommandExecutor extends CommandExecutor {
    //Ird be a listaba az uj adaptert.
    private static final List<StringCommandAdapter> adapterCommandList = List.of(
            new StartCommandAdapter(),
            new CreateAsteroidAdapter()
    );

    public ConsoleCommandExecutor(Game game) {
        super(game);
    }

    //Magic
    public void attachToConsole() {

        try (Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextLine()) {
                Optional<Command> optionalCommand = adapterCommandList.stream()
                        .map(stringCommandAdapter -> stringCommandAdapter.parse(scanner.nextLine()))
                        .filter(Objects::nonNull)
                        .findAny();
                if (optionalCommand.isPresent()) {
                    optionalCommand.get().apply(getGame());
                } else {
                    System.out.println("Wrong command");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
