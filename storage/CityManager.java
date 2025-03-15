package storage;

import city.City;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для управления коллекцией городов.
 */
public class CityManager {
    private final Hashtable<Integer, City> collection = new Hashtable<>();

    /**
     * Добавляет город в коллекцию.
     *
     * @param city город для добавления.
     * @throws IllegalArgumentException если город уже существует в коллекции.
     */
    public void addCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("Город не может быть null.");
        }
        if (collection.containsValue(city)) {
            throw new IllegalArgumentException("Город уже существует в коллекции.");
        }
        collection.put(city.getId(), city);
    }

    /**
     * Обновляет город по ID.
     *
     * @param id      ID города для обновления.
     * @param newCity новый объект города.
     * @throws IllegalArgumentException если город с указанным ID не найден.
     */
    public void updateCity(int id, City newCity) {
        if (!collection.containsKey(id)) {
            throw new IllegalArgumentException("Город с ID " + id + " не найден.");
        }
        newCity.setId(id); // Сохраняем старый ID
        collection.put(id, newCity);
    }

    /**
     * Удаляет город по ID.
     *
     * @param id ID города для удаления.
     * @throws IllegalArgumentException если город с указанным ID не найден.
     */
    public void removeCity(int id) {
        if (!collection.containsKey(id)) {
            throw new IllegalArgumentException("Город с ID " + id + " не найден.");
        }
        collection.remove(id);
    }

    /**
     * Возвращает город по ID.
     *
     * @param id ID города.
     * @return объект города или null, если город не найден.
     */
    public City getCity(int id) {
        return collection.get(id);
    }

    /**
     * Возвращает все города в коллекции.
     *
     * @return список всех городов.
     */
    public List<City> getAllCities() {
        return new ArrayList<>(collection.values());
    }

    /**
     * Очищает коллекцию.
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Возвращает размер коллекции.
     *
     * @return количество городов в коллекции.
     */
    public int getCollectionSize() {
        return collection.size();
    }

    /**
     * Сортирует города по имени (по умолчанию).
     *
     * @return отсортированный список городов.
     */
    public List<City> sortByName() {
        return collection.values()
                .stream()
                .sorted(Comparator.comparing(City::getName))
                .collect(Collectors.toList());
    }

    /**
     * Фильтрует города по началу имени.
     *
     * @param prefix префикс для фильтрации.
     * @return список городов, имя которых начинается с указанного префикса.
     */
    public List<City> filterByName(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("Префикс не может быть пустым.");
        }
        return collection.values()
                .stream()
                .filter(city -> city.getName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает коллекцию городов.
     *
     * @return коллекция городов.
     */
    public Hashtable<Integer, City> getCollection() {
        return collection;
    }
}
