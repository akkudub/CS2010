// Copy paste this Java Template and save it as "HospitalTour.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0103516U
// write your name here: Akshat Dubey
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class HospitalTour {
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private int[][] AdjMatrix; // the graph (the hospital)
  private int[] RatingScore; // the weight of each vertex (rating score of each room)

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class;

  private boolean[] included;
  private List<List<Integer>> AdjList;
  
  public HospitalTour() {
    // Write necessary code during construction
    //
    // write your answer here
  }

  int Query() {
    int ans = -1;

    // You have to report the rating score of the important room (vertex)
    // with the lowest rating score in this hospital
    //
    // or report -1 if that hospital has no important room
    //
    // write your answer here

    //finding out which nodes are impt, cant use degree

    int degree;
    for (int i=0; i<V; i++) {
      if (isImportant(i)) {
        if (ans >= 0) {
          if (RatingScore[i] < ans) {
            ans = RatingScore[i];
          }
        }else{
          ans = RatingScore[i];
        }
      }
    }

    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------
  private boolean isImportant(int vertex){
    included[vertex] = false;
    boolean importance = false;
    List<Integer> neighbours = AdjList.get(vertex);
    if (neighbours.size() > 1) {
      if(BFS(neighbours.get(0)) != (V-1)){
        importance = true;
      }
    }
    included[vertex] = true;
    return importance;
  }

  //return the list of vertexes reachable from the given vertex via BFS
  int BFS(int from){
    boolean[] visited = new boolean[V];
    int connections = 1;
    LinkedList<Integer> tempQueue = new LinkedList<Integer>();
    visited[from] = true;
    tempQueue.addLast(from);

    while(!tempQueue.isEmpty()){
      int u = tempQueue.pop();
      for (int i = 0; i<AdjList.get(u).size(); i++) {
        int tempVertex = AdjList.get(u).get(i);
        if (!visited[tempVertex] && included[tempVertex]) {
          visited[tempVertex] = true;
          tempQueue.addLast(tempVertex);
          connections++;
        }
      }
    }
    return connections;
  }

  //convert adjacency matrix to adj list
  private List<List<Integer>> AdjListify(int[][] Matrix){
    List<List<Integer>> finalList = new ArrayList<List<Integer>>();
    for (int i=0; i<V; i++) {
      List<Integer> temp = new ArrayList<Integer>();
      for (int j=0; j<V; j++) {
        if (Matrix[i][j] == 1) {
          temp.add(j);
        }
      }
      finalList.add(temp);
    }
    return finalList;
  }

  // --------------------------------------------

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];
      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency Matrix
      AdjMatrix = new int[V][V];
      for (int i = 0; i < V; i++) {
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
          int j = Integer.parseInt(st.nextToken());
          AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
        }
      }
      included = new boolean[V];
      Arrays.fill(included, true);
      AdjList = AdjListify(AdjMatrix);
      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalTour ps3 = new HospitalTour();
    ps3.run();
  }
}