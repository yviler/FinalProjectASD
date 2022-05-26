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

public class Main {
    public static void main(String[] args) {
        StringMatcher sm = new StringMatcher();
        Scanner input = new Scanner(System.in);
        String a = input.nextLine();
        sm.findBook(a);
        input.close();
    }
}