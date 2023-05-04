package view;

import controller.Building.BuildingController;
import controller.Building.SelectedBuildingController;
import model.Building.Building;
import model.Building.Shop;
import model.Empire;
import view.Commands.SelectedBuildingCommands;
import view.Messages.SelectedBuildingMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectedBuildingMenu {
    public static Empire currentEmpire;
    public static Building selectedBuilding;
    String input;

    public void run(Scanner scanner) {
        SelectedBuildingController selectedBuildingController = new SelectedBuildingController();
        SelectedBuildingController.selectedBuilding = selectedBuilding;
        SelectedBuildingController.empire = currentEmpire;
        String buildingName = selectedBuilding.getNameEnum().getName();
        while (true) {
            input = scanner.nextLine();
            if (SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_COMMANDS_CREATE_UNIT) != null) {
                if (SelectedBuildingCommands.getMatcher(buildingName, SelectedBuildingCommands.SELECTED_BUILDING_CREATE_UNIT_BUILDINGS_NAME) != null) {
                    Matcher matcherType = SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_COMMANDS_FIND_CREATE_UNIT_TYPE);
                    Matcher matcherCount = SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_COMMANDS_FIND_COUNT);
                    if (matcherType != null && matcherCount != null) {
                        if (SelectedBuildingCommands.getMatcher(matcherType.group("type"), SelectedBuildingCommands.SELECTED_BUILDING_COMMANDS_ALL_TROOPS_NAME) == null) {
                            System.out.println(SelectedBuildingMessages.INVALID_TROOP_NAME.getName());
                            return;
                        } else if (SelectedBuildingCommands.getMatcher(matcherType.group("type"), SelectedBuildingCommands.SELECTED_BUILDING_BARRACKS_TROOP_NAME_CHECK) != null && buildingName.equals("barracks")) {
                            System.out.println(selectedBuildingController.Barracks(matcherType, matcherCount).getName());
                        } else if (SelectedBuildingCommands.getMatcher(matcherType.group("type"), SelectedBuildingCommands.SELECTED_BUILDING_MERCENARY_TROOP_NAME_CHECK) != null && buildingName.equals("mercenary")) {
                            System.out.println(selectedBuildingController.mercenary(matcherType, matcherCount).getName());
                        } else if (SelectedBuildingCommands.getMatcher(matcherType.group("type"), SelectedBuildingCommands.SELECTED_BUILDING_SIEGE_TENT_TROOP_NAME_CHECK) != null && buildingName.equals("siege tent")) {
                            System.out.println(selectedBuildingController.siegeTent(matcherType, matcherCount).getName());
                        } else if (SelectedBuildingCommands.getMatcher(matcherType.group("type"), SelectedBuildingCommands.SELECTED_BUILDING_CHURCH_TROOP_NAME_CHECK) != null && (buildingName.equals("small church") | buildingName.equals("big church"))) {
                            System.out.println(selectedBuildingController.church(matcherCount).getName());
                        } else if (SelectedBuildingCommands.getMatcher(matcherType.group("type"), SelectedBuildingCommands.SELECTED_BUILDING_ENGINEER_GUILD_TROOP_NAME_CHECK) != null && buildingName.equals("engineerGuild")) {
                            System.out.println(selectedBuildingController.engineerGuild(matcherType, matcherCount).getName());
                        } else
                            System.out.println(SelectedBuildingMessages.WRONG_BUILDING_TO_CREATE_TROOP);
                    } else {
                        System.out.println(SelectedBuildingMessages.INVALID_COMMAND.getName());
                    }
                } else {
                    System.out.println(SelectedBuildingMessages.BUILDING_CANT_CREATE_UNIT.getName());
                }
            } else if (SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_TAX_RATE) != null) {
                if (buildingName.equals("small stone gatehouse") | buildingName.equals("big stone gatehouse")) {
                    Matcher matcherTaxRate = SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_TAX_RATE);
                    if (matcherTaxRate != null) {
                        System.out.println(selectedBuildingController.gatehouse(matcherTaxRate).getName());
                    } else {
                        System.out.println(SelectedBuildingMessages.INVALID_COMMAND.getName());
                    }
                } else {
                    System.out.println(SelectedBuildingMessages.WRONG_BUILDING_CHOSEN.getName());
                }
            } else if (SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_DRAW_BRIDGE) != null) {
                if (buildingName.equals("drawbridge")) {
                    Matcher matcherBridgeCondition = SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_DRAW_BRIDGE);
                    if (matcherBridgeCondition != null) {
                        System.out.println(selectedBuildingController.drawBridge(matcherBridgeCondition).getName());
                    } else {
                        System.out.println(SelectedBuildingMessages.INVALID_COMMAND.getName());
                    }
                } else {
                    System.out.println(SelectedBuildingMessages.WRONG_BUILDING_CHOSEN.getName());
                }
            }
            //TODO : add the show current buildings hp function
            //TODO : consider the situation of not being able to repair because of the enemies being close too that building we want to repair
            else if (SelectedBuildingCommands.getMatcher(input, SelectedBuildingCommands.SELECTED_BUILDING_COMMANDS_REPAIR) != null) {
                System.out.println(BuildingController.repairBuilding(selectedBuilding).getMessages());
            } else if (buildingName.equals("shop")) {
                ShopMenu shopMenu = new ShopMenu();
                shopMenu.run(scanner , (Shop) selectedBuilding);
            } else if (input.equals("exit")) {
                return;
            } else {
                System.out.println(SelectedBuildingMessages.INVALID_COMMAND.getName());
            }
        }
    }
}