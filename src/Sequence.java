import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Sequence {

    public static void main(String[] args) throws IOException {

        String[] sequences = ReadSequence("reads.fq");
        String sequence = sequences[1];
        //String sequence = "AGCTGGTA";
        //sequence = sequence + Alignement("AGCTGGTA","CGTACT");
        sequence = sequence + Alignement(sequences[1],sequences[13]);
        sequence = sequence + Alignement(sequences[13],sequences[7]);
        sequence = sequence + Alignement(sequences[7],sequences[10]);
        sequence = sequence + Alignement(sequences[10],sequences[2]);
        sequence = sequence + Alignement(sequences[2],sequences[4]);
        sequence = sequence + Alignement(sequences[4],sequences[0]);
        sequence = sequence + Alignement(sequences[0],sequences[9]);
        sequence = sequence + Alignement(sequences[9],sequences[17]);
        sequence = sequence + Alignement(sequences[17],sequences[19]);
        sequence = sequence + Alignement(sequences[19],sequences[14]);
        sequence = sequence + Alignement(sequences[14],sequences[18]);
        sequence = sequence + Alignement(sequences[18],sequences[12]);
        sequence = sequence + Alignement(sequences[12],sequences[11]);
        sequence = sequence + Alignement(sequences[11],sequences[3]);
        sequence = sequence + Alignement(sequences[3],sequences[8]);
        sequence = sequence + Alignement(sequences[8],sequences[15]);
        sequence = sequence + Alignement(sequences[15],sequences[6]);
        sequence = sequence + Alignement(sequences[6],sequences[5]);
        sequence = sequence + Alignement(sequences[5],sequences[16]);
        System.out.println(sequence);
        System.out.print("La longueur de la séquence est: ");
        System.out.println(sequence.length());




    }

    public static String[] ReadSequence(String fileName) {
        String[] sequences = new String[20];
        String prevSeqID="";
        int i = 0;
        System.out.println(System.getProperty("user.dir"));

        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()&&i<20) {
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

    public static String Alignement(String seq1, String seq2) {

        char[] sequence1=seq1.toCharArray(),
                sequence2=seq2.toCharArray();
        int[][] tableauScore = new int[sequence2.length+1][sequence1.length+1];
        String[][] tableauChev1 = new String[sequence2.length+1][sequence1.length+1],
                tableauChev2 = new String[sequence2.length+1][sequence1.length+1];
        tableauScore[0][0] = 0;
        tableauChev1[0][0] = "";
        tableauChev2[0][0] = "";


        for (int i = 1; i < sequence2.length+1;i++) {
            tableauScore[i][0] = -8*i;

            tableauChev1[i][0] = tableauChev1[i-1][0]+" ";
            tableauChev2[i][0] = tableauChev2[i-1][0]+sequence2[i-1];
        }
        for (int j = 1; j < sequence1.length+1;j++) {
            tableauScore[0][j] = 0;


            tableauChev1[0][j] = tableauChev1[0][j-1]+sequence1[j-1];
            tableauChev2[0][j] = tableauChev2[0][j-1]+" ";
        }


        for (int i = 1; i < sequence2.length+1;i++) {

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

            }

        }

        int score = 0,
                longueurChev=0;
        for (int i = 1; i < sequence2.length+1;i++) {
            if(score<tableauScore[i][sequence1.length]) {
                score = tableauScore[i][sequence1.length];
                longueurChev = i;
            }
        }


        return seq2.substring(longueurChev);
    }

}
