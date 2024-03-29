import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Ce code sert à traduire une séquence nucléotide en code génétique standard dans
// les trois différents cadres de lecture. Ce code peut être exécuté à partir d'une
// invite de commande standard en allant dans le dossier oû se fichier se trouve et en faisant
//
// javac Traduction.java
// pour le compiler et
//
// java Traduction
// pour l'exécuter.
//
// Ce code prend comme input le fichier "sequence.fasta".
// Pour que le fichier soit correctement pris en input, il doit se trouver
// dans le même dossier que ce code.

public class Traduction {

    public static void main(String[] args) throws IOException {

        String sequenceGeno = ReadSequence("sequence.fasta"),
                sequenceCopy,
                codon,
                sequenceTraduit;


        for(int i=0; i<3;i++) {
            sequenceTraduit="";
            sequenceCopy = sequenceGeno.substring(i);

            System.out.println("Cadre de lecture "+(i+1));
            while (2 < sequenceCopy.length()) {
                codon = sequenceCopy.substring(0, 3);

                if (codon.equals("TTT") || codon.equals("TTC")) sequenceTraduit = sequenceTraduit + "F";
                if (codon.equals("TTA") || codon.equals("TTG") || codon.startsWith("CT"))
                    sequenceTraduit = sequenceTraduit + "L";
                if (codon.startsWith("TC")) sequenceTraduit = sequenceTraduit + "S";
                if (codon.equals("TAT") || codon.equals("TAC")) sequenceTraduit = sequenceTraduit + "Y";
                if (codon.equals("TAA") || codon.equals("TAG") || codon.equals("TGA"))
                    sequenceTraduit = sequenceTraduit + "*";
                if (codon.equals("TGT") || codon.equals("TGC")) sequenceTraduit = sequenceTraduit + "C";
                if (codon.equals("TGG")) sequenceTraduit = sequenceTraduit + "W";

                if (codon.startsWith("CC")) sequenceTraduit = sequenceTraduit + "P";
                if (codon.equals("CAT") || codon.equals("CAC")) sequenceTraduit = sequenceTraduit + "H";
                if (codon.equals("CAA") || codon.equals("CAG")) sequenceTraduit = sequenceTraduit + "Q";
                if (codon.startsWith("CG") || codon.equals("AGA") || codon.equals("AGG"))
                    sequenceTraduit = sequenceTraduit + "R";

                if (codon.equals("ATT") || codon.equals("ATC") || codon.equals("ATA"))
                    sequenceTraduit = sequenceTraduit + "I";
                if (codon.equals("ATG")) sequenceTraduit = sequenceTraduit + "M";
                if (codon.startsWith("AC")) sequenceTraduit = sequenceTraduit + "T";
                if (codon.equals("AAT") || codon.equals("AAC")) sequenceTraduit = sequenceTraduit + "N";
                if (codon.equals("AAA") || codon.equals("AAG")) sequenceTraduit = sequenceTraduit + "K";
                if (codon.equals("AGT") || codon.equals("AGC")) sequenceTraduit = sequenceTraduit + "S";

                if (codon.startsWith("GT")) sequenceTraduit = sequenceTraduit + "V";
                if (codon.startsWith("GC")) sequenceTraduit = sequenceTraduit + "A";
                if (codon.equals("GAT") || codon.equals("GAC")) sequenceTraduit = sequenceTraduit + "D";
                if (codon.equals("GAA") || codon.equals("GAG")) sequenceTraduit = sequenceTraduit + "E";
                if (codon.startsWith("GG")) sequenceTraduit = sequenceTraduit + "G";

                sequenceCopy = sequenceCopy.substring(3);
            }
            System.out.println(sequenceTraduit);
            System.out.print("\n");
        }

    }

    public static String ReadSequence(String fileName) {


        String line="";

        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            line = reader.nextLine();
            line = reader.nextLine();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        }

        return line;
    }
}