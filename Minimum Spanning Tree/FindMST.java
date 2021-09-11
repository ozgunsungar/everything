
import java.util.Arrays;
/*
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;*/


public class FindMST {

    int numberOfVertices; //Number of vertices
    int numberOfEdges; //number of edges
    private int[] vertices; // Vertices array
    private Edge[] MST; // Minimum Spanning Tree Array
    private int mstCost; // Minimum Spanning Tree Cost

    /**
     * Constructor
     *
     * @param edges
     */
    public FindMST(Edge[] edges) {
        //Write codes here

        numberOfEdges = edges.length;
        vertices = CreateVertices(edges);
        numberOfVertices = vertices.length;


        // MST = new Edge[0]; //This line will deleted
    }

    /**
     * A utility function to find set of an element i (uses path compression technique)
     *
     * @param subsets
     * @param v
     * @return
     */
    private int find(Subset[] subsets, int v) {
        //Write codes here

        // find root and make root as parent of i
        // (path compression)
        if (subsets[v].parent != v) {
            subsets[v].parent = find(subsets, subsets[v].parent);
        }


        return subsets[v].parent;

    } //COMPLETED


    /**
     * A function that does union of two sets of x and y (uses union by rank)
     *
     * @param subsets
     * @param x
     * @param y
     */
    private void Union(Subset subsets[], int x, int y) {
        //Write codes here

        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot].rank
                < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank
                > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as
            // root and increment its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }


    } //COMPLETED

    /**
     * Define all vertices from Edge array and return Vertex array
     *
     * @param edges
     * @return
     */
    private int[] CreateVertices(Edge[] edges) {
        //Write codes here

        //Using List
       /* List<Integer> listVertex1 = new LinkedList<>();
        List<Integer> listVertex2 = new LinkedList<>();

        for (Edge temp:edges
             ) {
            if (!listVertex1.contains(temp.getVertex1())){
                listVertex1.add(temp.getVertex1());
            }
            if (!listVertex2.contains(temp.getVertex2())){
                listVertex2.add(temp.getVertex2());
            }
        }
        for (Integer temp:listVertex2
             ) {
            if (!listVertex1.contains(temp)){
                listVertex1.add(temp);
            }
        }
        Collections.sort(listVertex1);

        int[] array = new int[listVertex1.size()];
        int i =0;
        for (int temp:listVertex1
             ) {
            array[i]=temp;
            i++;
        }

        return array;*/


        //Without using List
        /*
        int[] arrVertex1 = new int[numberOfEdges];
        int index = 0;
        for (Edge edge : edges
        ) {
            arrVertex1[index] = edge.getVertex1();
            index++;
        }


        int[] arrVertex2 = new int[numberOfEdges];
        index = 0;
        for (Edge edge : edges
        ) {
            arrVertex2[index] = edge.getVertex2();
            index++;
        }
        int[] arrConcatenated = new int[arrVertex1.length + arrVertex2.length];
        System.arraycopy(arrVertex1, 0, arrConcatenated, 0, arrVertex1.length);
        System.arraycopy(arrVertex2, 0, arrConcatenated, arrVertex1.length, arrVertex2.length);

        Arrays.sort(arrConcatenated);


        int n = arrConcatenated.length;;
        // creating another array for only storing
        // the unique elements
        int[] temp = new int[n];
        int j = 0;

        for (int i = 0; i < n - 1; i++) {
            if (arrConcatenated[i] != arrConcatenated[i + 1]) {
                temp[j++] = arrConcatenated[i];
            }
        }

        temp[j++] = arrConcatenated[n - 1];

        // Changing the original array
        for (int i = 0; i < j; i++) {
            arrConcatenated[i] = temp[i];
        }

        int[] result = new int[j];

        for(int i = 0;i<j;i++){
            result[i]=arrConcatenated[i];
        }


        return  result;
*/

        //Best Method
        int[] tempArr = new int[numberOfEdges * 2];
        int addingCount = 0;
        for (int index = 0; index < numberOfEdges; ++index) {
            int vertex1Num = edges[index].getVertex1();
            int vertex2Num = edges[index].getVertex2();
            if (!isVertexIncluded(tempArr, vertex1Num)) {
                tempArr[vertex1Num] = vertex1Num;
                addingCount++;
            }
            if (!isVertexIncluded(tempArr, vertex2Num)) {
                tempArr[vertex2Num] = vertex2Num;
                addingCount++;
            }
        }
        int[] resultArr = new int[addingCount + 1];
        for (int i = 0; i <= addingCount; i++) {
            resultArr[i] = tempArr[i];
        }
        return resultArr;

    }

    /**
     * Main calculate Minimum Spanning Tree method
     *
     * @param edges
     */
    public void calculateMST(Edge[] edges) {
        //Write codes here

        //result yerine edgeArray kullandım!!!!!
        // This will store the resultant MST
        MST = new Edge[numberOfVertices];

        // An index variable, used for result[]
        int e = 0;
        // An index variable, used for sorted edges
        int i = 0;

        int count = 0;

        Arrays.sort(edges);


        // Allocate memory for creating V subsets
        Subset[] subsets = new Subset[numberOfVertices];
        for (i = 0; i < numberOfVertices; ++i) {
            subsets[i] = new Subset();
        }

        // Create V subsets with single elements
        for (int v = 0; v < numberOfVertices; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        i = 0; // Index used to pick next edge


        // Number of edges to be taken is equal to V-1
        while (e < numberOfVertices - 1) {

            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = edges[i++];




            int x = find(subsets, next_edge.getVertex1());
            int y = find(subsets, next_edge.getVertex2());

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                MST[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // print the contents of result[] to display
        // the built MST

        for (i = 0; i < e; ++i) {
            mstCost += MST[i].getWeight();
        }


    }//MAIN CODE COMPLETED

    /**
     * Get calculated Minimum Spanning Tree ArrayList
     *
     * @return
     */
    public Edge[] getMST() {
        return MST;
    }

    /**
     * Check vertex is included given arr[] or not
     *
     * @param arr
     * @param vertex
     * @return
     */
    private boolean isVertexIncluded(int[] arr, int vertex) {
        //Write codes here
        for (int i : arr) {
            if (i == vertex) {//vertex == arr[i]
                return true;
            }
        }
        return false;

    }//COMPLETED

    /**
     * Get cost of Minimum Spanning Tree
     *
     * @return
     */
    public int getMstCost() {
        return mstCost;
    }

    /**
     * Print Minimum Spanning Tree info
     */
    public void printMST() {
        //Write codes here
        System.out.println("Minimum Spanning Tree Weight :\n" + mstCost);
        System.out.println("Edge List:");
        for (int i = 0; i < numberOfVertices - 1; ++i) {
            System.out.println(MST[i].getVertex1() + " -- "
                    + MST[i].getVertex2()
                    + " == " + MST[i].getWeight());

        }
        System.out.println("*********************");


    }
}
