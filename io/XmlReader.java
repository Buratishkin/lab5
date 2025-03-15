package io;

import city.*;
import enums.*;
import exceptions.FileReadException;
import exceptions.ValidationException;
import service.ValidationService;

import org.w3c.dom.*;
import storage.CityManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Класс для чтения данных из XML-файла и их преобразования в объекты {@link City}.
 */
public class XmlReader {
    private final CityManager cityManager;

    /**
     * Читает коллекцию городов из XML-файла.
     *
     * @param fileName имя файла.
     * @return список городов.
     * @throws FileReadException если произошла ошибка при чтении файла или данных.
     */
    public XmlReader(CityManager cityManager, String fileName) throws FileReadException {
        this.cityManager=cityManager;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(fileName));
            document.getDocumentElement().normalize();

            NodeList cityNodes = document.getElementsByTagName("city");
            for (int i = 0; i < cityNodes.getLength(); i++) {
                Node node = cityNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        Element element = (Element) node;
                        City city = parseCity(element);
                        cityManager.addCity(city);
                    } catch (ValidationException e) {
                        throw new FileReadException("Ошибка валидации города: " + e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            throw new FileReadException("Ошибка при чтении XML-файла: " + fileName, e);
        }
    }



    /**
     * Преобразует XML-элемент в объект {@link City}.
     *
     * @param element XML-элемент города.
     * @return объект {@link City}.
     * @throws ValidationException если данные не проходят валидацию.
     */
    private static City parseCity(Element element) throws ValidationException {
        String name = ValidationService.validateName(getTextValue(element, "name"));
        Coordinates coordinates = parseCoordinates((Element) element.getElementsByTagName("coordinates").item(0));
        Long area = ValidationService.validateArea(getLongValue(element, "area"));
        Integer population = ValidationService.validatePopulation(getIntValue(element, "population"));
        Float metersAboveSeaLevel = getFloatValue(element, "metersAboveSeaLevel");
        Climate climate = Climate.valueOf(getTextValue(element, "climate"));
        Government government = Government.valueOf(getTextValue(element, "government"));
        StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(getTextValue(element, "standardOfLiving"));
        Human governor = parseHuman((Element) element.getElementsByTagName("governor").item(0));

        return new City(name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    private static Coordinates parseCoordinates(Element element) throws ValidationException {
        if (element == null) throw new ValidationException("Координаты отсутствуют");
        double x = ValidationService.validateX(getDoubleValue(element, "x"));
        int y = getIntValue(element, "y");
        return new Coordinates(x, y);
    }

    private static Human parseHuman(Element element) throws ValidationException {
        if (element == null) return null;
        Float height = ValidationService.validateHeight(getFloatValue(element, "height"));
        return new Human(height);
    }

    private static String getTextValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) return "";
        return nodeList.item(0).getTextContent().trim();
    }

    private static int getIntValue(Element element, String tagName) {
        return Integer.parseInt(getTextValue(element, tagName));
    }

    private static long getLongValue(Element element, String tagName) {
        return Long.parseLong(getTextValue(element, tagName));
    }

    private static float getFloatValue(Element element, String tagName) {
        return Float.parseFloat(getTextValue(element, tagName));
    }

    private static double getDoubleValue(Element element, String tagName) {
        return Double.parseDouble(getTextValue(element, tagName));
    }
}
