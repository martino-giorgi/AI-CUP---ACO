import java.util.ArrayList;

public class Problem {
    private final String name;
    private final String comment;
    private final int dimension;
    private final int bestKnown;
    private final ArrayList<City> cities;

    public Problem(String name, String comment, int dimension, int bestKnown, ArrayList<City> cities) {
        this.name = name;
        this.comment = comment;
        this.dimension = dimension;
        this.bestKnown = bestKnown;
        this.cities = cities;

        updateDistance();
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getDimension() {
        return dimension;
    }

    public int getBestKnown() {
        return bestKnown;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    /**
     * Update every city's c1 distance to the next city c2 and vice-versa.
     * Create distance Matrix.
     */
    private void updateDistance() {
        for (final City c1 : cities) {
            for (final City c2 : cities) {
                if (c1 != c2) {
                    c1.updateDistanceTo(c2);
                    c2.updateDistanceTo(c1);
                }
            }
        }
    }

}
