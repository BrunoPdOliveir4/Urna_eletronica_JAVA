import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class Voting {
    CONSTANTS constants = new CONSTANTS();
    DB_Manager db = new DB_Manager();
    private final String StartKey;
    String textoFim = "Voto contabilizado com sucesso!";

    int choiceNow = 0;
    //Variables
    String[] nowVotos = new String[3];
    String votingNow = "";
    String[] actualCandidate;
    Voting(String StartKey){
        this.StartKey = StartKey;
    }

    //Elementos
    JFrame initialized;
    JTextField voteString;
    JLabel numbsLabel;
    JPanel inputsPanel;
    JPanel candidatesPanel;
    JPanel textPanel;

    String reserv = "";

    //Quantidade limite de inputs
    int limitInput = 11;

    /**
     * Inicializar o programa
     */
    void initiaze() {
        if (StartKey.equals(db.getValidKey())) {
            db.setVoters();
            initialized = new JFrame();

            inputsPanel = new JPanel();
            inputsPanel.setBackground(Color.lightGray);
            inputsPanel.setBounds(constants.PANELWIDTH,
                    0, constants.PANELWIDTH, constants.PANELHEIGHT);

            inputsPanel.setLayout(null);

            candidatesPanel = new JPanel();
            candidatesPanel.setBackground(new Color(245, 255, 245));
            candidatesPanel.setBounds(0,
                    0, constants.PANELWIDTH, constants.PANELHEIGHT);
            initialized.add(inputsPanel);
            candidatesPanel.setLayout(null);



            initialized.add(inputsPanel);
            initialized.add(candidatesPanel);
            setCandidatesPanel();
            setInputsPanel();


            initialized.setSize(constants.SCREENWIDTH, constants.SCREENHEIGHT);
            initialized.setResizable(false);
            initialized.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            initialized.setLayout(null);
            initialized.setVisible(true);
        }
    }
    JLabel textoCandidato;
    JLabel nomeCandidato;
    JLabel nomePartido;
    JLabel foto;
    JLabel textoFimContinue;
    JLabel nomeVoter;
    /**
     * Painel lateral esquerdo, Painel de Outputs
     */
    void setCandidatesPanel(){
        textPanel = new JPanel();
        textPanel.setBounds(constants.BUTTONWIDTH - (constants.BUTTONWIDTH / 2)+35,
                constants.BUTTONHEIGHT/2+15,
                constants.BUTTONWIDTH * 3,
                constants.BUTTONHEIGHT/2);

        textoCandidato = new JLabel("Digite o título de eleitor:");
        textoCandidato.setBounds(constants.BUTTONWIDTH,
                0,
                constants.PANELWIDTH/2,
                constants.BUTTONHEIGHT);
        textoCandidato.setFont(new Font("Arial", Font.BOLD, 30));

        textoFimContinue = new JLabel("");
        textoFimContinue.setBounds(constants.BUTTONWIDTH/3,
                constants.BUTTONHEIGHT*2+150,
                constants.BUTTONWIDTH*7,
                constants.BUTTONHEIGHT*2);
        textoFimContinue.setForeground(Color.red);
        textoFimContinue.setFont(new Font("Arial", Font.BOLD, 35));

        voteString = new JTextField("");
        voteString.setBounds(constants.PANELWIDTH/4,
                constants.BUTTONHEIGHT/2,
                constants.BUTTONWIDTH*5,
                constants.BUTTONHEIGHT/2
        );
        voteString.setFont(new Font("Arial", Font.BOLD, 40));
        voteString.setVisible(false);

        nomeCandidato = new JLabel("");
        nomeCandidato.setBounds(constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT,
                constants.BUTTONWIDTH*5,
                constants.BUTTONHEIGHT/2
        );
        nomeCandidato.setFont(new Font("Arial", Font.BOLD, 20));

        nomePartido = new JLabel("");
        nomePartido.setBounds(constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT+50,
                constants.BUTTONWIDTH*5,
                constants.BUTTONHEIGHT/2
        );
        nomePartido.setFont(new Font("Arial", Font.BOLD, 20));

        foto = new JLabel();
        foto.setOpaque(true);
        foto.setBounds(constants.PANELWIDTH/2+constants.BUTTONWIDTH/2+50,
                constants.BUTTONHEIGHT/2-15,
                constants.BUTTONWIDTH*2-(constants.BUTTONWIDTH/8),
                constants.BUTTONHEIGHT*2);
        foto.setIcon(db.chooseImg("default"));


        numbsLabel = new JLabel(voteString.getText());
        numbsLabel.setBounds(0,0,
                constants.BUTTONWIDTH*5,
                constants.BUTTONHEIGHT);
        numbsLabel.setFont(new Font("Arial", Font.BOLD, 50));
        numbsLabel.setBackground(Color.WHITE);

        nomeVoter = new JLabel("");
        nomeVoter.setBounds(constants.BUTTONWIDTH,
                constants.PANELHEIGHT/2+constants.BUTTONHEIGHT,
                constants.PANELWIDTH/2,
                constants.BUTTONHEIGHT);
        nomeVoter.setFont(new Font("Arial", Font.BOLD, 30));

        candidatesPanel.add(nomeVoter);
        candidatesPanel.add(textoFimContinue);
        candidatesPanel.add(nomeCandidato);
        candidatesPanel.add(nomePartido);
        candidatesPanel.add(textoCandidato);
        candidatesPanel.add(foto);
        candidatesPanel.add(textPanel);
        textPanel.add(numbsLabel);

    }

    /**
     * Painel de inputs, botões etc. Painel lateral direito
     */
    void setInputsPanel(){
        JButton num1 = new JButton("1 ⠼\n" +
                "⠁");
        num1.setFont(new Font("Arial", Font.BOLD,40));
        num1.setBounds(constants.BUTTONWIDTH-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2-(constants.BUTTONHEIGHT*2),
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num1.setBackground(Color.BLACK);
        num1.setForeground(Color.WHITE);
        num1.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'1');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num2 = new JButton("2 ⠼\n" +
                "⠃");
        num2.setFont(new Font("Arial", Font.BOLD,40));
        num2.setBounds(constants.BUTTONWIDTH*2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2-(constants.BUTTONHEIGHT*2),
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num2.setBackground(Color.BLACK);
        num2.setForeground(Color.WHITE);
        num2.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'2');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num3 = new JButton("3 ⠼\n" +
                "⠉");
        num3.setFont(new Font("Arial", Font.BOLD,40));
        num3.setBounds(constants.PANELWIDTH/2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2-(constants.BUTTONHEIGHT*2),
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num3.setBackground(Color.BLACK);
        num3.setForeground(Color.WHITE);
        num3.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'3');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num4 = new JButton("4 ⠼\n" +
                "⠙");
        num4.setFont(new Font("Arial", Font.BOLD,40));
        num4.setBounds(constants.BUTTONWIDTH-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2-constants.BUTTONHEIGHT,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num4.setBackground(Color.BLACK);
        num4.setForeground(Color.WHITE);
        num4.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'4');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num5 = new JButton("5 ⠼\n" +
                "⠑");
        num5.setFont(new Font("Arial", Font.BOLD,40));
        num5.setBounds(constants.BUTTONWIDTH*2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2-constants.BUTTONHEIGHT,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num5.setBackground(Color.BLACK);
        num5.setForeground(Color.WHITE);
        num5.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'5');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num6 = new JButton("6 ⠼\n" +
                "⠋");
        num6.setFont(new Font("Arial", Font.BOLD,40));
        num6.setBounds(constants.PANELWIDTH/2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2-constants.BUTTONHEIGHT,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num6.setBackground(Color.BLACK);
        num6.setForeground(Color.WHITE);
        num6.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'6');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});


        JButton num7 = new JButton("7 ⠼\n" +
                "⠛");
        num7.setFont(new Font("Arial", Font.BOLD,40));
        num7.setBounds(constants.BUTTONWIDTH-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num7.setBackground(Color.BLACK);
        num7.setForeground(Color.WHITE);
        num7.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'7');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num8 = new JButton("8 ⠼\n" +
                "⠓");
        num8.setFont(new Font("Arial", Font.BOLD,40));
        num8.setBounds(constants.BUTTONWIDTH*2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num8.setBackground(Color.BLACK);
        num8.setForeground(Color.WHITE);
        num8.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'8');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num9 = new JButton("9 ⠼\n" +
                "⠊");
        num9.setFont(new Font("Arial", Font.BOLD,40));
        num9.setBounds(constants.PANELWIDTH/2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num9.setBackground(Color.BLACK);
        num9.setForeground(Color.WHITE);
        num9.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'9');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }});

        JButton num0 = new JButton("0 ⠼\n" +
                "⠚");
        num0.setFont(new Font("Arial", Font.BOLD,40));
        num0.setBounds(constants.BUTTONWIDTH*2-(constants.BUTTONWIDTH/2),
                constants.SCREENHEIGHT/2+constants.BUTTONHEIGHT,
                constants.BUTTONWIDTH,
                constants.BUTTONHEIGHT);
        num0.setBackground(Color.BLACK);
        num0.setForeground(Color.WHITE);
        num0.addActionListener(Action->{
            if(voteString.getText().length() < limitInput){
                voteString.setText(voteString.getText()+'0');
                numbsLabel.setText(voteString.getText());
            }
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
            if(limitInput < 11 && voteString.getText().length() == limitInput){
                String candidate = (voteString.getText());
                actualCandidate = db.getCandidate(candidate);
                nomeCandidato.setText(actualCandidate[1]);
                nomePartido.setText(actualCandidate[2]);
                foto.setIcon(db.chooseImg(actualCandidate[1]));
            }else{
                foto.setIcon(db.chooseImg("default"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }
        });

        JButton brancoBtn = new JButton("""
                ☐ ⠨⠨
                ⠃
                ⠗
                ⠁
                ⠝
                ⠉
                ⠕""");
        brancoBtn.setFont(new Font("Arial", Font.BOLD,30));
        brancoBtn.setBounds(constants.PANELWIDTH/2+constants.BUTTONWIDTH/2,
                constants.SCREENHEIGHT/2-(constants.BUTTONHEIGHT*2),
                constants.BUTTONWIDTH*2,
                constants.BUTTONHEIGHT);
        brancoBtn.setBackground(Color.WHITE);
        brancoBtn.addActionListener(Action->{
            if(limitInput < 11){
                StringBuilder text = new StringBuilder();
                for(int i = 0; i < limitInput; i++){
                    text.append("0");
                }
                if(textoCandidato.getText().equals(textoFim)){
                    textoCandidato.setText("Digite o título de eleitor:");
                    textoCandidato.setBounds(constants.BUTTONWIDTH,
                            0,
                            constants.PANELWIDTH/2,
                            constants.BUTTONHEIGHT);
                    textoCandidato.setForeground(Color.black);
                    textoFimContinue.setText("");
                }
                switch(choiceNow){
                    case 1:
                        if(text.length() == 2){
                            nowVotos[0] = text.toString();
                            choiceNow++;
                            limitInput = 3;
                            textoCandidato.setText("Escolha Senador: ");
                            numbsLabel.setText("");
                            voteString.setText("");
                        }
                        break;
                    case 2:
                        if(text.length() == 3){
                            nowVotos[1] = text.toString();
                            choiceNow++;
                            textoCandidato.setText("Escolha outro Senador: ");
                            numbsLabel.setText("");
                            voteString.setText("");
                        }
                        break;
                    case 3:
                        if(text.length() == 3){
                            if(text.toString().equals(nowVotos[1])){
                                nowVotos[2] = "000";
                            }else{
                                nowVotos[2] = text.toString();
                            }
                            choiceNow++;
                            textoCandidato.setText(textoFim);
                            textoFimContinue.setText("Aperte qualquer botão para votar novamente!");
                            textoCandidato.setBounds(constants.BUTTONWIDTH,
                                    constants.BUTTONHEIGHT*2,
                                    constants.BUTTONWIDTH*7,
                                    constants.BUTTONHEIGHT*2);
                            textoCandidato.setForeground(Color.red);
                            numbsLabel.setText("");
                            voteString.setText("");
                            try {
                                this.reseter();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                }
            }
            foto.setIcon(db.chooseImg("default"));
            nomeCandidato.setText("");
            nomePartido.setText("");
        });

        JButton excludeBtn = new JButton("✖ ⠨⠨\n" +
                "⠁\n" +
                "⠏\n" +
                "⠁\n" +
                "⠛\n" +
                "⠁");
        excludeBtn.setFont(new Font("Arial", Font.BOLD,30));
        excludeBtn.setBounds(constants.PANELWIDTH/2+constants.BUTTONWIDTH/2,
                constants.SCREENHEIGHT/2-constants.BUTTONHEIGHT,
                constants.BUTTONWIDTH*2,
                constants.BUTTONHEIGHT);
        excludeBtn.setBackground(new Color(122, 16, 16));
        excludeBtn.addActionListener(Action->{
            voteString.setText("");
            numbsLabel.setText(voteString.getText());
            foto.setIcon(db.chooseImg("default"));
            nomeCandidato.setText("");
            nomePartido.setText("");
            if(textoCandidato.getText().equals(textoFim)){
                textoCandidato.setText("Digite o título de eleitor:");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        0,
                        constants.PANELWIDTH/2,
                        constants.BUTTONHEIGHT);
                textoCandidato.setForeground(Color.black);
                textoFimContinue.setText("");
            }
        });

        JButton validaBtn = new JButton("✔  ⠨⠨\n" +
                "⠕\n" +
                "⠅");
        validaBtn.setFont(new Font("Arial", Font.BOLD,40));
        validaBtn.setBounds(constants.PANELWIDTH/2+constants.BUTTONWIDTH/2,
                constants.SCREENHEIGHT/2,
                constants.BUTTONWIDTH*2,
                constants.BUTTONHEIGHT*2);
        validaBtn.setBackground(new Color(93, 119, 61));
        validaBtn.addActionListener(Action->{
            String textNow = voteString.getText();
            if(limitInput < 12){
                if(textoCandidato.getText().equals(textoFim)){
                    textoCandidato.setText("Digite o título de eleitor:");
                    textoCandidato.setBounds(constants.BUTTONWIDTH,
                            0,
                            constants.PANELWIDTH/2,
                            constants.BUTTONHEIGHT);
                    textoCandidato.setForeground(Color.black);
                    textoFimContinue.setText("");
                }
                boolean isverify = verifyer(textNow);
                switch (choiceNow){
                    case 0:
                        isverify = verifyer(textNow);
                        votingNow = textNow;
                        if(isverify) {
                            choiceNow++;
                            limitInput = 2;
                            textoCandidato.setText("Escolha Presidente: ");
                            numbsLabel.setText("");
                            voteString.setText("");
                        }
                        break;
                    case 1:
                        isverify = verifyer(textNow);
                        if(isverify){
                            nowVotos[0] = textNow;
                            choiceNow++;
                            limitInput = 3;
                            textoCandidato.setText("Escolha Senador: ");
                            numbsLabel.setText("");
                            voteString.setText("");
                            foto.setIcon(db.chooseImg("default"));
                            nomeCandidato.setText("");
                            nomePartido.setText("");
                        }
                        break;
                    case 2:
                        isverify = verifyer(textNow);
                        if(isverify){
                            nowVotos[1] = textNow;
                            choiceNow++;
                            textoCandidato.setText("Escolha outro Senador: ");
                            numbsLabel.setText("");
                            voteString.setText("");
                            foto.setIcon(db.chooseImg("default"));
                            nomeCandidato.setText("");
                            nomePartido.setText("");
                        }
                        break;
                    case 3:
                        isverify = verifyer(textNow);
                        if(isverify){
                            if(textNow.equals(nowVotos[2])){
                                nowVotos[1] = "000";
                            }else{
                                nowVotos[2] = textNow;
                            }
                            choiceNow++;
                            textoCandidato.setText(textoFim);
                            textoFimContinue.setText("Aperte qualquer botão para votar novamente!");
                            textoCandidato.setBounds(constants.BUTTONWIDTH,
                                    constants.BUTTONHEIGHT*2,
                                    constants.BUTTONWIDTH*7,
                                    constants.BUTTONHEIGHT*3);
                            textoCandidato.setForeground(Color.red);
                            numbsLabel.setText("");
                            voteString.setText("");
                            try {
                                reseter();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            foto.setIcon(db.chooseImg("default"));
                            nomeCandidato.setText("");
                            nomePartido.setText("");
                        }
                        break;
                }

            }else{
                if(textNow.equals(db.getValidKey())){
                    for (String[] votes:
                          db.votos) {
                        System.out.println(Arrays.toString(votes));
                    }
                    finalRelatoryGenerator();
                    this.initialized.dispose();
                }else{
                    numbsLabel.setText("");
                    voteString.setText("");
                    textoCandidato.setText(reserv);
                    textoCandidato.setBounds(constants.BUTTONWIDTH,
                                    0,
                                    constants.PANELWIDTH/2,
                                    constants.BUTTONHEIGHT);
                    switch (choiceNow){
                        case 0:
                            limitInput = 11;
                            break;
                        case 1:
                            limitInput = 2;
                            break;
                        case 2:
                            limitInput = 3;
                            break;
                        case 3:
                            limitInput = 3;
                            break;
                    }
                }
            }
        });
        JButton end = new JButton("TERMINAR");
        end.setFont(new Font("end", Font.BOLD,40));
        end.setBounds(constants.PANELWIDTH-constants.BUTTONWIDTH*5,
                0,
                constants.BUTTONWIDTH*4,
                constants.BUTTONHEIGHT);
        end.setBackground(new Color(222, 255, 225));
        end.addActionListener(Action->{
            if (limitInput != 20) {
                numbsLabel.setText("");
                voteString.setText("");
                limitInput = 20;
                reserv = textoCandidato.getText();
                if (textoCandidato.getText().equals(textoFim)){
                    reserv = "Digite o título de eleitor:";
                    textoFimContinue.setText("");
                }
                textoCandidato.setForeground(Color.black);
                textoCandidato.setText("Digite a senha de finalização");
                textoCandidato.setBounds(constants.BUTTONWIDTH,
                        constants.PANELHEIGHT/2,
                        constants.PANELWIDTH,
                        constants.BUTTONHEIGHT);
                foto.setIcon(db.chooseImg("defalt"));
                nomeCandidato.setText("");
                nomePartido.setText("");
            }
        });

        inputsPanel.add(end);
        inputsPanel.add(voteString);
        inputsPanel.add(num1);
        inputsPanel.add(num2);
        inputsPanel.add(num3);
        inputsPanel.add(validaBtn);
        inputsPanel.add(num4);
        inputsPanel.add(num5);
        inputsPanel.add(num6);
        inputsPanel.add(excludeBtn);
        inputsPanel.add(num7);
        inputsPanel.add(num8);
        inputsPanel.add(num9);
        inputsPanel.add(num0);
        inputsPanel.add(brancoBtn);
    }

    /**
     * Verificar validade dos dados inseridos
     * @param tryIt
     * @return
     */
    boolean verifyer(String tryIt){
        boolean verified = false;
        switch (limitInput) {
            case 2:
                try {
                    String[] voted = db.getCandidate(tryIt);
                    if(voted[0] != null){
                        verified = true;
                    }
                } catch (NumberFormatException e) {}
                break;
            case 3:
                try {
                    String[] voted = db.getCandidate(tryIt);
                    if(voted[0] != null){
                        verified = true;
                    }
                } catch (NumberFormatException e) {}
                break;
            case 11:
                String[] voters = db.getDbVoters();
                for (int value = 0; value < voters.length; value++) {
                    if (tryIt.equals(voters[value])){
                        verified = true;
                        nomeVoter.setText("Votando agora: "+db.dbVotersNumbersNames[value]);
                    }
                }
                break;
        }

        return verified;
    }

    void reseter() throws InterruptedException {
        db.alreadyVoted.add(votingNow);
        String[] voters = db.getDbVoters();
        for(int value = 0; value < voters.length; value++){
            if(votingNow.equals(voters[value])){
                voters[value] = voters[value] + " Votou!";
                db.setDbFantasiaVoters(voters);
            }
        }
        nomeVoter.setText("");
        votingNow = "";
        db.votos.add(nowVotos);
        nowVotos = new String[3];
        limitInput = 11;
        choiceNow = 0;
        nomeVoter.setText("");
    }

    /**
     * Serve para gerar relatórios .txt e outras manipuações em conjunto com o banco de dados
     */
    void finalRelatoryGenerator(){
        try{
            FileReader alreadyvoted = new FileReader("Relatory.txt");
            int data = alreadyvoted.read();
            String script = "";
            while (data != -1){
                script += (char)data;
                data = alreadyvoted.read();
            }
            alreadyvoted.close();

            //RELATÓRIO DOS VOTOS
            FileWriter relatory = new FileWriter("Relatory.txt");
            for(String[] voto : db.votos){
                script += Arrays.toString(voto)+"\n";
            }
            relatory.append(script);
            relatory.close();
            resultado(script);
            FileWriter finalRelatory = new FileWriter("FinalRelatory.txt");
            script = relatorycalc(script);
            finalRelatory.write(script);
            finalRelatory.close();

            String[] dbase = db.getDbVotersUpdate();
            //MANTÉM DB ATUALIZADO
            FileWriter voted = new FileWriter("Voters.txt");
            String voters = "";
            for(int voter = 0; voter < dbase.length; voter++){
                voters += dbase[voter]+"-";
            }
            voted.write(voters);
            voted.close();

            //RETORNA QUEM VOTOU!
            FileWriter hasVoted = new FileWriter("Votaram.txt");
            String correctVoters = "";
            String noVoters = "";
            for(int voter = 0; voter < dbase.length; voter++){
                try{
                    if (dbase[voter].length() > 11){
                        correctVoters += dbase[voter] +"\n";
                    }else {
                        try {
                            long teste = Long.parseLong(dbase[voter]);
                            noVoters += dbase[voter] + "- não votou \n";
                        } catch (NumberFormatException e) {}
                    }
                }
                catch (NullPointerException e){}
            }
            hasVoted.write(correctVoters);
            hasVoted.close();
            FileWriter hasntVoted = new FileWriter("naoVotaram.txt");
            hasntVoted.write(noVoters);
            hasntVoted.close();
        }
        catch (IOException e){}
    }

    /**
     * Encontra a quantidade de votos para cada candidato e organiza.
     * @param unorganizedRelatory
     * @return
     */
    String relatorycalc(String unorganizedRelatory){
        String organizedRelatory = "VOTOS:";
        unorganizedRelatory = unorganizedRelatory.replace("[", "");
        unorganizedRelatory = unorganizedRelatory.replace("]", "");
        unorganizedRelatory = unorganizedRelatory.replace("\n", ",");
        unorganizedRelatory = unorganizedRelatory.replace(" ","");
        String[] relat = unorganizedRelatory.split(",");
        String[] candidate = new String[db.candidates.length];
        String[] finalCandidate = candidate;
        String brancos = "Brancos: ";
        for(int i = 0; i < candidate.length; i++){
            candidate[i] = db.candidates[i][0];
            finalCandidate[i] = db.candidates[i][1];
        }
        for(int vote = 0; vote < relat.length; vote++){
            for(int candidat = 0; candidat < candidate.length; candidat++){
                String[] candidato = db.getCandidate(relat[vote]);
                try{
                    if(candidato[1].equals(candidate[candidat].replace("1", "")) && candidato[1] != null){
                        finalCandidate[candidat] += "1";
                        break;
                    }
                }
                catch(NullPointerException e) {
                    brancos += "1";
                    break;
                }
            }
        }
        for(int vote = 0; vote < finalCandidate.length; vote++){
            int votecounter = 0;
            for(int str = 0; str < finalCandidate[vote].length(); str++){
                if(finalCandidate[vote].charAt(str) == '1'){
                    votecounter += 1;
                }
            }
            finalCandidate[vote] = finalCandidate[vote].replace("1", "");
            finalCandidate[vote] += " votos: "+ Integer.toString(votecounter);
            organizedRelatory += "\nCandidato: "+finalCandidate[vote];
        }
        organizedRelatory += "\n"+brancos;
        return organizedRelatory;
    }
    void resultado(String votes){
        try{
            votes = votes.replace("[", "");
            votes = votes.replace("]", "");
            votes = votes.replace("\n", ",");
            votes = votes.replace(" ","");
            String[] votesSepared = votes.split(",");

            int maiscontadoPresd = 0;
            int maiscontadosen = 0;
            int maiscontadosen2 = 0;

            String eleitoPresd = "";
            String eleitoSen1= "";
            String eleitoSen2= "";

            System.out.println(Arrays.toString(votesSepared));
            for(int i = 0; i < votesSepared.length; i++){
                int contando = 0;
                for(int j = 0; j < votesSepared.length; j++){
                     if(votesSepared[i].equals(votesSepared[j])){
                        if(i != j && votesSepared[i] != "contabilizado"){
                            contando += 1;
                            votesSepared[j] = "contabilizado";
                        }
                    }
                }
                if((votesSepared[i].length() == 2 && !votesSepared[i].equals("00"))||
                        (votesSepared[i].length() == 3 && !votesSepared[i].equals(" 00"))||
                        (votesSepared[i].length() == 4 && !votesSepared[i].equals(" 000"))){
                    if(votesSepared[i].length() <= 2){
                        if (contando > maiscontadoPresd){
                            maiscontadoPresd = contando;
                            eleitoPresd = votesSepared[i];
                        }else if(contando == maiscontadoPresd){
                            eleitoPresd += ", "+ votesSepared[i];
                        }
                    }else{
                        if (contando > maiscontadosen){
                            maiscontadosen = contando;
                            eleitoSen1 = votesSepared[i];
                        } else if (contando > maiscontadosen2) {
                            maiscontadosen2 = contando;
                            eleitoSen2 = votesSepared[i];
                        }
                    }
                }
            }
            if(eleitoPresd.equals("") || eleitoPresd.length() > 4){
                eleitoPresd = "votos empatados, então foi incapaz de definir um vencedor.";
            }
            String relatorio = "Seguem os eleitos para: ";
            if(eleitoPresd.length() < 5){
                String[] presd = db.getCandidate(eleitoPresd);
                float porcentPres = ((maiscontadoPresd+1)*100)/(votesSepared.length/3);
                relatorio += "\n Presidente: "+presd[1]+" do partido "+presd[2]+"\n Quantidade de votos = "+
                Integer.toString(maiscontadoPresd+1)+"/"+Integer.toString(votesSepared.length/3)+", ("+porcentPres+"%).";
            }else{
                relatorio += "\n Presidente tiveram "+eleitoPresd;
            }
            String[] sen1 = db.getCandidate(eleitoSen1);
            float porcentSen1 = ((maiscontadosen+1)*100)/(votesSepared.length/3*2);
            if(sen1[1] == null) {
                sen1[1] = "Votos Brancos";
                sen1[2] = " ";
            }
            String[] sen2 = db.getCandidate(eleitoSen2);
            if(sen2[1] == null){
                sen2[1] = "Votos Brancos";
                sen2[2] = " ";
            }
            float porcentSen2 = ((maiscontadosen2+1)*100)/(votesSepared.length/3*2);
            relatorio += "\n Senador 1: "+sen1[1]+" do partido "+sen1[2]+"\n Quantidade de votos = "+
                    Integer.toString(maiscontadosen+1)+"/"+Integer.toString(votesSepared.length/3*2)+", ("+porcentSen1+"%).\n "+
            "Senador 2:"+sen2[1]+" do partido "+sen2[2]+"\n Quantidade de votos = "+
                    Integer.toString(maiscontadosen2+1)+"/"+Integer.toString(votesSepared.length/3*2)+", ("+porcentSen2+"%).";
            System.out.println(Arrays.toString(votesSepared));
            System.out.println(relatorio);
            try{
                FileWriter result = new FileWriter("Resultado.txt");
                result.write(relatorio);
                result.close();
            }
            catch (IOException e){}
        }
        catch (ArithmeticException e){}
    }
}