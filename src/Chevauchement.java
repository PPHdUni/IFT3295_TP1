import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Chevauchement {

    public static void main(String[] args) throws IOException {



        String[] sequences = ReadSequence("sequence.fq");
//        sequences[0] = "AGCTGGTA";
//        sequences[1] = "GAGTACT";


        Alignement(sequences[0], sequences[1]);

    }

    public static String[] ReadSequence(String fileName) {
        String[] sequences = new String[2];
        String prevSeqID="";
        int i = 0;
        System.out.println(System.getProperty("user.dir"));

        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()&&i<2) {
                String line = reader.nextLine();
                if(line.startsWith("@")&&(!line.equals(prevSeqID))) {
                    prevSeqID = line;
                    sequences[i] = reader.nextLine();
                    i++;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        }

        return sequences;
    }

    public static int Alignement(String seq1, String seq2) {

        char[] sequence1=seq1.toCharArray(),
                sequence2=seq2.toCharArray();
        int[][] tableauScore = new int[sequence2.length+1][sequence1.length+1];
        String[][] tableauChev1 = new String[sequence2.length+1][sequence1.length+1],
                tableauChev2 = new String[sequence2.length+1][sequence1.length+1];
        tableauScore[0][0] = 0;
        tableauChev1[0][0] = "";
        tableauChev2[0][0] = "";

        System.out.print("\n");
        System.out.print("    ");
        for (int j = 0; j < sequence1.length;j++) {
            System.out.print(sequence1[j]+" ");
        }
        System.out.print("\n");
        System.out.print("  "+tableauScore[0][0]+" ");

        for (int i = 1; i < sequence2.length+1;i++) {
            tableauScore[i][0] = -8*i;

            tableauChev1[i][0] = tableauChev1[i-1][0]+" ";
            tableauChev2[i][0] = tableauChev2[i-1][0]+sequence2[i-1];
        }
        for (int j = 1; j < sequence1.length+1;j++) {
            tableauScore[0][j] = 0;
            System.out.print(tableauScore[0][j]+" ");

            tableauChev1[0][j] = tableauChev1[0][j-1]+sequence1[j-1];
            tableauChev2[0][j] = tableauChev2[0][j-1]+" ";
        }
        System.out.print("\n");

        for (int i = 1; i < sequence2.length+1;i++) {
            System.out.print(sequence2[i-1]+" "+tableauScore[i][0]+" ");
            for (int j = 1; j < sequence1.length+1;j++) {
                if(sequence1[j-1]==sequence2[i-1]) {
                    tableauScore[i][j] = tableauScore[i-1][j-1]+4;
                }
                else {
                    tableauScore[i][j] = tableauScore[i-1][j-1]-4;
                }
                tableauChev1[i][j] = tableauChev1[i-1][j-1]+sequence1[j-1];
                tableauChev2[i][j] = tableauChev2[i-1][j-1]+sequence2[i-1];

                if(tableauScore[i][j]<tableauScore[i][j-1]-8) {
                    tableauScore[i][j]=tableauScore[i][j-1]-8;
                    tableauChev1[i][j] = tableauChev1[i][j-1]+sequence1[j-1];
                    tableauChev2[i][j] = tableauChev2[i][j-1]+"-";
                }

                if(tableauScore[i][j]<=tableauScore[i-1][j]-8) {
                    tableauScore[i][j]=tableauScore[i-1][j]-8;
                    if (j== sequence1.length) { tableauChev1[i][j] = tableauChev1[i-1][j]+" "; }
                    else { tableauChev1[i][j] = tableauChev1[i-1][j]+"-"; }
                    tableauChev2[i][j] = tableauChev2[i-1][j]+sequence2[i-1];
                }
                System.out.print(tableauScore[i][j]+" ");
            }
            System.out.print("\n");
        }

        int score = 0,
                longueurChev=0;
        for (int i = 1; i < sequence2.length+1;i++) {
            if(score<tableauScore[i][sequence1.length]) {
                score = tableauScore[i][sequence1.length];
                longueurChev = i;
            }
        }

        System.out.print("\n");
        System.out.println("Le score est: ");
        System.out.println(score);
        System.out.print("\n");
        System.out.println("L'alignement optimal est: ");
        System.out.println(tableauChev1[sequence2.length][sequence1.length]);
        for(int i=0; i< tableauChev1[longueurChev][sequence1.length].length()-longueurChev;i++) {
            System.out.print(" ");
        }
        System.out.println(sequence2);
        System.out.print("\n");
        System.out.print("La longeur du chevauchement est: ");
        System.out.println(longueurChev);
        return score;
    }

}
