package ua.telega;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Task1
//        calculatingParentheses();

        //Task2
//        costSearch();

        //Task3
//        factorialDigitSum();
    }

//Task1___________________________
    private static void calculatingParentheses(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число N (количество пар скобок): ");
        int n = scanner.nextInt();

        if (n < 0) {
            System.out.println("N должно быть неотрицательным числом.");
            return;
        }

        long result = calculateCatalan(n);
        System.out.println("Число правильных скобочных выражений для N = " + n + ": " + result);
    }

    // Method for calculating the Catalan number for a given N
    private static long calculateCatalan(int n) {
        long[] catalan = new long[n + 1];
        catalan[0] = 1; // Base case: for N=0 only one expression (empty)

        for (int i = 1; i <= n; i++) {
            catalan[i] = 0;
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - 1 - j];
            }
        }

        return catalan[n];
    }
//________________________________


//Task3___________________________
    private static void factorialDigitSum(){
        // Let's calculate
        int count = 100;

        BigInteger factorial = calculateFactorial(count);

        // Let's sum up the numbers of the result
        int digitSum = calculateDigitSum(factorial);

        // We display the result
        System.out.println("The sum of the digits in the number " + count +"! is: " + digitSum);
    }

    private static BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i)); // Multiply the current result by i
        }

        return result;
    }

    private static int calculateDigitSum(BigInteger number) {
        String numberString = number.toString(); // Converting a number to a string
        int sum = 0;

        for (char digit : numberString.toCharArray()) {
            sum += Character.getNumericValue(digit); // Add the value of each digit to the sum
        }

        return sum;
    }
//________________________________


//Task2___________________________
    private static void costSearch(){
        Scanner scanner = new Scanner(System.in);

        // Reading the number of tests
        int testCases = Integer.parseInt(scanner.nextLine().trim());

        while (testCases-- > 0) {
            // Reading the number of cities
            int numberOfCities = Integer.parseInt(scanner.nextLine().trim());

            // Dictionary for storing city indexes
            Map<String, Integer> cityIndexMap = new HashMap<>();

            // Adjacency list for a graph
            List<List<Edge>> graph = new ArrayList<>();

            // Reading data for each city
            for (int i = 0; i < numberOfCities; i++) {
                // Reading the city name
                String cityName = scanner.nextLine().trim();

                // Assigning an index to a city
                cityIndexMap.put(cityName, i);

                // Creating a list of neighbors for the current city
                graph.add(new ArrayList<>());

                // Reading the number of neighbors
                int numberOfNeighbors = Integer.parseInt(scanner.nextLine().trim());

                // Reading information about neighbors
                for (int j = 0; j < numberOfNeighbors; j++) {
                    String[] connection = scanner.nextLine().trim().split(" ");
                    int neighborIndex = Integer.parseInt(connection[0]) - 1; // Neighbor index
                    int cost = Integer.parseInt(connection[1]); // Transition cost

                    // Adding an Edge to the Adjacency List
                    graph.get(i).add(new Edge(neighborIndex, cost));
                }
            }

            // Reading the number of requests
            int queries = Integer.parseInt(scanner.nextLine().trim());

            for (int i = 0; i < queries; i++) {
                // Reading query: source and destination cities
                String[] query = scanner.nextLine().trim().split(" ");
                String sourceCity = query[0];
                String destinationCity = query[1];

                // Getting the source and destination city indexes
                int sourceIndex = cityIndexMap.get(sourceCity);
                int destinationIndex = cityIndexMap.get(destinationCity);

                // Finding the minimum cost of a route between cities
                int minCost = findShortestPath(graph, sourceIndex, destinationIndex, numberOfCities);

                // Output of the result
                System.out.println(minCost);
            }

            // Skip a blank line between tests (if there are more tests)
            if (testCases > 0) {
                scanner.nextLine();
            }
        }
    }

    // Dijkstra's algorithm for finding the minimum path
    private static int findShortestPath(List<List<Edge>> graph, int source, int destination, int numberOfCities) {
        // An array for storing minimum distances to each city
        int[] distances = new int[numberOfCities];
        Arrays.fill(distances, Integer.MAX_VALUE); // Initialization to infinity
        distances[source] = 0; // The distance to yourself is 0

        // Priority queue to store current minimum paths
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        priorityQueue.add(new Edge(source, 0)); // Добавление исходной точки

        // Main vertex processing loop
        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            int currentNode = current.target;
            int currentCost = current.cost;

            // If the current cost already exceeds the stored minimum distance, skip
            if (currentCost > distances[currentNode]) continue;

            // Traversing all neighbors of the current vertex
            for (Edge edge : graph.get(currentNode)) {
                int neighbor = edge.target;
                int newCost = currentCost + edge.cost; // Cost of the path through the current vertex

                // If we find a shorter path, update the distance and add it to the queue
                if (newCost < distances[neighbor]) {
                    distances[neighbor] = newCost;
                    priorityQueue.add(new Edge(neighbor, newCost));
                }
            }
        }
        // We return the minimum cost of travel to the final city
        return distances[destination];
    }

}

// Class for representing graph edges
class Edge {
    int target; // Target city (zip code)
    int cost;   // Transition cost

    Edge(int target, int cost) {
        this.target = target;
        this.cost = cost;
    }
}
//_________________________________
