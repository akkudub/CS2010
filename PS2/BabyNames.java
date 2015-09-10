// Copy paste this Java Template and save it as "BabyNames.java"
import java.util.*;
import java.io.*;
import java.util.NoSuchElementException; // we will use this to illustrate Java Error Handling mechanism

// write your matric number here: A0103516U
// write your name here: Akshat Dubey
// write list of collaborators here: Suranjana Sengupta
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class BabyNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  // --------------------------------------------
  private BST BoyBabies;
  private BST GirlBabies;
  private HashMap<String, Integer> Hash;
  // --------------------------------------------

  public BabyNames() {
    // Write necessary code during construction;
    //
    // write your answer here

    // --------------------------------------------

	  BoyBabies = new BST();
	  GirlBabies = new BST();
	  Hash = new HashMap<String, Integer>();

    // --------------------------------------------
  }

  void AddSuggestion(String babyName, int genderSuitability) {
    // You have to insert the information (babyName, genderSuitability)
    // into your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
	  if(genderSuitability == 1)
      BoyBabies.insert(babyName);
    else
      GirlBabies.insert(babyName);

    Hash.put(babyName, genderSuitability);

    // --------------------------------------------
  }

  void RemoveSuggestion(String babyName) {
    // You have to remove the babyName from your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
    if(Hash.get(babyName) == 1){
      BoyBabies.delete(babyName);
      Hash.remove(babyName);
    }else if(Hash.get(babyName) == 2){
      GirlBabies.delete(babyName);
      Hash.remove(babyName);
    }

    // --------------------------------------------
  }

  int Query(String START, String END, int genderPreference) {
    int ans = 0;

    // You have to answer how many baby name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here

    // --------------------------------------------
    int tempBoys = 0;
    int tempGirls = 0;
    if(genderPreference == 0){
      tempBoys = BoyBabies.query(START, END);
      tempGirls = GirlBabies.query(START, END);
    }else if(genderPreference == 1){
      tempBoys = BoyBabies.query(START, END);
    }else if(genderPreference == 2){
      tempGirls = GirlBabies.query(START, END);
    }
    
    ans = tempBoys + tempGirls;

    // --------------------------------------------

    return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddSuggestion
        AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemoveSuggestion
        RemoveSuggestion(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BabyNames ps2 = new BabyNames();
    ps2.run();
  }
}

// Every vertex in this BST is a Java Class
class BSTVertex {
  BSTVertex(String v) { name=v; parent=left=right=null; height=0; size=1;}
  // all these attributes remain public to slightly simplify the code
  public BSTVertex parent, left, right;
  public String name;
  public int height; // will be used in AVL lecture
  public int size;
}

// This is just a sample implementation
// There are other ways to implement BST concepts...
class BST {
  protected BSTVertex root;
  protected ArrayList<BSTVertex> nodeList;

  protected BSTVertex search(BSTVertex T, String v) {
         if (T == null)  return T;                                  // not found
    else if (T.name.equals(v)) return T;                                      // found
    else if (T.name.compareTo(v) < 0)  return search(T.right, v);       // search to the right
    else                 return search(T.left, v);         // search to the left
  }

  protected BSTVertex insert(BSTVertex T, String v) {
    if (T == null){
    	T = new BSTVertex(v);          // insertion point is found
    	//nodeList.add(T);
    	return T;
    }

    if (T.name.compareTo(v) < 0) {                                      // search to the righttttt
      T.right = insert(T.right, v);
      T.right.parent = T;
      if(getHeight(T.left) - getHeight(T.right) == -2){               //unnbalanced
        if(T.right.name.compareTo(v) < 0){
          T = rotateLeft(T);
        }else{
          T.right = rotateRight(T.right);
          T = rotateLeft(T);
        }
      }
    }else{                                                 // search to the left
      T.left = insert(T.left, v);
      T.left.parent = T;
      if(getHeight(T.left) - getHeight(T.right) == 2){
        if(T.left.name.compareTo(v)>0){
          T = rotateRight(T);
        }else{
          T.left = rotateLeft(T.left);
          T = rotateRight(T);
        }
      }
    }
    T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
    T.size = getSize(T.left) + getSize(T.right) + 1;
    return T;                                          // return the updated BST
  }

  protected void inorder(BSTVertex T) {
    if (T == null) return;
    inorder(T.left);                               // recursively go to the left
    System.out.printf(" %d", T.name);                      // visit this BST node
    inorder(T.right);                             // recursively go to the right
  }

  // Example of Java error handling mechanism
  /* // old code, returns -1 when there is no minimum (the BST is empty)
  protected int findMin(BSTVertex T) {
         if (T == null)      return -1;                             // not found
    else if (T.left == null) return T.name;                    // this is the min
    else                     return findMin(T.left);           // go to the left
  }
  */

  protected String findMin(BSTVertex T) {
         if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
    else if (T.left == null) return T.name;                    // this is the min
    else                     return findMin(T.left);           // go to the left
  }

  protected String findMax(BSTVertex T) {
         if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
    else if (T.right == null) return T.name;                   // this is the max
    else                      return findMax(T.right);        // go to the right
  }

  protected String successor(BSTVertex T) {
    if (T.right != null)                       // this subtree has right subtree
      return findMin(T.right);  // the successor is the minimum of right subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? null : par.name;           // this is the successor of T
    }
  }

  protected String predecessor(BSTVertex T) {
    if (T.left != null)                         // this subtree has left subtree
      return findMax(T.left);  // the predecessor is the maximum of left subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its left children
      while ((par != null) && (cur == par.left)) { 
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? null : par.name;           // this is the successor of T
    }
  }

  protected BSTVertex delete(BSTVertex T, String v) {
    if (T == null)  return T;              // cannot find the item to be deleted'

    if (T.name.equals(v)) {                          // this is the node to be deleted
      if (T.left == null && T.right == null)                   // this is a leaf
        T = null;                                      // simply erase this node
      else if (T.left == null && T.right != null) {   // only one child at right
        BSTVertex temp = T;
        T.right.parent = T.parent;
        T = T.right;                                                 // bypass T
        temp = null;
      }
      else if (T.left != null && T.right == null) {    // only one child at left
        BSTVertex temp = T;
        T.left.parent = T.parent;
        T = T.left;                                                  // bypass T
        temp = null;
      }
      else {                                 // has two children, find successor
        String successorV = successor(v);
        T.name = successorV;         // replace this key with the successor's key
        T.right = delete(T.right, successorV);      // delete the old successorV
      }
    }
    else if (T.name.compareTo(v) < 0)                                   // search to the right
      T.right = delete(T.right, v);
    else                                                   // search to the left
      T.left = delete(T.left, v);

    if(T!= null){
      if(getHeight(T.left) - getHeight(T.right) == -2){               //unnbalanced
        if(T.right.name.compareTo(v) < 0){
          T = rotateLeft(T);
        }else{
          T.right = rotateRight(T.right);
          T = rotateLeft(T);
        }
      }else if(getHeight(T.left) - getHeight(T.right) == 2){
        if(T.left.name.compareTo(v)>0){
          T = rotateRight(T);
        }else{
          T.left = rotateLeft(T.left);
          T = rotateRight(T);
        }
      }
      T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
      T.size = getSize(T.left) + getSize(T.right) + 1;
    }
    return T;                                          // return the updated BST
  }
  
  protected BSTVertex rotateRight(BSTVertex Q){
      BSTVertex P = Q.left;
      if(Q.parent != null)        
        P.parent = Q.parent;
      Q.parent = P;
      Q.left = P.right;
      if (P.right != null)
        P.right.parent = Q;
      P.right = Q;
      Q.height = Math.max(getHeight(Q.left), getHeight(Q.right)) + 1;
      P.height = Math.max(getHeight(P.left), getHeight(Q)) + 1;
      Q.size = getSize(Q.left) + getSize(Q.right) + 1;
      P.size = getSize(P.left) + getSize(Q) + 1;
      return P;
  }

  protected BSTVertex rotateLeft(BSTVertex P){
      BSTVertex Q = P.right;
      if(P.parent != null)
        Q.parent = P.parent;
      P.parent = Q;
      P.right = Q.left;
      if (Q.left != null)
        Q.left.parent = P;
      Q.left = P;
      P.height = Math.max(getHeight(P.left), getHeight(P.right)) + 1;
      Q.height = Math.max(getHeight(Q.right), getHeight(P)) + 1;
      P.size = getSize(P.left) + getSize(P.right) + 1;
      Q.size = getSize(Q.right) + getSize(P) + 1;
      return Q;
  }

  protected int getHeight(BSTVertex T){
    return T == null ? -1 : T.height;
  }

  protected int getSize(BSTVertex T){
    return T == null ? 0 : T.size;
    // if(T == null)
    //   return 0;
    // else return getSize(T.left) + getSize(T.right) +1;
  }

  protected int getRank(String S, BSTVertex T){
    if (T == null) return 0; 
    int temp = S.compareTo(T.name);
    if      (temp < 0) return getRank(S, T.left); 
    else if (temp > 0) return 1 + getSize(T.left) + getRank(S, T.right); 
    else              return getSize(T.left); 
  }

  protected void printBST() {
    for (int i = 0; i < nodeList.size(); i++) {
      System.out.println(nodeList.get(i).name + " " + getHeight(nodeList.get(i)) + " " + getSize(nodeList.get(i)));
    }
  }

  public BST(){
	  root = null;
	  nodeList = new ArrayList<BSTVertex>();
  }

  public String search(String v) {
    BSTVertex res = search(root, v);
    return res == null ? "-1" : res.name;
  }

  public void insert(String v){
    root = insert(root, v);
    //printBST();
  }

  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  public String findMin() { return findMin(root); }

  public String findMax() { return findMax(root); }

  public String successor(String v) { 
    BSTVertex vPos = search(root, v);
    return vPos == null ? null : successor(vPos);
  }

  public String predecessor(String v) { 
    BSTVertex vPos = search(root, v);
    return vPos == null ? null : predecessor(vPos);
  }

  public void delete(String v) { root = delete(root, v); }

  // will be used in AVL lecture
  protected int calcHeight(BSTVertex T) {
    if (T == null) return -1;
    else return Math.max(calcHeight(T.left), calcHeight(T.right)) + 1;
  }

  public int calcHeight() { return calcHeight(root); }

  public int query(String START, String ENDING){
    return getRank(ENDING, root) - getRank(START, root);
  }
}