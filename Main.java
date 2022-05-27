import java.util.Scanner;

class StringMatcher {
    private static String bookTitles[];
    ReadFile rf = new ReadFile();
    public StringMatcher() {
        rf.OpenFile("titles.txt");
        rf.readFile();
        bookTitles = rf.getFile();
    }

    public void findBook(String pat) {
        int counter = 0;
        for(int i = 0; i < bookTitles.length; i++) {
            if(kmp(bookTitles[i].toLowerCase(), pat.toLowerCase())) {
                System.out.println(bookTitles[i]);
                counter++;
            }
            if(counter == 3) { 
                break;
            }
        }
    }

    private int[] computeLPSArray(String str) {
        int len = str.length();

        int[] lps = new int[len];
        lps[0] = 0;

        for(int i = 1; i < len; i++) {
            int j = lps[i - 1];

            while((j > 0) && (str.charAt(i) != str.charAt(j))) {
                j = lps[j - 1];
            }
            if(str.charAt(i) == str.charAt(j)) {
                j++;
            }
            lps[i] = j;
        }
        return lps;
    }

    public boolean isSpaced(String txt){
        for (int i = 0; i < txt.length(); i++) {
            if(txt.charAt(i) == ' '){
                return true;
            }
        }
        return false;
    }

    public boolean kmp(String txt, String pat) {
        if(!isSpaced(pat)){
            int M = pat.length();
            int N = txt.length();

            int lps[] = new int[M];
            lps = computeLPSArray(pat);
            int j = 0;

            int i = 0;
            while (i < N) {
                if (pat.charAt(j) == txt.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    return true;
                }
                else if (i < N && pat.charAt(j) != txt.charAt(i)) {

                    if (j != 0)
                    j = lps[j - 1];
                    else
                    i++;
                }
            }
            return false;
        } else{
            String[] patterns = pat.split(" ");
            int N = txt.length();

            for (int i = 0; i < patterns.length; i++) {
                int M = patterns[i].length();
                int lps[] = new int[M];
                lps = computeLPSArray(patterns[i]);
                int k = 0;
                int j = 0;

                while (j < N){
                    if (patterns[i].charAt(k) == txt.charAt(j)){
                        k++;
                        j++;
                    }
                    if (k == M){
                        return true;
                    }
                    else if(j < N && patterns[i].charAt(j) != txt.charAt(j)){
                        if(k != 0)
                        k = lps[j-1];
                        else
                        j++;
                    }
                }
            }
            return false;
        }


        
    }
}

class Graph{
    private int numVertex;
    private int[][] adjacencyMatrix;

    public Graph(int numVertex){
        this.numVertex = numVertex;
        adjacencyMatrix = new int[numVertex][numVertex];
    }

    public void addEdge(int from, int to, int len){
        adjacencyMatrix[from][to] = len;
        adjacencyMatrix[to][from] = len;
    }

    public void displayGraph(){
        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void dijkstra(int src, int dst){
        int[] distance = new int[numVertex];
        boolean[] fixed = new boolean [numVertex];  
        for (int i = 0; i < numVertex; i++) {
            distance[i] = Integer.MAX_VALUE;
            fixed[i] = false;
        }
        distance[src] = 0;
        while(true){
            int marked = minIndex(distance, fixed);
            if(marked<0) break;
            if(distance[marked]==Integer.MAX_VALUE) break;
            fixed[marked] = true;
            for (int j = 0; j < numVertex; j++) {
                if (adjacencyMatrix[marked][j]>0 && !fixed[j]){
                    int newDistance = distance[marked]+adjacencyMatrix[marked][j];

                    if(newDistance < distance[j])distance[j] = newDistance;
                }
            }
        }
        if(distance[dst] == Integer.MAX_VALUE){
            System.out.println("no route");
        } else{
            System.out.println("distance-" + distance[dst]);
        }
    }

    public int minIndex(int[] distance, boolean[] fixed){
        int idx=0;
        for(;idx<fixed.length; idx++){
            if(!fixed[idx]) break;
        }
        if(idx == fixed.length) return -1;
        for (int i = idx; i < fixed.length; i++) {
            if(!fixed[i]&&distance[i]<distance[idx]) idx = i;
        }
        return idx;
    }
}

public class Main {
    public static void main(String[] args) {
        // Sample town map
        int numOfVertex = 10;
        Graph sampleTownMap = new Graph(numOfVertex);

        // Library is in node 1, 3, 7
        sampleTownMap.addEdge(0,1,5);
        sampleTownMap.addEdge(0, 3, 7);
        sampleTownMap.addEdge(0,9,15);
        sampleTownMap.addEdge(2,4, 4);
        sampleTownMap.addEdge(1,2,12);
        sampleTownMap.addEdge(3, 8, 8);
        sampleTownMap.addEdge(2,7,9);
        sampleTownMap.addEdge(4, 5, 6);
        sampleTownMap.addEdge(5,9,10);
        sampleTownMap.addEdge(4, 6, 8);

        Scanner int_input = new Scanner(System.in);
        System.out.println("Welcome to Book Warden How can I help you?");
        System.out.println("Choose one of our services below");
        System.out.println("1. Book Finder");
        System.out.println("2. Find Nearest Library");
        int choice = int_input.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Welcome to Book Finder!");
                StringMatcher sm = new StringMatcher();
                System.out.println("Input book title/year/writer/etc ");
                Scanner string_input = new Scanner(System.in);
                String a = string_input.nextLine();
                sm.findBook(a);
                string_input.close();
                break;
            
            case 2:
                System.out.println("Welcome to Library Finder");
                Scanner loc_input = new Scanner(System.in);
                System.out.println("Please input your location in map: ");
                int location = loc_input.nextInt();
                sampleTownMap.dijkstra(location, 1);
                sampleTownMap.dijkstra(location, 3);
                sampleTownMap.dijkstra(location, 7);
                loc_input.close();
                break;
            default:
                break;
        }

        int_input.close();

        
    }
}