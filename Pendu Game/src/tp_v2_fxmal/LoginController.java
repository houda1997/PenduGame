/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_v2_fxmal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import jeu.*;
import java.applet.Applet;
import javafx.scene.image.ImageView;
import java.io.*;
import java.util.*;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author TOSHIBA
 */
public class LoginController implements Initializable {
    Jeu jeu = new Jeu ();
    Session session;
    
    @FXML
    private Button entrer;
    @FXML
    private Button inscription;
    @FXML
    private TextField text;
    @FXML
    private AnchorPane panel;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    
   

    /**
     * Initializes the controller class.
     */
   @FXML 
   public boolean enterJeu()  throws IOException { 
   
    FileReader fichier=null;
    try  {     
         fichier = new FileReader("Comptes.txt");
         String nPseudo =text.getText();
            if (jeu.Existe(nPseudo)) {
                int m= jeu.getMeilleurS();
                Joueur joueur = new Joueur (nPseudo,m,0);
                session = new Session (joueur);
                return true; 
                                       }
   else {  
           label3.setVisible(false);
           label4.setVisible(false);
           label2.setVisible(true);
           }  return false;   }
   
   finally {if (fichier!= null) fichier.close();}
   }
   
    
    @FXML 
    public boolean InscripJeu() throws IOException 
       {// FileWriter fichier=null;
    try { 
   String nPseudo = text.getText();
   if (! jeu.Existe(nPseudo) && jeu.EstValide(nPseudo))
    {
   Joueur joueur = new Joueur (nPseudo,0,0);     
   jeu.SetJoueur(joueur);
   session = new Session (joueur);
   return true;
    }
   else{
         if (jeu.Existe(nPseudo)) {
             label2.setVisible(false);
             label4.setVisible(false);
             label3.setVisible(true);} 
         else {
             label2.setVisible(false);
             label3.setVisible(false);
             label4.setVisible(true);}
         return false;   
   }
         
}
  finally { }
   }
 
 @FXML
public void loginEntrer(javafx.event.ActionEvent event) throws IOException
{           
           // enterJeu();
            if  ( enterJeu()) { 
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            MainController controller = fxmlLoader.getController();
            controller.setSession(session);
            Scene scene = new Scene(parent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
            }
}
@FXML
public void loginInscrip(javafx.event.ActionEvent event) throws IOException
{           
           if(InscripJeu()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            MainController controller = fxmlLoader.getController();
            controller.setSession(session);
            Scene scene = new Scene(parent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
           }
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   // panel.setStyle("-fx-background-size:30%; -fx-background-image:url(8.jpg);");
    }    
    
}