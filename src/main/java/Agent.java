import java.util.ArrayList;
public class Agent {

    private Tour tour;
    private final ArrayList<City> unvisitedCities;
    private final double[][] pheromones;
    private City currentCity;

    public Agent(ACOSolver solver) {
        this.tour = new Tour();
        this.unvisitedCities = new ArrayList<>(solver.getCities());

        this.pheromones = solver.getPheromones();

        currentCity = initTour();
    }

    // Move the Agent to a random City and return it
    private City initTour() {
        // Take a random city from the list of unvisited cities
        City city = unvisitedCities.get(
                App.RANDOM.nextInt(unvisitedCities.size()));

        walkTo(city);
        return city;
    }

    // Move the Agent to a given City
    public void walkTo(City city) {

        // Add the city to the tour
        tour.addCity(city);

        // Update the pheromone trail if not the first city
        if (currentCity != null) {
            updatePheromones();
        }

        // Set the city as currentCity
        currentCity = city;

        // Remove the city from the list of unvisited cities
        unvisitedCities.remove(city);

        // If the list of unvisited cities is empty, the tour is complete
        if (unvisitedCities.isEmpty()) {

            // Compute Tour length
            tour.computeLength();
        }

    }

    /**
     * Find the best city to move to.
     */
    public City findNextCity() {
        City city;

        // If a random number q is less than Q0 perform exploitation
        if (App.RANDOM.nextDouble() <= ACOSolver.Q0) {
            city = exploitation();
        } else {
            // Perform exploration
            city = exploration();
        }
        return city;
    }

    // Get the probability of choosing a given city
    private double getChoiceProbability(City city) {
        double pheromone = pheromones[currentCity.getId()][city.getId()];
        double heuristic = Math.pow(1 / ((double) currentCity.getDistanceTo(city.getId())),ACOSolver.BETA);

        return pheromone * heuristic;
    }

    private City exploitation() {
        // Choose the city with the highest probability of being visited.
        City city;

        double maxProb = Double.MIN_VALUE;
        city = unvisitedCities.get(0);

        for (City c : unvisitedCities) {
            double prob = getChoiceProbability(c);

            if (prob > maxProb) {
                maxProb = prob;
                city = c;
            }
        }

        return city;
    }

    private City exploration() {
        City city = unvisitedCities.get(0);
        double totalProb = 0;
        double randomProb = App.RANDOM.nextDouble();

        for (City c : unvisitedCities) {
           totalProb += getChoiceProbability(c);
        }

        double prob = 0;

        for (int i = 0 ; i < unvisitedCities.size() ; i++) {
            prob += getChoiceProbability(unvisitedCities.get(i)) / totalProb;
            if (randomProb >=  prob) {
                city = unvisitedCities.get(i+1);
            }
        }
        return city;
    }

    public Tour getTour() {
        return tour;
    }

    private void updatePheromones() {
        // Compute new Pheromone
        double newPheromone = (1 - ACOSolver.LOCAL_RHO) *
                pheromones[currentCity.getId()][tour.getPath().getLast().getId()]
                + ACOSolver.LOCAL_RHO * ACOSolver.INITIAL_PHEROMONE;

        // Update Pheromone in the matrix for both directions
        pheromones[currentCity.getId()][tour.getPath().getLast().getId()] = newPheromone;
        pheromones[tour.getPath().getLast().getId()][currentCity.getId()] = newPheromone;
    }
}
