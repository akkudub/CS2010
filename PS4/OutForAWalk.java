
// Copy paste this Java Template and save it as "OutForAWalk.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0103516U
// write your name here: Akshat Dubey
// write list of collaborators here: Suranjana Sengupta
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class OutForAWalk {
	private int V; // number of vertices in the graph (number of rooms in the
					// building)
	private Vector<Vector<IntegerPair>> AdjList; // the weighted graph (the
													// building), effort rating
													// of each corridor is
													// stored here too

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------

	private int[][] maxEdgeList;
	boolean[] visited;
	private Vector<Vector<IntegerPair>> MST;

	// --------------------------------------------

	public OutForAWalk() {
		// Write necessary codes during construction;
		//
		// write your answer here
		MST = new Vector<Vector<IntegerPair>>();
	}

	void PreProcess() {
		// write your answer here
		// you can leave this method blank if you do not need it
		// what is life anymore
		maxEdgeList = new int [10][V];
		for (int i = 0; i < V; i++) {
			MST.add(i, new Vector<IntegerPair>());
		}
		doPrim(0);
		// System.out.println(MST);
		for (int d = 0; (d < 10) && (d < V); d++) {
			visited = new boolean[V];
			DFS(d, d, 0);
		}
	}

	int Query(int source, int destination) {
		int ans = 0;
		// You have to report the weight of a corridor (an edge)
		// which has the highest effort rating in the easiest path from source
		// to destination for Grace
		//
		// write your answer here
		ans = maxEdgeList[source][destination];

		return ans;
	}

	// You can add extra function if needed
	// --------------------------------------------

	private void doPrim(int source) {
		PriorityQueue<IntegerTriple> primPQ = new PriorityQueue<IntegerTriple>();
		boolean MSTvisited[] = new boolean[V];		
		Vector<IntegerPair> neighbours = AdjList.get(source);

		MSTvisited[source] = true;

		for (int i = 0; i < neighbours.size(); i++) {
			IntegerTriple tempVertex = new IntegerTriple(neighbours.get(i).second(), source, neighbours.get(i).first());
			primPQ.offer(tempVertex);
		}
		// rest of the nodes
		while (!primPQ.isEmpty()) {
			IntegerTriple top = primPQ.poll();
			if (!MSTvisited[top.third()]) {
				MSTvisited[top.third()] = true;
				neighbours = AdjList.get(top.third());
				MST.get(top.third()).add(new IntegerPair(top.second(), top.first()));
				MST.get(top.second()).add(new IntegerPair(top.third(), top.first()));
				for (int j = 0; j < neighbours.size(); j++) {
					IntegerTriple tempVertex = new IntegerTriple(neighbours.get(j).second(),top.third() , neighbours.get(j).first());
					if (!MSTvisited[neighbours.get(j).first()]) {						
						primPQ.offer(tempVertex);
					}
				}
			}
		}
	}

	private void DFS(int source, int curr, int max) {
		visited[curr] = true;
		Vector<IntegerPair> nbrs = MST.get(curr);
		for (int i = 0; i < nbrs.size(); i++) {
			int to = MST.get(curr).get(i).first();
			int weight = MST.get(curr).get(i).second();
			if (!visited[to]) {
				int tempMax = max;
				if (weight > max) {
					tempMax = weight;
				}
				maxEdgeList[source][to] = tempMax;
				DFS(source, to, tempMax);
			}
		}
	}
	// --------------------------------------------

	void run() throws Exception {
		// do not alter this method
		IntegerScanner sc = new IntegerScanner(System.in);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector<Vector<IntegerPair>>();
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector<IntegerPair>());

				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w)); // edge
																// (corridor)
																// weight
																// (effort
																// rating) is
																// stored here
				}
			}

			PreProcess(); // you may want to use this function or leave it empty
							// if you do not need it

			int Q = sc.nextInt();
			while (Q-- > 0)
				pr.println(Query(sc.nextInt(), sc.nextInt()));
			pr.println(); // separate the answer between two different graphs
		}

		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method
		OutForAWalk ps4 = new OutForAWalk();
		ps4.run();
	}
}

class IntegerScanner { // coded by Ian Leow, using any other I/O method is not
						// recommended
	BufferedInputStream bis;

	IntegerScanner(InputStream is) {
		bis = new BufferedInputStream(is, 1000000);
	}

	public int nextInt() {
		int result = 0;
		try {
			int cur = bis.read();
			if (cur == -1)
				return -1;

			while ((cur < 48 || cur > 57) && cur != 45) {
				cur = bis.read();
			}

			boolean negate = false;
			if (cur == 45) {
				negate = true;
				cur = bis.read();
			}

			while (cur >= 48 && cur <= 57) {
				result = result * 10 + (cur - 48);
				cur = bis.read();
			}

			if (negate) {
				return -result;
			}
			return result;
		} catch (IOException ioe) {
			return -1;
		}
	}
}

class IntegerPair implements Comparable<IntegerPair> {
	Integer _first, _second;

	public IntegerPair(Integer f, Integer s) {
		_first = f;
		_second = s;
	}

	public String toString() {
		return _first.toString() + _second.toString();
	}

	public int compareTo(IntegerPair o) {
		if (!this.second().equals(o.second()))
			return this.second() - o.second();
		else
			return this.first() - o.first();
	}

	Integer first() {
		return _first;
	}

	Integer second() {
		return _second;
	}
}

class IntegerTriple implements Comparable<IntegerTriple> {
	Integer _first, _second, _third;

	public IntegerTriple(Integer f, Integer s, Integer t) {
		_first = f;
		_second = s;
		_third = t;
	}

	public int compareTo(IntegerTriple o) {
		if (!this.first().equals(o.first()))
			return this.first() - o.first();
		else if (!this.second().equals(o.second()))
			return this.second() - o.second();
		else
			return this.third() - o.third();
	}

	Integer first() {
		return _first;
	}

	Integer second() {
		return _second;
	}

	Integer third() {
		return _third;
	}
}