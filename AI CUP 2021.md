# AI CUP 2021



To compile the app go into main folder and type: `./gradlew build` 

To run the app go into main folder and type: `java  -jar build/libs/ACO-1.0-SNAPSHOT.jar [problem.tsp]`



## Algorithm

I have implemented the Ant Colony Optimization algorithm to solve the TSP. I have followed the pseudo-code provided in the paper of Prof. Gambardella, some other code that I have found on the internet and the lectures of the course.

I have implemented the algorithm using Java since I am more familiar with that.

After implementing and making sure the algorithm was working correctly I have fine tuned the parameters by doing numerous tests with different numbers and seeds and trying some improvements such as the dynamic number of Agents and others. After many tentatives I have reached an average error of 0.78%.