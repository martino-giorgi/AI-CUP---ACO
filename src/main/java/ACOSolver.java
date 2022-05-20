import java.util.ArrayList;
import java.util.Comparator;

public class ACOSolver {
    public static double Q0;
    public static final double ALPHA = 0.1d;
    public static final double BETA = 2.0d;
    public static final double LOCAL_RHO = 0.3d;
    public static int AGENTS_NUMBER;
    private static int ACO_MAX;

    public static double INITIAL_PHEROMONE;

    private final Problem problem;

    private final double[][] pheromones;
    private final ArrayList<Agent> agents;
    private Agent bestAgentEver;
    private final long startTime;

    public ACOSolver(Problem problem, Tour nnTour, long startTime) {
        this.problem = problem;
        this.startTime = startTime;

        Q0 = 1.0 - 20.0 / (double)(problem.getDimension());

        AGENTS_NUMBER = problem.getDimension() < 200 ? 20 : 12;
        ACO_MAX = problem.getDimension() < 500 ? 3 : 1;

        INITIAL_PHEROMONE = 1.0d / (double) (nnTour.getLength() * problem.getDimension());
        this.pheromones = new double[problem.getDimension()][problem.getDimension()];
        this.agents = new ArrayList<>(AGENTS_NUMBER);

        initPheromones();
    }

    /**
     * Initialize pheromones matrix with initial value
     */
    private void initPheromones() {
        for (int i = 0; i < problem.getDimension(); i++) {
            for (int j = 0; j < problem.getDimension(); j++) {
                pheromones[i][j] = INITIAL_PHEROMONE;
            }
        }
    }



    public Tour solve() {
        int MAX_DURATION = 178;
        while ((System.currentTimeMillis() - startTime) * Math.pow(10, -3) < MAX_DURATION) {

            // If best solution is found, stop
            if (bestAgentEver != null
                    && bestAgentEver.getTour().getLength() <= problem.getBestKnown()) {
                break;
            }

            // Initialize agents
            for (int i = 0; i < AGENTS_NUMBER; i++) {
                agents.add(new Agent(this));
            }

            // Move each agent until it reaches the end
            for (int i = 0; i < problem.getDimension() - 1; i++) {
                for (Agent agent : agents) {
                    agent.walkTo(agent.findNextCity());
                }
            }

            // Sort agents by their tour length
            agents.sort(Comparator.comparingInt(a -> a.getTour().getLength()));

            // TwoOpt
            for (int i = 0; i < ACO_MAX; i++) {
                TwoOptSolver.solve(agents.get(i).getTour());
            }

            agents.sort(Comparator.comparingInt(a -> a.getTour().getLength()));
            Agent bestAgent = agents.get(0);

            // Update the best Agent so far
            if (bestAgentEver == null
                    || bestAgent.getTour().getLength() <= bestAgentEver.getTour().getLength()) {
                bestAgentEver = bestAgent;
            }

            // Update pheromones
            if (bestAgentEver == null) {
                updatePheromones(bestAgent);
            } else {
                updatePheromones(bestAgentEver);
            }

            // Reset agents
            agents.clear();
        }

        if (bestAgentEver == null) {
            return null;
        }
        return bestAgentEver.getTour();
    }

    public ArrayList<City> getCities() {
        return problem.getCities();
    }

    public double[][] getPheromones() {
        return pheromones;
    }

    // For each city update the level of pheromones based on the best Agent's tour
    private void updatePheromones(Agent agent) {
        final double constant = 1 / (double)(agent.getTour().getLength());
        City previous = agent.getTour().getPath().getLast();

        for (City current : agent.getTour().getPath()) {
            final double currentTau = pheromones[previous.getId()][current.getId()];
            double newTau = (1 - ALPHA) * currentTau + ALPHA * (constant);

            pheromones[previous.getId()][current.getId()] = newTau;
            pheromones[current.getId()][previous.getId()] = newTau;
            previous = current;
        }
    }
}
