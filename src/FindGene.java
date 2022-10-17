import java.net.SocketOption;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.Array;

public class FindGene {

    public static String cadre1 = "RTKFALPYVS*SCESPFLGTWRTQVL*KVLMKVINRFRKITLLSDVLLLPRMTVIPVFACIFHVWLNVPALWFKTNSNNMLLCSVG*RFTTSVLGRGKEKT*KETPSTKSKFLLYGCKMSR*NLKS*FLY*RKFL*GLLVWCV*LRYIRTLC*VEVGLQNWKCQGHFHNKCTETIVETFRGK*KQAFKGDMPFDCTCG*SKCGYSK*RCSLIVKYSLDFDDVAQKKNTLITLFLKNFVSLLEKFMLHMWLAVS*H*SY*FFYILPEMSNRCCFPLLFIFNIAFFFF*FATRSPRFSAMLRQWFFV*VVQQCCASLQEERPDSQKDYHLAFSNPVMRLMIINVSIFTEKFKEILMITKITYLSLEEFKWIGSKSEIYLV*PGEI*IWSQHMIFLRVILLSNIAKYSLKIPLIKIIYLRKVFRIVPKN*EYISGIKG*IICI*VLIQYS*NFSILLKSTFCH*NYSKGRMHLFNILS*FFCRLFI*KKATLMIQTAS*ILILCCLTESLIINSIILINLPR*CNYIWFCKVYSSNLLFWCQFFNKVLIMGK";
    public static String cadre2 = "GQSLPYHMFPDRAKALF*GLGEHKYYEKY**KLLTGFEK*LYYLTCCFCRG*PLFLFLHVYFTYG*MCQRCGLKLIVIICFFVQLARDLLHPSLEEEKKKHKKKRLVQSPNSYFMDVKCPGKI*NLNSFTKENFCRDC*CGVYS*DTLEPSVE*KWDYRIGNVRDIFIINVLKPL*KHFGVSENRHSKEICHLTALVDNQNVDTQNRDVL*L*NTHWILTM*HRKKIH*LHCF*KILCRC*KSLCYTCGLLFHSTEVTDFFTYYQKCRTGAVFLCFSFLTLPFFFFSLLQDHHGFQPCSDSGSLCRLFNSVVPAYRRKGQTHRRIIIWHSPTQ**D**L*MSLSSLKSLKKS**LPK*LISHWKSSSGLAANLRSIWCDLVRSKYGVST*FF*E*YC*VILLSIV*KYL*SKLFT*EKYSE*FLKIKSIFLV*KDK*SVYEY*SNILKTSVFYLKVLFVIKIIAKVECTCLIYSHDSFADCSFRRKQH**FKQLPEF*FCVVSQKALS*IP*F*LIYQDNVITFGFVRYTAVISYFGVSFSIKF*LWA";
    public static String cadre3 = "DKVCLTICFLIVRKPFSRDLENTSIMKSTDESY*QVSKNNFTI*RVASAEDDRYSCFCMYISRMVKCASVVV*N****YASLFSWLEIYYIRPWKRKRKNIKRNA*YKVQILTLWM*NVQVKFEILIPLLKKISVGIASVVCIVKIH*NPLLSRSGITELEMSGTFS**MY*NHCRNISG*VKTGIQRRYAI*LHLWIIKMWILKIEMFFNCKILTGF*RCSTEKKYIDYTVFKKFCVAARKVYVTHVACCFIALKLLIFLHITRNVEQVLFSSAFHF*HCLFFFLVCYKITTVFSHAQTVVLCVGCSTVLCQPTGGKARLTEGLSFGILQPSDEIDDYKCLYLH*KV*RNLNDYQNNLSLTGRVQVDWQQI*DLFGVTW*DLNMESAHDFFKSNIAK*YC*V*SENTSNQNYLLEKSIQNSS*KLRVYFWYKRINNLYMSINPIFLKLQYFT*KYFLSLKL*QR*NALV*YTLMILLQIVHLEESNTNDSNSFLNFNFVLSHRKPYHKFHNSN*FTKIM*LHLVL*GIQQ*SPILVSVFQ*SFDYGQ";
    public static String gene = "MCQRCGLKLIVIICFFVQLARDLLHPSLEEEKKKHKKKRLVQSPNSYFMDVKCPGCYKITTVFSHAQTVVLCVGCSTVLCQPTGGKARLTEGCSFRRKQH";

    public static void main(String[] args){

        int score1 = findGene(gene, cadre1);
        int score2 = findGene(gene, cadre2);
        int score3 = findGene(gene, cadre3);

        int meilleurScore = Math.min(Math.min(score1, score2), score3);
        if(meilleurScore == score1){
            System.out.println("Cadre de lecture 1 avec une mesure de distance de " + meilleurScore);
        }else if(meilleurScore == score2){
            System.out.println("Cadre de lecture 2 avec une mesure de distance de " + meilleurScore);
        } else {
            System.out.println("Cadre de lecture 3 avec une mesure de distance de " + meilleurScore);
        }

    }

    public static int findGene(String gene, String sequence){

        int[][] tableau = new int[gene.length()+1][sequence.length()+1];
        for (int i =0; i<=gene.length(); i++){
            tableau[i][0] = 8*i;
            for(int j=1; j<=sequence.length();j++){
                if (i == 0) {
                    tableau[0][j] = 0;
                } else{
                    int matchScore;
                    if(gene.charAt(i-1)==sequence.charAt(j-1)){
                        matchScore = 0;
                    } else{
                        matchScore = 4;
                    }
                    tableau[i][j] = Math.min(Math.min(tableau[i-1][j] +8, tableau[i][j-1] +8), tableau[i-1][j-1] + matchScore);
                }
            }
        }
        int minScore = 2147483647;
        for (int j=0; j<sequence.length(); j++){
            if (tableau[gene.length()][j] < minScore){
                minScore = tableau[gene.length()][j];
            }
        }

        return minScore;
    }
}
