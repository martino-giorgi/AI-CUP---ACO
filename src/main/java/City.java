import java.util.HashMap;

public class City {
    private final int id;
    private final double x;
    private final double y;
    private final HashMap<Integer,Integer> distances; // TODO: Consider using 2D array

    public City(int index, double x, double y) {
        this.id = index;
        this.x = x;
        this.y = y;
        this.distances = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    /**
     * Update the distance HashMap with the destination city index as key and the distance to this city
     * as value.
     * @param city the city to calculate the distance to
     */
    public void updateDistanceTo(City city) {
        // Calculate euclidean distance from this city to the given city
        double distance = Math.sqrt(Math.pow(city.x - this.x, 2) + Math.pow(city.y - this.y, 2));

        // Update the distance HashMap
        distances.put(city.id, (int) Math.round(distance));
    }

    public int getDistanceTo(int cityId) {
        return distances.get(cityId);
    }

    /**
     * Get the nearest city index to this city based on the distance HashMap.
     * The city must not be already in the Tour
     */
    public int getNearestCityIndex(Tour tour) {
        // Get the nearest city index
        int nearestCityIndex = -1;
        int nearestCityDistance = Integer.MAX_VALUE;

        for (int cityIndex : distances.keySet()) {
            if (!tour.containsCity(cityIndex) && getDistanceTo(cityIndex) < nearestCityDistance) {
                nearestCityIndex = cityIndex;
                nearestCityDistance = getDistanceTo(cityIndex);
            }
        }
        return nearestCityIndex;
    }
}
