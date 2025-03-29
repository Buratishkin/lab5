package io;

import commands.ExecuteScriptCommand;
import exceptions.ValidateException;
import main.Main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;

public class DataReader {
    private static Scanner currentScanner;
    private final Scanner mainScanner;
    private final Queue<String> scriptBuffer = new LinkedList<>();

    private boolean scriptMode;



    public DataReader(Scanner mainScanner){
        this.mainScanner = mainScanner;
        currentScanner = mainScanner;
        this.scriptMode = false;
    }

    public void prepareScriptMode() {
        this.scriptMode = true;
        readScriptLines();
    }

    private void readScriptLines() {
        while (currentScanner.hasNextLine()) {
            String line = currentScanner.nextLine();
            if (line.startsWith("    ")) {
                scriptBuffer.add(line.substring(4).trim());
            } else {
                ExecuteScriptCommand.setLostLine(line);
                break;
            }
        }
    }

    public Scanner getMainScanner() {
        return mainScanner;
    }

    public boolean isScriptMode() {
        return scriptMode;
    }

    public void setScriptMode(boolean scriptMod) {
        scriptMode = scriptMod;
    }

    public boolean getScriptMode() {return  scriptMode;}

    public static void setCurrentScanner(Scanner currentScan){
        currentScanner = currentScan;
    }

    public Scanner getCurrentScanner() {
        return currentScanner;
    }

    public <T> T readAnything(String message, String errMessage, Function<String, T> validator) {
        prepareScriptMode();
        while (true) {
            try {
                String input;
                if (scriptMode && !scriptBuffer.isEmpty()) {
                    input = scriptBuffer.poll();
                    System.out.println(message + input);
                } else {
                    if (scriptMode) {
                        scriptMode = false;
                        currentScanner = Main.getScanner();
                    }
                    System.out.println("Введите " + message);
                    input = currentScanner.nextLine().trim();
                }
                return validator.apply(input);
            } catch (ValidateException e) {
                System.out.println(errMessage);
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Возникла ошибка: " + e.getMessage());
            }
        }
    }
}
