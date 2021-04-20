package com.github.kozosjavak.asteroidmining.console;

import com.github.kozosjavak.asteroidmining.console.adapter.*;
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
    /**
     * List of the adapters of the commands which can be performed
     */
    private static final List<StringCommandAdapter> adapterCommandList = List.of(
            new StartCommandAdapter(),
            new CreateAsteroidAdapter(),
            new CreateMaterialAdapter(),
            new SpaceshipMoveAdapter(),
            new CreateSettlerAdapter(),
            new AddNeighborAdapter(),
            new CreateSettlerAdapter(),
            new SettlerDrillAdapter(),
            new CreateSettlerAdapter(),
            new InsertMaterialAdapter(),
            new CreateSettlerAdapter(),
            new LinkTeleportsAdapter(),
            new CreateSettlerAdapter(),
            new CreateSunAdapter(),
            new SettlerAddMaterialAdapter(),
            new SettlerBuildBaseAdapter(),
            new SettlerAddMaterialAdapter(),
            new StartSolarStormAdapter(),
            new SettlerRemoveMaterialAdapter(),
            new ShowGameStateAdapter(),
            new CreateSunAdapter(),
            new SettlerAddMaterialAdapter(),
            new UfoStealAdapter(),
            new SettlerBuildRobotAdapter(),
            new SettlerBuildTeleportAdapter(),
            new SettlerBuildRobotAdapter(),
            new UfoStealAdapter(),
            new CreateRobotAdapter(),
            new CreateTeleportAdapter(),
            new AsteroidExplodeAdapter(),
            new CreateTeleportAdapter(),
            new AddTeleportAdapter(),
            new SettlerUseTeleportAdapter(),
            new SettlerInsertMaterialAdapter()
    );

    public ConsoleCommandExecutor(Game game) {
        super(game);
    }

    /**
     * Connects to the console and listen
     */
    public void attachToConsole() {

        try (Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                Optional<Command> optionalCommand = adapterCommandList.stream()
                        .map(stringCommandAdapter -> stringCommandAdapter.parse(line))
                        .filter(Objects::nonNull)
                        .findFirst();
                if (optionalCommand.isPresent()) {
                    execute(optionalCommand.get());
                } else {
                    System.out.println("Wrong command");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
