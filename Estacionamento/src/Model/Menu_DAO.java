
package Model;

import View.Menu_GUI;
import static View.Menu_GUI.horarioe_txt;
import static View.Menu_GUI.marca_txt;
import static View.Menu_GUI.modelo_txt;
import static View.Menu_GUI.placa_txt;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Menu_DAO {
    static String placa;
    static String marca;
    static String modelo;
    static String horario;
    
    public static void salvar(){
        //início variáveis
        placa = placa_txt.getText(); // recebendo o nome
        marca = marca_txt.getText(); // recebendo o email
        modelo = modelo_txt.getText();
        //horario = horarioe_txt.getText();
        
        horario = "2024-09-23 14:30:00";
        
        String url = "jdbc:mysql://localhost/script"; // enderço do BD
        String username = "root";        //nome de um usuário de seu BD
        String password = "";  // senha do BD
 
        //fim variáveis
        
    Controller.Conecta_DB.carregaDriver();
      
     try { 
              
                  
              
           Connection con = null;
          
          
   try {
   con = (Connection) DriverManager.getConnection(url, username, password);
   } catch (SQLException ex) {
 
   Logger.getLogger(Menu_GUI.class.getName()).log(Level.SEVERE, null, ex);
 
          
                  }
 
           // Recebendo os dados a serem inseridos na tabela
           String sql = "INSERT INTO carro(MARCA, MODELO, PLACA, HORARIO_ENTRADA) values('"+marca+"','"+modelo+"','"+placa+"','"+horario+"')";
    
           try { // Tratamento de Erros para inserção
 
               // Criando varialvel que executara a inserção
               PreparedStatement inserir = (PreparedStatement) con.prepareStatement(sql);
               inserir.execute(); // Executando a inserção
 
JOptionPane.showMessageDialog(null,"\nInserção realizada com sucesso!!!\n","",-1);
               placa_txt.setText("");
               marca_txt.setText("");
               modelo_txt.setText("");
           } catch (Exception ex) {
              JOptionPane.showMessageDialog(null,"\nErro na inserção!","ERRO!",0);
           }
 
       }catch(NumberFormatException erro){
           // Tratamento de erro caso o usuario não digite o telefone corretamente
          JOptionPane.showMessageDialog(null,"Digite os dados corretamente","ERRO",0);
           marca_txt.setText("");
       }
 
      
             }
    
    }
