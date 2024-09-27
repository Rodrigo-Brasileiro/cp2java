package Model;

import View.Login_GUI;
import View.Splash_GUI;
import static View.Splash_GUI.barra;
import static View.Splash_GUI.msg_txt;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Splash_DAO {
    public static void splash(Splash_GUI menuFrame){
         //----------Inicio Splash
        
                new Thread(){
        
            public void run() {
        
        
        for (int i=0; i<101; i++){
                    try {
                        sleep(60); 
                        
                        barra.setValue(i);
                        
                        if(barra.getValue() == 10){
                        
                            msg_txt.setText("Fazendo a conexao com o banco de dados");
                            sleep(2000);
                                                       
                        
                        }else if (barra.getValue() <=30){
                            msg_txt.setText("Carregando o sistema");
                          sleep(100);  
                        } else if (barra.getValue() <=99){
                           msg_txt.setText("Carregamento quase completo");
                            
                        }else{
                           msg_txt.setText("Carregamento completo. Seu programa sera iniciado.");
                        
                        sleep(3000);
                        
                        new Login_GUI().setVisible(true);
                        menuFrame.dispose();
                        }
                        
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Splash_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }        
        }
        
        }.start();
        //----------Fim Splash
    }

    
}
