// Copy paste this Java Template and save it as "SchedulingDeliveries.java"
import java.util.*;
import java.io.*;

// write your matric number here:A0103516U
// write your name here:Akshat Dubey
// write list of collaborators here:Suranjana Sengupta, Aditya Swami
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class SchedulingDeliveries {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  BinaryHeap heapWoman;

  public SchedulingDeliveries() {
    // Write necessary code during construction
    //
    // write your answer here
  	heapWoman = new BinaryHeap();

  }

  void ArriveAtHospital(String womanName, int dilation) {
    // You have to insert the information (womanName, dilation)
    // into your chosen data structure
    //
    // write your answer here
  	PregnantWoman newWoman = new PregnantWoman(womanName, dilation);
  	heapWoman.Insert(newWoman);
  }

  void UpdateDilation(String womanName, int increaseDilation) {
    // You have to update the dilation of womanName to
    // dilation += increaseDilation
    // and modify your chosen data structure (if needed)
    //
    // write your answer here
  	PregnantWoman updateWoman = new PregnantWoman(womanName, increaseDilation);
  	heapWoman.Update(updateWoman);
  }

  void GiveBirth(String womanName) {
    // This womanName gives birth 'instantly'
    // remove her from your chosen data structure
    //
    // write your answer here
  	heapWoman.Delete(womanName);
  }

  String Query() {
    String ans = "The delivery suite is empty";

    // You have to report the name of the woman that the doctor
    // has to give the most attention to. If there is no more woman to
    // be taken care of, return a String "The delivery suite is empty"
    //
    // write your answer here
    String nameWoman = heapWoman.Query();
    
    if(nameWoman != "")
    	ans = nameWoman;

    return ans;
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: GiveBirth(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    SchedulingDeliveries ps1 = new SchedulingDeliveries();
    ps1.run();
  }
}

class PregnantWoman {
	String name;
	int dilation;
	int tokenNo;

	PregnantWoman(String womanName, int womanDilation){
		name = womanName;
		dilation = womanDilation;
		tokenNo = 0;
	}
}

class BinaryHeap {
	  private Vector<PregnantWoman> A;
	  private int BinaryHeapSize;
	  private int Tokens;

	  BinaryHeap() {
	    A = new Vector<PregnantWoman>();
	    A.add(new PregnantWoman("", 0)); // dummy
	    BinaryHeapSize = 0;
	    Tokens = 0;
	  }

	  int parent(int i) { return i >> 1; } // shortcut for i/2, round down
	  
	  int left(int i) { return i << 1; } // shortcut for 2*i
	  
	  int right(int i) { return (i << 1) + 1; } // shortcut for 2*i + 1
	  
	  void shiftUp(int i) {
	    while (i > 1 && A.get(parent(i)).dilation <= A.get(i).dilation) {
	    	if((A.get(parent(i)).dilation == A.get(i).dilation) && (A.get(parent(i)).tokenNo < A.get(i).tokenNo)){
	    		break;
	    	}
			PregnantWoman temp = A.get(i);
			A.set(i, A.get(parent(i)));
			A.set(parent(i), temp);
			i = parent(i);
	    }
	  }

	  void Insert(PregnantWoman woman) {
	    BinaryHeapSize++;
	    Tokens++;

	    woman.tokenNo = Tokens;
	    if (BinaryHeapSize >= A.size())
	      A.add(woman);
	    else
	      A.set(BinaryHeapSize, woman);
	    shiftUp(BinaryHeapSize);
	  }

	  void shiftDown(int i) {
	    while (i <= BinaryHeapSize) {
	      int maxV = A.get(i).dilation, max_id = i;
	      if (left(i) <= BinaryHeapSize && maxV <= A.get(left(i)).dilation) { // compare value of this node with its left subtree, if possible
	        if(!((maxV == A.get(left(i)).dilation) && (A.get(i).tokenNo < A.get(left(i)).tokenNo))){
	        	maxV = A.get(left(i)).dilation;
	        	max_id = left(i);
	        }  
	      }
	      if (right(i) <= BinaryHeapSize && maxV <= A.get(right(i)).dilation) { // now compare with its right subtree, if possible
	        if(!((maxV == A.get(right(i)).dilation) && (A.get(i).tokenNo < A.get(right(i)).tokenNo))){
	        	maxV = A.get(right(i)).dilation;
		        max_id = right(i);		        
	        }
	      }
	  
	      if (max_id != i) {
	        PregnantWoman temp = A.get(i);
	        A.set(i, A.get(max_id));
	        A.set(max_id, temp);
	        i = max_id;
	      }
	      else
	        break;
	    }
	  }
	  
	  PregnantWoman ExtractMax() {
	    PregnantWoman maxV = A.get(1);
	    A.set(1, A.get(BinaryHeapSize));
	    BinaryHeapSize--; // virtual decrease
	    shiftDown(1);
	    return maxV;
	  }

	  int size() { return BinaryHeapSize; }
	  
	  boolean isEmpty() { return BinaryHeapSize == 0; }

	  void Update(PregnantWoman woman){
	  	int index = findIndex(woman.name);
	  	PregnantWoman tempWoman;
	  	if(index!=0){
	  		tempWoman = A.get(index);
	  		tempWoman.dilation = tempWoman.dilation + woman.dilation;
	  		shiftUp(index);
	  		shiftDown(index);
	  	}
	  }

	  void Delete(String womanName){
	  	int index = findIndex(womanName);
	  	if(index != 0){
	  		A.set(index, A.get(BinaryHeapSize));
  			A.remove(BinaryHeapSize);
  			BinaryHeapSize--;
  			if(index != BinaryHeapSize+1){
  				shiftUp(index);
  				shiftDown(index);
  			}
	  	}
	  }

	  String Query(){
	  	if(BinaryHeapSize > 0)
	  		return A.get(1).name;
	  	else
	  		return "";
	  }

	  int findIndex(String findName){
	  	int index = 0;
	  	for(int i=1; i<=BinaryHeapSize; i++){
	  		if(A.get(i).name.equals(findName)){
	  			index = i;
	  		}
	    }
	    return index;
	}

	/*for part C
	int smartFindIndex(String findName){
	
	}
	*/
}