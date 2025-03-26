package io;

import exceptions.ValidateException;
import java.util.Scanner;
import java.util.function.Function;

public class DataReader {
    private final Scanner scanner;

    public DataReader(Scanner scanner){
        this.scanner = scanner;
    }

    public <T> T readAnything(String message, String errMessage, Function<String, T> validator, boolean isConsoleRead) {
        while (true) {
            //String input;
            if (isConsoleRead) {
                System.out.println(message); //bufferedReader юзнуть

            }
            String input = scanner.nextLine().trim(); //else
            try {
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
