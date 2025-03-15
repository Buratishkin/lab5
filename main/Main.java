package main;
import commands.*;
import commands.RemoveGreaterCommand;
import commands.RemoveKeyCommand;
import exceptions.FileReadException;
import io.XmlReader;
import storage.CityManager;
import util.EnvReader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileReadException {
        //RemoveKeyCommand removeKeyCommand = new RemoveKeyCommand(new CityManager(),new Scanner(System.in));
        //removeKeyCommand.execute();
        //String file=EnvReader.getFilePath();
        CityManager cityManager = new CityManager();
        XmlReader xmlReader = new XmlReader(cityManager,"collection.xml");
        CommandHandler commandHandler= new CommandHandler(new CityManager(),new Scanner(System.in));
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            commandHandler.handleCommand(line);
        }
    }
}