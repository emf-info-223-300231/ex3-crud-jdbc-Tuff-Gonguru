package app.presentation;

import app.beans.Personne;
import app.exceptions.MyDBException;
import app.helpers.DateTimeLib;
import app.helpers.JfxPopup;
import app.workers.DbWorker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import java.io.File;
import app.workers.DbWorkerItf;
import app.workers.PersonneManager;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author PA/STT
 */
public class MainCtrl implements Initializable {

  // DBs à tester
  private enum TypesDB {
    MYSQL, HSQLDB, ACCESS
  };

  // DB par défaut
  final static private TypesDB DB_TYPE = TypesDB.MYSQL;

  private DbWorkerItf dbWrk;
  private PersonneManager manPers;
  private boolean modeAjout;

  @FXML
  private TextField txtNom;
  @FXML
  private TextField txtPrenom;
  @FXML
  private TextField txtPK;
  @FXML
  private TextField txtNo;
  @FXML
  private TextField txtRue;
  @FXML
  private TextField txtNPA;
  @FXML
  private TextField txtLocalite;
  @FXML
  private TextField txtSalaire;
  @FXML
  private CheckBox ckbActif;
  @FXML
  private Button btnDebut;
  @FXML
  private Button btnPrevious;
  @FXML
  private Button btnNext;
  @FXML
  private Button btnEnd;
  @FXML
  private Button btnSauver;
  @FXML
  private Button btnAnnuler;
  @FXML
  private DatePicker dateNaissance;  

  /*
   * METHODES NECESSAIRES A LA VUE
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    dbWrk = new DbWorker();
    manPers = new PersonneManager();
    ouvrirDB();
    btnAnnuler.setVisible(false);
    btnSauver.setVisible(false);
  }

  @FXML
  public void actionPrevious(ActionEvent event) {
      afficherPersonne(manPers.precedentPersonne());
  }

  @FXML
  public void actionNext(ActionEvent event) {
      afficherPersonne(manPers.suivantPersonne());
  }
  
  @FXML
  private void actionEnd(ActionEvent event) {
  
      afficherPersonne(manPers.finPersonne());
          }

  @FXML
  private void debut(ActionEvent event) {
      afficherPersonne(manPers.debutPersonne());
  }

  @FXML
  private void menuAjouter(ActionEvent event) throws MyDBException {
      
      rendreVisibleBoutonsDepl(false);
//dbWrk.creer(manPers.courantPersonne());
      modeAjout = true;
      effacerContenuChamps();
  }
  

  @FXML
  private void menuModifier(ActionEvent event) throws MyDBException {
      rendreVisibleBoutonsDepl(false);
      dbWrk.modifier(manPers.courantPersonne());
      modeAjout = false;
  }

  @FXML
  private void menuEffacer(ActionEvent event) throws MyDBException {
      dbWrk.effacer(manPers.courantPersonne());
  }

  @FXML
  private void menuQuitter(ActionEvent event) throws MyDBException {
      
      dbWrk.deconnecter();
      System.exit(1);
  }

  @FXML
  private void annulerPersonne(ActionEvent event) {
      
      modeAjout=false;
      afficherPersonne(manPers.courantPersonne());
      rendreVisibleBoutonsDepl(true);
  }

  @FXML
  private void sauverPersonne(ActionEvent event) throws MyDBException {
     
      Personne p = new Personne(Integer.valueOf(txtPK.getText()),txtNom.getText(), txtPrenom.getText(), java.sql.Date.valueOf(dateNaissance.getValue()), Integer.valueOf(txtNo.getText()), txtRue.getText(), Integer.valueOf(txtNPA.getText()), txtLocalite.getText(), Boolean.valueOf(ckbActif.getText()), Double.valueOf(txtSalaire.getText()), new Date() );
      if (modeAjout==true) {     
          dbWrk.creer(p); 
      }else{
          dbWrk.modifier(p);  
      }
      
      p = manPers.setPersonnes(dbWrk.lirePersonnes());
      afficherPersonne(p);
      rendreVisibleBoutonsDepl(true);
  }

  public void quitter() {
    try {
      dbWrk.deconnecter(); // ne pas oublier !!!
    } catch (MyDBException ex) {
      System.out.println(ex.getMessage());
    }
    Platform.exit();
  }

  /*
   * METHODES PRIVEES 
   */
  private void afficherPersonne(Personne p) {
    if (p != null) {
      txtPK.setText(String.valueOf(p.getPkPers()));
      txtPrenom.setText(p.getPrenom());
      txtNom.setText(p.getNom());
      dateNaissance.setValue(DateTimeLib.dateToLocalDate(p.getDateNaissance()));
      txtNo.setText(String.valueOf(p.getNoRue()));
      txtRue.setText(p.getRue());
      txtNPA.setText(String.valueOf(p.getNpa()));
      txtLocalite.setText(p.getLocalite());
      ckbActif.setText(String.valueOf(p.isActif()));
      txtSalaire.setText(String.valueOf(p.getSalaire()));
    }
  }

  private void ouvrirDB() {
    try {
      switch (DB_TYPE) {
        case MYSQL:
          dbWrk.connecterBdMySQL("223_personne_1table");
          break;
        case HSQLDB:
          dbWrk.connecterBdHSQLDB("../data" + File.separator + "223_personne_1table");
          break;
        case ACCESS:
          dbWrk.connecterBdAccess("../data" + File.separator + "223_Personne_1table.accdb");
          break;
        default:
          System.out.println("Base de données pas définie");
      }
      System.out.println("------- DB OK ----------");
      Personne p;
      p = manPers.setPersonnes(dbWrk.lirePersonnes());
      afficherPersonne(p);
    } catch (MyDBException ex) {
      JfxPopup.displayError("ERREUR", "Une erreur s'est produite", ex.getMessage());
      System.exit(1);
    }
  }
  
    private void rendreVisibleBoutonsDepl(boolean b) {
    btnDebut.setVisible(b);
    btnPrevious.setVisible(b);
    btnNext.setVisible(b);
    btnEnd.setVisible(b);
    btnAnnuler.setVisible(!b);
    btnSauver.setVisible(!b);
  }

  private void effacerContenuChamps() {
    txtNom.setText("");
    txtPrenom.setText("");
    txtPK.setText("0");
    txtNo.setText("0");
    txtRue.setText("");
    txtNPA.setText("0");
    txtLocalite.setText("");
    txtSalaire.setText("0.0");
    ckbActif.setSelected(false);
  }

}
