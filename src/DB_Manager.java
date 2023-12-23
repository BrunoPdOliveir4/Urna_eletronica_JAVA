import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Banco de dados da aplicação(por não ter uma conexão com rede foi feito em txts.)
 */
public class DB_Manager {
    int candidate = 0;
    ArrayList<String[]> votos = new ArrayList<>();
    ArrayList<String> alreadyVoted = new ArrayList<>();
    String[][] candidates = new String[0][0];
    DB_Manager(){
        String[][] readyCandidates = new String[90][3];
        try{
            FileReader reader = new FileReader("Partidos.txt");
            int data = reader.read();
            String script = "";
            while (data != -1){
                script += (char)data;
                data = reader.read();
            }
            reader.close();
            String[] divided = script.split(":");
            for(int extorganizer = 0; extorganizer < 90; extorganizer++){
                for(int organizer = 1; organizer <= 3; organizer++){
                    readyCandidates[extorganizer][(organizer-1)] = divided[((extorganizer*3)-1)+(organizer)];
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        this.candidates = readyCandidates;
    }
    public ImageIcon chooseImg(String candidate){
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("default.jpeg"));
        try{
            ImageIcon imageIcon2 = new ImageIcon(getClass().getResource(candidate+".jpg"));
            imageIcon = imageIcon2;
        }
        catch(NullPointerException e){

        };
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(494, 350, 1));
        return imageIcon;
    }

    public String[] getDbVotersUpdate() {
        for(int numero = 0; numero < dbVoters.length-1; numero++){
            dbVotersUpdate[numero*2+1] = dbVoters[numero];

        }
        return dbVotersUpdate;
    }

    public void setDbVotersUpdate(String[] dbVotersUpdate) {
        this.dbVotersUpdate = dbVotersUpdate;
    }

    String[] dbVotersUpdate = new String[0];
    public void setDbFantasiaVoters(String[] dbFantasiaVoters) {
        this.dbVoters = dbFantasiaVoters;
    }
    String[] dbVotersNumbersNames = new String[0];
    void setVoters(){
        try{
            FileReader reader = new FileReader("Voters.txt");
            int data = reader.read();
            String script = "";
            while (data != -1){
                script += (char)data;
                data = reader.read();
            }
            reader.close();
            String[] dbFantasiaVoters = script.split("-");
            String[] dbVotersNames = new String[dbFantasiaVoters.length/2+1];
            String[] dbVotersNumbers = new String[dbFantasiaVoters.length/2+1];
            for(int nome = 0; nome < dbFantasiaVoters.length; nome++){
                if(nome%2 != 0)dbVotersNumbers[nome/2] = dbFantasiaVoters[nome];
                if(nome%2 == 0)dbVotersNames[(nome+1)/2] = dbFantasiaVoters[nome];
            }
            dbVotersUpdate = dbFantasiaVoters;
            dbVotersNumbersNames = dbVotersNames;
            setDbFantasiaVoters(dbVotersNumbers);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    /**
     * Receber o database de "votadores"
     * @return
     */
    public String[] getDbVoters() {
        return dbVoters;
    }

    private String[] dbVoters = new String[1];


    /**
     * recebe a chave
     * @return
     */
    public String getValidKey() {
        return validKey;
    }

    /**
     * Encontra um candidato pelo seu numero
     * @param number
     * @return
     */
    public String[] getCandidate(String number){
        String[] candidatoReturn = new String[3];
        for(int i = 0; i < candidates.length; i++){
            number = number.replace(" ", "");

            if(candidates[i][0].equals(number)){
                candidatoReturn = candidates[i];
                break;
            }
        }
        return candidatoReturn;
    }
    /**
     * chave de validação para inicialização do programa
     */
    private final String validKey = "33344123";
}