package org.helperCook;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.helperCook.ColorConstants.ANSI_RED;
import static org.helperCook.ColorConstants.ANSI_RESET;

/**
 * This class checks the command line arguments and returns the list of recipe files that will be used
 */

public class CheckArguments {


    private String mode; // mode of the program
    private final List<File> fileList = new ArrayList<>(); // list of recipe files that will be returned
    private int servings = 1; // default servings is 1

    public List<File> check(String[] args) throws InvalidCommandLineArgumentException {
        switch (args.length) {
            case 0 -> mode = "windows";

            case 1 -> {
                makeFile(args[0]);
                mode = "windows";
            }
            default -> {
                if (args[args.length - 1].equalsIgnoreCase("-t")) { // check if -t exists and is the last argument
                    switch (args.length) {
                        case 2 -> {
                            makeFile(args[0]);
                            mode = "recipe";
                        }
                        case 3 -> {
                            if (args[0].equals("-list")) { // if -list as first argument, then check if second argument is a recipe
                                makeFile(args[1]);
                                mode = "list";
                                break;
                            } // if not -list, then check if it is a single recipe and has # servings
                            try {
                                servings = Integer.parseInt(args[1]);
                                if (servings < 1) // if servings is not a positive integer, then invalid
                                    throw new Exception();
                            } catch (Exception e) {
                                throw new InvalidCommandLineArgumentException(
                                        ANSI_RED + "Invalid number of servings: Servings must be an integer." + ANSI_RESET);
                            }
                            // if second argument is a positive integer then check if first argument is a recipe
                            makeFile(args[0]);
                            mode = "recipe";
                        }
                        default -> {
                            if (!args[0].equals("-list")) {
                                throw new InvalidCommandLineArgumentException(ANSI_RED + "Invalid command line arguments" + ANSI_RESET);
                            }
                            makeFile(args[0]);
                            mode = "list";
                        }
                    }
                } else {
                    makeFiles(args);
                    mode = "windows";
                }

            }

        }

        return fileList;
    }

    public String getMode() {
        return mode;
    }

    public int getServings() {
        return servings;
    }

    private void makeFile(String arg) throws InvalidCommandLineArgumentException { // Check if the argument is a valid file and add it to the list
        Argument argument = new Argument(arg);
        File file = argument.isValidCookFile();
        fileList.add(file); // add file at the list
    }

    private void makeFiles(String[] args) throws InvalidCommandLineArgumentException { // Check if the arguments are valid files and add them to the list
        for (String arg : args) { //ignore "-list" and "-t" arguments
            if (arg.equalsIgnoreCase("-list") || arg.equalsIgnoreCase("-t")) {
                continue;
            }
            makeFile(arg);
        }
    }

}