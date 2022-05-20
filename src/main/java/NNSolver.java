public class NNSolver {

    public static Tour solve(Problem problem) {
        Tour tour = new Tour();

        // Add the first city of the Problem to the tour
        tour.addCity(problem.getCities().get(0));

        // For each city in the problem find the nearest neighbor of the last city in the tour
        // and add it to the tour.

        for (int i = 0; i < problem.getCities().size() - 1; i++) {
            int nearestNeighborIndex = tour.getPath().getLast().getNearestCityIndex(tour);
            tour.addCity(problem.getCities().get(nearestNeighborIndex));
        }

        tour.computeLength();

        return tour;
    }
}
