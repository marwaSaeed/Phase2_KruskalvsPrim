package kruskalvsprim;

import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.*;
import java.lang.*;
import java.io.*;

public class KruskalVsPrim {

    public static void main(String[] args) {
        //call make graph 
        Graph graph = Make_graph(10);
        //decleration Time variable
        long startTime;
        long endTime;
        long estimatedTime;
        //start time
      
        startTime = System.currentTimeMillis();
        graph.KruskalMST();// Kruskal Function call
        //end time for kruskal 
        endTime = System.currentTimeMillis();
        estimatedTime = endTime - startTime;
        System.out.println("-Estimated Time for Kroskal:" + estimatedTime + " millisecond\n");
          System.out.println("");
//Zoning the variable
       startTime =0;
        startTime = System.currentTimeMillis();
        //call prim
        graph.primMST();//prim function call
        endTime = System.currentTimeMillis();
        //calculate Excution time
        estimatedTime = endTime - startTime;
        System.out.println("\n-Estimated Time for Prim :" + estimatedTime + " millisecond\n");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Make_graph--> Generate Random graph for differant cases
    public static Graph Make_graph(int cases) {
        int v = 0, e = 0;//decliration and Initialize two varible v(vertices) and e(edges)

        switch (cases) {
            case 1:     //Case1: generate v=1000 , e= 10000
                v = 1000;
                e = 10000;
                break;
            case 2:    //Case2: generate v=1000 , e= 15000
                v = 1000;
                e = 15000;

                break;
            case 3:    //Case3: generate v=1000 , e= 25000
                v = 1000;
                e = 25000;
                break;
            case 4:    //Case4: generate v= 5000 , e= 15000
                v = 5000;
                e = 15000;
                break;
            case 5:    //Case5: generate v=5000, e= 25000
                v = 5000;
                e = 25000;
                break;
            case 6:   //Case2: generate v=10000 , e= 15000
                v = 10000;
                e = 15000;
                break;
            case 7:  //Case7: generate v=10000 , e= 25000
                v = 10000;
                e = 25000;
                break;
            case 8:  //Case8: generate v=20000 , e=200000
                v = 20000;
                e = 200000;
                break;
            case 9:  //Case9: generate v=20000 , e= 300000
                v = 20000;
                e = 300000;
                break;
            case 10: //Case10: generate v=50000 , e= 1000000
                v = 50000;
                e = 1000000;
                break;
        }
        // Create a Generate_Random_Graph object
        return new Graph(v, e);

    }
    //**************************************************************************
    //class of edge 
    static class Edge implements Comparable<Edge> {

        //decliration source, destination, weight
        int source;
        int destination;
        int weight;

        public Edge() {
        }

        // Creating the constructor
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public boolean equals(Object o) {
            //check if the 
            return (this.source == ((Edge) o).source && this.destination == ((Edge) o).destination);
            //false => not equel 
            //True  => equel
        }
        //compare weight
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    //**************************************************************************
    // class to Generate Random Graph 
    public static class Graph {

        //decliration vertices, edges
        public int vertices;
        public int edges;
        //Edge ed[];
        Random random = new Random();// generate random values

        // An adjacency list to represent a graph
        LinkedList<Edge>[] adjacencyList; //decliration adjacencyList
        //decliration allEdges as Arraylist to sort it in Kruskal algorithm 
        ArrayList<Edge> allEdges;
        // Creating the constructor
        public Graph(int Vertices, int edge) {
            this.vertices = Vertices;//the number of vertices 
            this.edges = edge; //the number of edges 
            
            adjacencyList = new LinkedList[vertices];//Initializing the linked list by the number of vertices 
            allEdges = new ArrayList<>(vertices);//Initializing the Arraylist by the number of vertices 

            // Creating an adjacency list for each vertices
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new LinkedList<>();

            }

            // for loop to generate random edges
            for (int i = 0; i < edges; i++) {
                // Randomly select two vertices (v,u) to create an edge between them
                int v = random.nextInt(vertices);
                int u = random.nextInt(vertices);
                // ed[i] = new Edge();
                //creating an edge between the two vertices v and u and assigning it a random weight 
                addEgde(v, u, (int) (Math.random() * 10) + 1);

            }
      
        }

        //**************************************************************************
        // Method to add edges between source, destination
        public void addEgde(int source, int destination, int weight) {
            Edge edge1 = new Edge(source, destination, weight);
            Edge edge = new Edge(destination, source, weight);
            if (!(source == destination)) {
                if (!(exists(source, destination)) && !(exists(destination, source))) {
                    adjacencyList[source].addFirst(edge1);
                    adjacencyList[destination].addFirst(edge);
                    allEdges.add(edge1);
                    allEdges.add(edge);

                }
            }
        }
        //check if edge exists
        public boolean exists(int v, int u) {
            Edge edge = new Edge(v, u, 0);
            return adjacencyList[v].contains(edge);
        }
        class subset {
            int parent, rank;
        };

        //int V, E; // V-> no. of vertices & E->no.of edges
        //Edge edge[] = new Edge[1000]; // collection of all edges
        // A utility function to find set of an
        // element i (uses path compression technique)
        int find(subset subsets[], int i) {
            // find root and make root as parent of i
            // (path compression)
            if (subsets[i].parent != i) {
                subsets[i].parent
                        = find(subsets, subsets[i].parent);
            }

            return subsets[i].parent;
        }

        // A function that does union of two sets
        // of x and y (uses union by rank)
        void Union(subset subsets[], int x, int y) {
            int xroot = find(subsets, x);
            int yroot = find(subsets, y);

            // Attach smaller rank tree under root
            // of high rank tree (Union by Rank)
            if (subsets[xroot].rank
                    < subsets[yroot].rank) {
                subsets[xroot].parent = yroot;
            } else if (subsets[xroot].rank
                    > subsets[yroot].rank) {
                subsets[yroot].parent = xroot;
            } // If ranks are same, then make one as
            // root and increment its rank by one
            else {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }

        // The main function to construct MST using Kruskal's
        // algorithm
        void KruskalMST() {
            // Tnis will store the resultant MST
            Edge result[] = new Edge[vertices];

            // An index variable, used for result[]
            int e = 0;

            // An index variable, used for sorted edges
            int i = 0;
            for (i = 0; i < vertices; ++i) {
                result[i] = new Edge();
            }

            // Step 1: Sort all the edges in non-decreasing
            // order of their weight. If we are not allowed to
            // change the given graph, we can create a copy of
            // array of edges
            Collections.sort(allEdges);

            // Allocate memory for creating V ssubsets
            subset subsets[] = new subset[vertices];
            for (i = 0; i < vertices; ++i) {
                subsets[i] = new subset();
            }

            // Create V subsets with single elements
            for (int v = 0; v < vertices; ++v) {
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            i = 0; // Index used to pick next edge

            // Number of edges to be taken is equal to V-1
            while (e < vertices - 1 && i < allEdges.size()) {
                // Step 2: Pick the smallest edge. And increment
                // the index for next iteration

                Edge next_edge = new Edge();
                next_edge = allEdges.get(i++);

                int x = find(subsets, next_edge.source);
                int y = find(subsets, next_edge.destination);

                // If including this edge does't cause cycle,
                // include it in result and increment the index
                // of result for next edge
                if (x != y) {
                    result[e++] = next_edge;
                    Union(subsets, x, y);
                }

                // Else discard the next_edge
            }

            // print the contents of result[] to display
            // the built MST
            System.out.println("**********************************Kruskal**********************************");
            int minimumCost = 0;

            for (i = 0; i < e; ++i) {
                minimumCost += result[i].weight;
            }
            System.out.println("\nMinimum Cost Spanning Tree "
                    + minimumCost);

        }

        ////////////////--------------- Haya------------------------/////////////
        public void primMST() {

            boolean[] mst = new boolean[vertices];
            ResultSet[] resultSet = new ResultSet[vertices];
            int[] key = new int[vertices];  //keys used to store the key to know whether priority queue update is required

            //Initialize all the keys to infinity and
            //initialize resultSet for all the vertices
            for (int i = 0; i < vertices; i++) {
                key[i] = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
            }

            //Initialize priority queue
            //override the comparator to do the sorting based keys
            PriorityQueue<Pair<Integer, Integer>> Queue = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sort using key values
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1 - key2;
                }
            });

            //create the pair for for the first index, 0 key 0 index
            key[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(key[0], 0);
            //add it to pq
            Queue.offer(p0);

            resultSet[0] = new ResultSet();
            resultSet[0].parent = -1;

            //while priority queue is not empty
            while (!Queue.isEmpty()) {
                //extract the min
                Pair<Integer, Integer> extractedPair = Queue.poll();

                //extracted vertex
                //delet // int extractedVertex = extractedPair.getValue();
                mst[extractedPair.getValue()] = true;

                //iterate through all the adjacent vertices and update the keys
                LinkedList<Edge> list = adjacencyList[extractedPair.getValue()];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    //only if edge destination is not present in mst
                    if (mst[edge.destination] == false) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update if
                        if (key[destination] > newKey) {
                            //add it to the priority queue
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            Queue.offer(p);
                            //update the resultSet for destination vertex
                            resultSet[destination].parent = extractedPair.getValue();
                            resultSet[destination].weight = newKey;
                            //update the key[]
                            key[destination] = newKey;
                        }
                    }
                }
            }
            //print Minimum Spanning Tree
            Print(resultSet);
        }

        ////////////////////////////////////////////////////
        public void Print(ResultSet[] resultSet) {
            int total_min_weight = 0;

            System.out.println("************************Prim's using Priority queue************************");
            //System.out.println("Minimum Spanning Tree: ");
            for (int i = 1; i < vertices; i++) {
                //  System.out.println("Edge: " + i + " - " + resultSet[i].parent + " key: " + resultSet[i].weight);
                total_min_weight += resultSet[i].weight;
            }

            System.out.print("\nMinimum Cost Spanning Tree " + total_min_weight);
            
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Class to define the Parent and weight for evry vertx 
    static class ResultSet {

        int parent;
        int weight;
    }
}
