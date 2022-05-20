import java.text.DecimalFormat;
import java.util.Random;

public class App {

    private static final long seed = Long.parseLong("77327463178894");
    public static Random RANDOM = new Random(seed);

    private static void printProblemInfo(Problem problem) {
        System.out.println();
        System.out.println("PROBLEM NAME: " + problem.getName());
        System.out.println("DIMENSION: " + problem.getDimension());
        System.out.println("BEST KNOWN: " + problem.getBestKnown());
        System.out.println();
    }

    /**
     * Check whether the size of the tour is the same as the problem's dimension;
     * whether there are no duplicated cities;
     */
    private static boolean isValid(Tour tour, Problem problem) {

        final boolean isSameSize = tour.getPath().size() == problem.getDimension();
        final boolean isNoDuplicated = tour.getPath().size() == tour.getPath().stream().distinct().count();
        final boolean containsAllCities = tour.getPath().containsAll(problem.getCities());

        if (!isSameSize) {
            System.out.println("Not the same size");
            System.out.println("Expected: " + problem.getDimension());
            System.out.println("Actual: " + tour.getPath().size());
        }

        if (!isNoDuplicated) {
            System.out.println("Duplicated cities");
        }

        if (!containsAllCities) {
            System.out.println("Not all cities are in the tour");
        }

        return isSameSize &&
                isNoDuplicated &&
                containsAllCities;
    }

    public static void main(String[] args) {
            System.out.println("Current Seed: " + seed);
            long startTime = System.currentTimeMillis();
            System.out.println("[Ant Colony Optimization]");
            Problem problem = ProblemFactory.create(args[0]);
            assert problem != null;
            printProblemInfo(problem);

            Tour nnTour = NNSolver.solve(problem);
            if (!isValid(nnTour, problem)) {
                System.out.println("NN tour is invalid!");
                System.exit(1);
            }

            ACOSolver acoSolver = new ACOSolver(problem, nnTour, startTime);
            Tour acoTour = acoSolver.solve();

            if (acoTour == null) {
                acoTour = nnTour;
            }

            if (!isValid(acoTour, problem)) {
                System.out.println("ACO tour is invalid!");
                System.exit(1);
            }

            System.out.println("Tour Length " + acoTour.getLength());
            DecimalFormat f = new DecimalFormat("##.00");
            System.out.println("Relative error: " + f.format((acoTour.getLength() - problem.getBestKnown())
                    / (double) problem.getBestKnown() * 100) + "%");
            System.out.println("Time: " + (System.currentTimeMillis() - startTime) / 1000.0 + "s");

//                Print the tour
                System.out.println("Size: " + acoTour.getPath().size());
                for (City city : acoTour.getPath()) {
                    System.out.print(city.getId() + ",");
                }
                System.out.println();
    }
}
