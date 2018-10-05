/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_v2_fxmal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.util.Random;
import java.util.*;
import jeu.*;
import java.applet.Applet;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author TOSHIBA
 */
public class FXMLDocumentController implements Initializable {
   
    private Joueur joueur = new Joueur ("Test",0,0); // le joueur sera cree lors de la connexion
    private Session session = new Session (joueur); //la session courante 
    private Mot [] tabMots; // pour contenir les mots
    private int nbrReponse=0; //contenir le nombre des réponses justes; permet de passer au mot suivant
    private int nbrTentative; //permet d'afficher les tentative restants pour MultiChance
    
    private Control [] cases ; //un tableau des cases; les cases peuvent etre : TextField ou ComboBox
    @FXML
    Label [] motsLabels;  //Labels des mots: initialiszer dans InitializeMots
    @FXML
    private Text SessionScore; //contient Session score
    @FXML
    private Text meilleurScore; //contient la meilleur score
    @FXML
    private HBox Cases; // contient les cases
    @FXML
    private VBox root;
    @FXML
    private AnchorPane espaceJeu;
    @FXML
    private VBox Mots; //contient les Labels des mots
    @FXML
    private Button start; 
    @FXML
    private Label meilleurScoreLabel;
    @FXML
    private Label SessionScoreLabel;
    @FXML
    private Text Indication; //contient l'indication
    @FXML
    private Label nbrTentaLabel; //contient nombre de tentative restants
    @FXML
    private ImageView hangman ;
    @FXML
    private Button aide; 
    
    
    //ImageView hangman = new ImageView();    
    //-------------------- Initialiser la liste des mots ------------------------//
    public void InitializeMots ()
    {
        int a = session.getnbrMot(); // la constante : nombre des mots
        tabMots = session.getTabMot(); //initialiser les mots
        motsLabels = new Label [a];
        int i; // parcourir le tableaux des labels
        int j; //initialiser les labels
        int lettres; //contenir le nombre de lettre de chaque mots
        String contenue; //le contenue du label
        
        
        for (i=0; i< a; i++){//creation des labels
            lettres = tabMots[i].getNbrLettres(); //nombre de lettres de la ieme mot
            contenue="";
            for (j=0; j< lettres; j++) // initialiser le i-eme mot par XXXXX..
               contenue=contenue+"X"; 
            
            motsLabels[i] = new Label(contenue);
            
            Font myFont = new Font("Times New Roman",17); //modifier le style
            motsLabels[i].setFont(myFont);
            
            Mots.getChildren().add(motsLabels[i]); //ajouter le mot au VBox Mots
        }
    }
    
    //-------------------- Genere des proposition pour Proposition ------------------------//
    private List<Character> generateAlphabets(char l){
        Random r = new Random();
        int j = r.nextInt(4);
        char c;
        int i=0;
        List<Character> TabAlphabet = new ArrayList <Character>(); 
        for(i=0; i<4; i++){
            if (i == j) TabAlphabet.add(l);
            else {
                c = (char)((r.nextInt(26) + 'a'));
                if (!TabAlphabet.contains(c) && c!=l)
                {    
                TabAlphabet.add(c);
                }
                else {i--;}
            }
        }
        return TabAlphabet;
    }
    
     TextField txt= new TextField(); 
     ComboBox box = new ComboBox();
    
   //-------------------- Initialiser les cases ------------------------//
   public void InitialiseCases (Mot mot ){  //a : nombre de lettres = nombre des cases
        
        int i=0; //parcour 
        //Affiche l'indication
        String text = mot.getIndicateur() + " : " + mot.getquestion();
        Indication.setText(text);
        
        //Prepare neauveau mot
        this.nbrReponse=0; //neauveau mot => re-initialisez le calcul des lettres juste 
        this.Cases.getChildren().clear();
        int length=mot.getNbrLettres(); // nombre lettres du mot 
        cases = new Control [length]; //instancier le tableau des cases
        
        List<Character> list ; // pour le ComboBox
        
        for (i=0;i<length; i++){
            
           if (mot.getLettres()[i] instanceof Proposition ){
               
               // Case est une Proposition :
               
               cases [i] = new ComboBox<Character>();
               list = generateAlphabets(mot.getLettres()[i].getReponse()); //generer les proposition y-compris la bonne reponse
               ((ComboBox)cases[i]).getItems().addAll(list);
               ((ComboBox)cases[i]).setId(Integer.toString(i)); //set the ID  
               ((ComboBox)cases[i]).setPrefHeight(40);
               ((ComboBox)cases[i]).setPrefWidth(40);
               //Event : la lettre introduit
               ((ComboBox)cases[i]).setOnAction(new EventHandler<ActionEvent>() { 
                   @Override              
                   public void handle(ActionEvent e) {
                       box = (ComboBox)(e.getSource()); //recuperer la case qui a lancer l'event 
                       ClickCaseProposition(box); //traitement 
                   }     
               });
           }
           else {
               //   Multi chance ou ZeroChance : TextField 
               
               cases [i] = new TextField ();
               ((TextField)cases[i]).setId(Integer.toString(i)); //set the ID  
               ((TextField)cases[i]).setPrefHeight(40);
               ((TextField)cases[i]).setPrefWidth(40);
               ((TextField)cases[i]).setText("X");
               
               if (mot.getLettres()[i] instanceof MultiChance ){
                   //MultiChance 
               ((TextField)cases[i]).setStyle("-fx-background-color: #0be6f4;"); //indiquer c'est un multichance : Blue
               //Event : lettre introduit 
               ((TextField)cases[i]).setOnAction(new EventHandler<ActionEvent>() { 
                   @Override              
                   public void handle(ActionEvent e) {
                       txt = (TextField)(e.getSource());
                       ClickCaseMultiChance(txt);
                   }     
               });
               //Event : mouse enetered ; on affiche les tentatives restants 
               cases[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                 public void handle(MouseEvent e) {
                     int j = Integer.parseInt(((TextField)(e.getSource())).getId()); // recuperer la case 
                     nbrTentative= ((MultiChance)(tabMots[session.getnumMotCourant()].getLettres()[j])).TentativeRestant() +1 ; // calcul des tentatives restant 
                     //Afficher les tentative
                     nbrTentaLabel.setVisible(true);
                     nbrTentaLabel.setText("Nombre de tentative restant : "+Integer.toString(nbrTentative));
                   }
                  });
               //Event : mouse Exit; Hide l'affichage
               cases[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                 public void handle(MouseEvent e) {
                     nbrTentaLabel.setVisible(false);
                   }
                  });
               }
               else {
                   if (mot.getLettres()[i] instanceof ZeroChance ){
                       //Zero chance 
                   ((TextField)cases[i]).setStyle("-fx-background-color: #f40baa;"); //indiquer c'est un Zerochance: rose
               (    (TextField)cases[i]).setOnAction(new EventHandler<ActionEvent>() { 
                   @Override              
                   public void handle(ActionEvent e) {
                       txt = (TextField)(e.getSource());
                      ClickCaseZeroChance(txt);
                   }     
               });
               }
              }  
            }  
           Cases.getChildren().add(cases[i]); //ajouter la case au HBox
    }  
   }
  
   
   //-------------------- Initialiser le jeu ------------------------//
  //evenement : cliquer sur le button Commencer a jouer
          
    @FXML public void StartJeu (){
        session.genererMot(); // generer les mots
        InitializeMots(); //Initialiser les labels affichant les mots
        
        //Intialiser l'interface
        aide.setVisible(false);
        aide.setDisable(true);
        start.setVisible(false);
        start.setDisable(true);
        meilleurScoreLabel.setVisible(true);
        SessionScoreLabel.setVisible(true);
        SessionScore.setText("00");
        String score= Integer.toString(session.getJoueur().getMeilleurScore()); 
        meilleurScore.setText(score);
        
        // Upload du 1er mot 
        Mot mot1 = tabMots[session.getnumMotCourant()]; //tabMot initialiser dans  InitializeMots
            //Affichage des cases à remplir
        InitialiseCases(mot1);
        
    }
    
    //-------------------- Afficher l'image ------------------------//
    public void setHangman (int i){

    String im="tp_v2_fxmal/Etape"+i+".png";
    Image image = new Image(im);
    hangman.setImage(image);
    }
    
    //-------------------- Traitement Case Zerochance  ------------------------//
    
    public void ClickCaseZeroChance (TextField txt){
        
          String text= txt.getText();
          int j = Integer.parseInt(txt.getId()); //id correspond à l'indice de lettre
          int score; //contient score
          char l; //lettre
          
          if (text.length() == 1)  //verifier si le text introduit est un chracter 
           l = text.toCharArray()[0]; 
          else l=0;
          
          //pour l'affichage de la bonne répnse dans le VBox au lieu de XXXX...
          Label label = new Label ();
          Font myFont = new Font("Times New Roman",17);
          label.setFont(myFont);
          
          if (tabMots[session.getnumMotCourant()].ScoreLettre(l, j)) { // le score est incrementer 
               //case rate donc passage au mot suivant
              txt.setStyle("-fx-background-color: #e80808;"); //colorer en rouge 
              
               // on affiche le score du mot 
              score =tabMots[session.getnumMotCourant()].CalculScore() + session.getJoueur().getSessionScore(); 
              session.getJoueur().setSessionScore(score);
              SessionScore.setText(Integer.toString(score)); //affiche le score
              
              //incremnter les mots ratés
              session.setMotRate(session.getMotRate()+1);
              
              //Afficher la bonne réponse dans la liste des Mots
              label.setText(session.getTabMot()[session.getnumMotCourant()].getReponse());
              this.motsLabels[session.getnumMotCourant()] = label;
              this.Mots.getChildren().clear();
              this.Mots.getChildren().setAll(motsLabels);
              
              //Passer au mot suivant ou fin jeu
              if (session.getMotRate() <6){
                session.setnumMotCourant(session.getnumMotCourant()+1); //passer au mot suivant
                InitialiseCases(tabMots[session.getnumMotCourant()]);
              }
              setHangman(session.getMotRate());
              //hangman.setVisible(true);
             
              }
          else  // Le mot n'est pas raté : soit reponse juste ou fausse;
              
              if (tabMots[session.getnumMotCourant()].getLettres()[j].Tester(l)){
                  //Réponse juste 
                  
                  this.nbrReponse++; //incrementer les lettre juste
                  
                  //Vérifier si on a répondu à tout le mot
                  if (nbrReponse == tabMots[session.getnumMotCourant()].getNbrLettres()) { 
                      //passe au mot suivant 
                      
                      //Afficher la bonne réponse dans le VBox
                    label.setText(session.getTabMot()[session.getnumMotCourant()].getReponse());
                    this.motsLabels[session.getnumMotCourant()] = label;
                    this.Mots.getChildren().clear();
                     this.Mots.getChildren().setAll(motsLabels);
                     
                     //Afficher le score 
                      score =tabMots[session.getnumMotCourant()].CalculScore() + session.getJoueur().getSessionScore();
                      session.getJoueur().setSessionScore(score );
                      SessionScore.setText(Integer.toString(score));
                      
                      //initialiser le mot suivant ou fin jeur 
                      if (session.getnumMotCourant() < session.getnbrMot()){
                      session.setnumMotCourant(session.getnumMotCourant()+1); //passer au mot suivant
                       InitialiseCases(tabMots[session.getnumMotCourant()]);
                      }
                  }
                  txt.setDisable(true); // disable la case
                  txt.setStyle("-fx-background-color: #1eff00;"); //colorer en vert
              }
              else  //n'est pas la bonne reponse
                  txt.setStyle("-fx-background-color: #e80808;"); //colorer en rouge
          session.setTabMot(tabMots);
    }
    //-------------------- Traitement Case MultiChance  ------------------------//
    public void ClickCaseMultiChance (TextField txt){
          String text= txt.getText();
          int score;
          char l ;
          int j = Integer.parseInt(txt.getId()); 
          if (text.length() == 1) //verifier si le text introduit est un chracter 
          l = text.toCharArray()[0];
          else 
              l=0;
          
          //pour l'affichage de la bonne répnse dans le VBox au lieu de XXXX...
          Label label = new Label ();
          Font myFont = new Font("Times New Roman",17);
          label.setFont(myFont);
          
          
          if (tabMots[session.getnumMotCourant()].ScoreLettre(l, j)) {// le score est incrementer 
               //case rate donc passage au mot suivant
               
              txt.setStyle("-fx-background-color: #e80808;");//colorer en rouge
              
              //Afficher le score
              score =tabMots[session.getnumMotCourant()].CalculScore() + session.getJoueur().getSessionScore(); //calcul du score
              session.getJoueur().setSessionScore(score );
              SessionScore.setText(Integer.toString(score));
              
              //Afficher la bonne réponse dans la liste des Mots
              label.setText(session.getTabMot()[session.getnumMotCourant()].getReponse());
              this.motsLabels[session.getnumMotCourant()] = label;
              this.Mots.getChildren().clear();
              this.Mots.getChildren().setAll(motsLabels);
              
              //incrementer les mots ratés
               session.setMotRate(session.getMotRate()+1);
               
              //passer au mot suivant ou fin jeu 
              if (session.getMotRate() <6){
                session.setnumMotCourant(session.getnumMotCourant()+1); //passer au mot suivant
                InitialiseCases(tabMots[session.getnumMotCourant()]);
              }
              setHangman(session.getMotRate());
              }
          else{  // Le mot n'est pas raté : soit reponse juste ou fausse;
              
              if (tabMots[session.getnumMotCourant()].getLettres()[j].Tester(l)){
                  //Réponse juste 
                  
                  this.nbrReponse++;//incrementer les lettre juste
                  
                  //Vérifier si on a répondu à tout le mot
                  if (nbrReponse == tabMots[session.getnumMotCourant()].getNbrLettres()) {
                      //Passe u mot suivant
                      
                      //Afficher la bonne réponse dans le VBox
                     label.setText(session.getTabMot()[session.getnumMotCourant()].getReponse());
                     this.motsLabels[session.getnumMotCourant()] = label;
                      this.Mots.getChildren().clear();
                      this.Mots.getChildren().setAll(motsLabels);
                      
                      //Afficher le score
                      score =tabMots[session.getnumMotCourant()].CalculScore() + session.getJoueur().getSessionScore();
                      session.getJoueur().setSessionScore(score );
                      SessionScore.setText(Integer.toString(score));
                      
                      //initialiser le mot suivant
                       if (session.getnumMotCourant() < session.getnbrMot()){
                      session.setnumMotCourant(session.getnumMotCourant()+1); //passer au mot suivant
                       InitialiseCases(tabMots[session.getnumMotCourant()]);
                      }
                  }
                  txt.setDisable(true); //diable
                  txt.setStyle("-fx-background-color: #1eff00;"); //colorer en vert
              }
              else  //réponse n'est pas juste
                  txt.setStyle("-fx-background-color: #e80808;"); //colorer en rouge
          session.setTabMot(tabMots);
    }
  }
   
    //-------------------- Traitement Case MultiChance  ------------------------//
    public void ClickCaseProposition (ComboBox box){
          char l= (char)box.getValue();
          int score;
          int j = Integer.parseInt(box.getId());
          
          //pour affiche la bonne réponse dans la liste des mots
          Label label = new Label ();
          Font myFont = new Font("Times New Roman",17);
          label.setFont(myFont);
          
          if (tabMots[session.getnumMotCourant()].ScoreLettre(l, j)) { 
              
              box.setDisable(true);//disable
              box.setStyle("-fx-background-color: #e80808;");//colorer en rouge
              
              //Affiche la bonne réponse dans la liste des mots
              label.setText(session.getTabMot()[session.getnumMotCourant()].getReponse());
              this.motsLabels[session.getnumMotCourant()] = label;
              this.Mots.getChildren().clear();
              this.Mots.getChildren().setAll(motsLabels);
              
              //Affiche le sxore
               score =tabMots[session.getnumMotCourant()].CalculScore() + session.getJoueur().getSessionScore();
               session.getJoueur().setSessionScore(score );
               SessionScore.setText(Integer.toString(score));
               
               //incrementer les mots raté
              session.setMotRate(session.getMotRate()+1);
              
              //passer au mot suivant ou fin jeu
              if (session.getMotRate() <6){
                session.setnumMotCourant(session.getnumMotCourant()+1); //passer au mot suivant
                InitialiseCases(tabMots[session.getnumMotCourant()]);
              }
             setHangman(session.getMotRate());
              }
          else //Mot n'est pas raté
              if (tabMots[session.getnumMotCourant()].getLettres()[j].Tester(l)){
                  //réponse juste
                  
                  this.nbrReponse++;
                  if (nbrReponse == tabMots[session.getnumMotCourant()].getNbrLettres()) { //le mot est just
                      
                      //Afficher la bonne réponse dans la liste des mots
                      label.setText(session.getTabMot()[session.getnumMotCourant()].getReponse());
                      this.motsLabels[session.getnumMotCourant()] = label;
                      this.Mots.getChildren().clear();
                      this.Mots.getChildren().setAll(motsLabels);
                      
                      //Affiche score
                      score =tabMots[session.getnumMotCourant()].CalculScore() + session.getJoueur().getSessionScore();
                      session.getJoueur().setSessionScore(score );
                      SessionScore.setText(Integer.toString(score));
                      
                      //Passer au mot suivant ou fin jeu
                       if (session.getnumMotCourant() < session.getnbrMot()){
                      session.setnumMotCourant(session.getnumMotCourant()+1); //passer au mot suivant
                       InitialiseCases(tabMots[session.getnumMotCourant()]);
                      }
                  }
                  box.setDisable(true);
                  box.setStyle("-fx-background-color: #1eff00;"); //colorer en vert
              }
              else 
                  box.setStyle("-fx-background-color: #e80808;"); //colorer en rouge
          session.setTabMot(tabMots);
                     
    }
    //------------------------ Affichage du Pendu -----------------------------//
    public void imagePendu (int i){ 

    String im="tp_v2_fxmal/Etape"+i+".png";
    Image image = new Image(im);
    hangman.setImage(image);
    
                    }
    //------------------------ Ouvrir l'Aide ----------------------------------//
    @FXML
     public void Aide (javafx.event.ActionEvent event) throws IOException
             
     { String text1 = new String ("Bienvenue dans notre jeu : Le Pendu,\n");  
       String text2 = new String ("Ce jeu vous permettera d'apprendre une langue en vous amusant à trouver les mots adequat\nà chaque question (Synonyme,Antonyme,Définition)."); 
       String text3 = new String ("Chaque partie contient 10 mots à retrouver,\nCependant vous ne pouvez rater que 6.\n");
       String text4 = new String ("Chaque mot est composé de cases, introduisez les lettres de votre choix dans ces cases mais \n Attention !\n");
       String text5 = new String ("Case Bleu :vous ne pouvez pas la rater\n");
       String text6 = new String ("Case Rouge :vous pouvez la rater  2 fois\n");
       String text7 = new String ("Case multichoix :vous ne pouvez pas la rater mais une liste de choix vous aidera\n");
       String text8 = new String ("\n\n\n                       Bon Courage !!!!");
       Text text = new Text (text1+text2+text3+text4+text5+text6+text7+text8);
       
     final Stage dialog=new Stage ();
     dialog.initModality(Modality.APPLICATION_MODAL);
     VBox dialogVbox = new VBox (20);
     dialogVbox.getChildren().add(text);
     Scene dialogScene = new Scene (dialogVbox, 505,400);
     dialog.setScene(dialogScene);
     dialog.show();
     //aide.setVisible(false);
     
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
}
