import java.util.*;

public class TwoOptSolver {

//    private static int calculateGain(Tour tour, int i, int j) {
//        int oldLength = tour.getPath().get(i).getDistanceTo(tour.getPath().get(i - 1).getId()) +
//                tour.getPath().get(j).getDistanceTo(tour.getPath().get(j + 1).getId());
//
//        int newLength = tour.getPath().get(j).getDistanceTo(tour.getPath().get(i-1).getId()) +
//                tour.getPath().get(i).getDistanceTo(tour.getPath().get(j+1).getId());
//
////        System.out.println(newLength-oldLength);
//        return newLength - oldLength;
//    }
//
//    private static int loop(final Tour tour) {
//        int n = tour.getPath().size() - 1;
//        int bestI = -1;
//        int bestJ = -1;
//        int bestLength = tour.getLength();
//
//        for (int i = 1; i < n - 1; i++) {
//            for (int j = i + 1; j < n; j++) {
//                int newLength = tour.getLength() + calculateGain(tour, i, j);
//
//                if (newLength < bestLength) {
//                    bestLength = newLength;
//                    bestI = i;
//                    bestJ = j;
//                }
//            }
//        }
//
//        if (bestI == -1 || bestJ == -1) {
//            return 0;
//        }
//
//        // Reverse LinkedList tour between bestI and bestJ
////        List<City> cities = tour.getPath();
////        List<City> subList = cities.subList(bestI, bestJ + 1);
////        Collections.reverse(subList);
//
//        List<City> l = tour.getPath().subList(Math.min(bestI, bestJ), Math.max(bestI, bestJ) + 1);
//        Collections.reverse(l);
//
//        tour.setLength(bestLength);
//        return 1;
//    }

    // old implementation: slower
//    public static void solve2(final Tour tour) {
//        int cross;
//        do {
//            cross = loop(tour);
//        } while (cross > 0);
//    }

    public static void solve (final Tour tour) {

        final int distance = tour.getLength();
        final LinkedList<City> path = tour.getPath();
        int bestGain = -1;
        int finalGain = 0;

        while (bestGain != 0) {
            bestGain = 0;
            City bestI = path.getFirst();
            City bestJ = null;
            City last = path.getLast();

            ListIterator<City> firstEdge = path.listIterator(path.indexOf(bestI)); // start iterator at bestI
            firstEdge.add(path.getLast()); // add last city before bestI

            while (firstEdge.hasNext()) {

                final City i = firstEdge.previous();
                firstEdge.next();
                final City j = firstEdge.next();

                if (i.equals(j) || last.equals(j)) { break; }
                ListIterator<City> secondEdge = path.listIterator(path.indexOf(j) + 2);

                while (secondEdge.hasNext()) {
                    final City k = secondEdge.previous();
                    secondEdge.next();
                    final City l = secondEdge.next();
                    int gain = (l.getDistanceTo(j.getId()) +
                            k.getDistanceTo(i.getId())) -
                            (i.getDistanceTo(j.getId()) +
                            k.getDistanceTo(l.getId()));
                    if (gain < bestGain) {
                        bestGain = gain;
                        bestJ = j;
                        bestI = k;
                    }
                }
            }

            path.remove(path.get(0));
            finalGain += bestGain;

            if (bestJ != null && bestI != null) {
                int posJ = path.indexOf(bestJ);
                int posK = path.indexOf(bestI);
                List<City> l = path.subList(Math.min(posK, posJ), Math.max(posK, posJ) + 1);
                Collections.reverse(l);
            }
        }
        tour.setLength(distance + finalGain);
    }
}
