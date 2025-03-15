package commands;

import storage.CityManager;
import city.City;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда для группировки элементов по id и вывода количества элементов в каждой группе.
 */
public class GroupCountingByIdCommand implements Command {
    private final CityManager cityManager;

    public GroupCountingByIdCommand(CityManager cityManager) {
        this.cityManager = cityManager;
    }

    @Override
    public void execute(String[] args) {
        Map<Integer, Long> groupCounts = cityManager.getCollection().values().stream()
                .collect(Collectors.groupingBy(City::getId, Collectors.counting()));

        groupCounts.forEach((id, count) ->
                System.out.println("ID: " + id + ", Количество: " + count));
    }
}