import java.util.LinkedList;

public class Tour {
    private final LinkedList<City> path;
    private int length;

    public Tour() {
        this.path = new LinkedList<>();
    }

    public void addCity(City city) {
        path.add(city);
    }

    public LinkedList<City> getPath() {
        return path;
    }

    /**
     * Check if path contains a city with the given index
     */
    public boolean containsCity(int cityId) {
        for (City city : path) {
            if (city.getId() == cityId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the total length of the tour (sum of all edges)
     */
    public void computeLength() {
        for (int i = 0; i < path.size() - 1; i++) {
            length += path.get(i).getDistanceTo(path.get(i + 1).getId());
        }
        // Add distance from last city to first city
        length += path.getLast().getDistanceTo(path.getFirst().getId());
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
