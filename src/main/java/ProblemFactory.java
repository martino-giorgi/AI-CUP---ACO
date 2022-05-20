import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ProblemFactory {

    public static Problem create(String problemSrc) {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("src/main/resources/problems/" + problemSrc));

            // Read the first line of the file and set the problem name after ":"
            final String name = bufferedReader.readLine().split(":")[1].trim();

            // Skip the second line
            bufferedReader.readLine();

            // Read the third line and set the problem comment after ":"
            final String comment = bufferedReader.readLine().split(":")[1].trim();

            // Read the fourth line and set the problem dimension after ":"
            final int dimension = Integer.parseInt(bufferedReader.readLine().split(":")[1].trim());

            // Skip the fifth line
            bufferedReader.readLine();

            // Read the sixth line and set the problem bestKnown after ":"
            final int bestKnown = Integer.parseInt(bufferedReader.readLine().split(":")[1].trim());
            ArrayList<City> cities = new ArrayList<City>();

            // Skip the seventh line
            bufferedReader.readLine();

            // Read each line of the file until "EOF" is found
            String line;
            while (!(line = bufferedReader.readLine()).contains("EOF")) {
                // Read the line and split it by " "
                String[] parts  = line.split(" ");

                // Create a new city with the coordinates and index and add it to the list
                cities.add(new City(Integer.parseInt(parts[0])-1,Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
            }

            assert cities.size() == dimension;

            // Close the file
            bufferedReader.close();

            return new Problem(name, comment, dimension, bestKnown, cities);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
