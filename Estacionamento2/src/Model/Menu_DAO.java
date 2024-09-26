
package Model;

import View.Menu_GUI;
import static View.Menu_GUI.consultarHorarioEntrada_txt;
import static View.Menu_GUI.consultarMarca_txt;
import static View.Menu_GUI.consultarModelo_txt;
import static View.Menu_GUI.consultarPlaca_txt;
import static View.Menu_GUI.excluirPlaca_txt;
import static View.Menu_GUI.horarioe_txt;
import static View.Menu_GUI.horarios_txt;
import static View.Menu_GUI.marca_txt;
import static View.Menu_GUI.modelo_txt;
import static View.Menu_GUI.placa_txt;
import static View.Menu_GUI.tabela;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Menu_DAO {
    static String placa;
    static String marca;
    static String modelo;
    static String horario;
    static String url = "jdbc:mysql://localhost/script"; // enderço do BD
    static String username = "root";        //nome de um usuário de seu BD
    static String password = "";  // senha do BD
    
    public static void salvar(){
        //início variáveis
        placa = placa_txt.getText(); // recebendo o nome
        marca = marca_txt.getText(); // recebendo o email
        modelo = modelo_txt.getText();
        //horario = horarioe_txt.getText();
        
        horario = "2024-09-23 14:30:00";       
 
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
               refresh();
           } catch (Exception ex) {
              JOptionPane.showMessageDialog(null,"\nErro na inserção!","ERRO!",0);
           }
 
       }catch(NumberFormatException erro){
           // Tratamento de erro caso o usuario não digite o telefone corretamente
          JOptionPane.showMessageDialog(null,"Digite os dados corretamente","ERRO",0);
           marca_txt.setText("");
       }
 
      
             }
   public static void consultar(){
    try {
        // Obtendo a placa do campo de texto
        placa = consultarPlaca_txt.getText();
        
        try {
            // Estabelecendo a conexão
            Connection con = null;
            try {
                con = (Connection) DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(Menu_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Usando PreparedStatement para evitar erros de sintaxe
            String sql = "SELECT marca, modelo, horario_entrada FROM carro WHERE placa = ?";
            PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
            pstmt.setString(1, placa); // Atribui o valor da placa ao primeiro "?"

            // Executando a consulta
            ResultSet rs = pstmt.executeQuery();
            int i = 0;

            while (rs.next()) {
                marca = rs.getString("marca");
                modelo = rs.getString("modelo");
                String horarioEntrada = rs.getString("horario_entrada");

                i++;
                //System.out.println("Marca: " + marca + "\nModelo: " + modelo);
                //JOptionPane.showMessageDialog(null, "Marca: " + marca + "\nModelo: " + modelo);

                View.Menu_GUI.consultarMarca_txt.setText(String.valueOf(marca));
                View.Menu_GUI.consultarModelo_txt.setText(String.valueOf(modelo));
                View.Menu_GUI.consultarHorarioEntrada_txt.setText(String.valueOf(horarioEntrada));
            }

            if (i == 0) {
                JOptionPane.showMessageDialog(null, "Dado não cadastrado", "Resultado", -1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor", "ERRO!", 0);
        }

    } catch (NumberFormatException erro) {
        JOptionPane.showMessageDialog(null, "Digite o código corretamente", "ERRO", 0);
        consultarPlaca_txt.setText("");
        }
    }
    public static void alterar() {
    // Obtendo os dados dos campos de texto
    placa = consultarPlaca_txt.getText();       
    marca = consultarMarca_txt.getText(); // recebendo o nome
    modelo = consultarModelo_txt.getText(); // recebendo o email         
    String horarioEntrada = consultarHorarioEntrada_txt.getText();// recebendo o telefone

    try {
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(Menu_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Usando PreparedStatement para a atualização de dados
        String sql = "UPDATE carro SET marca = ?, modelo = ?, horario_entrada = ? WHERE placa = ?";
        try {
            PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);

            // Definindo os valores nos parâmetros
            pstmt.setString(1, marca);
            pstmt.setString(2, modelo);
            pstmt.setString(3, horarioEntrada);
            pstmt.setString(4, placa);

            // Executando a atualização
            int rowsAffected = pstmt.executeUpdate(); // Retorna o número de linhas afetadas

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Atualização realizada com sucesso!", "", -1);
                consultarPlaca_txt.setText("");
                consultarMarca_txt.setText("");
                consultarModelo_txt.setText("");
                consultarHorarioEntrada_txt.setText("");
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma linha foi atualizada. Verifique se a placa existe.", "Atenção", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace(); // Isso ajudará a depurar o erro real
            JOptionPane.showMessageDialog(null, "Erro na inserção: " + ex.getMessage(), "ERRO!", 0);
        }

    } catch (NumberFormatException erro) {
        // Tratamento de erro caso o usuário não digite os dados corretamente
        JOptionPane.showMessageDialog(null, "Digite os dados corretamente", "ERRO", 0);
    }
    // fim da função alterar
    }
    
    public static void excluir() {
    // Obtendo a placa e o horário de saída (ajustado para TIME)
    placa = excluirPlaca_txt.getText();
    horario = horarios_txt.getText();  // Horário de saída fornecido pelo usuário
    
    try {
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(Menu_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Buscando o horário de entrada com base na placa
        String sqlBuscaHorario = "SELECT horario_entrada FROM carro WHERE placa = ?";
        PreparedStatement pstmtBuscaHorario = (PreparedStatement) con.prepareStatement(sqlBuscaHorario);
        pstmtBuscaHorario.setString(1, placa);

        ResultSet rs = pstmtBuscaHorario.executeQuery();
        if (rs.next()) {
            // Obtendo o horário de entrada no formato TIME
            String horarioEntrada = rs.getString("horario_entrada");

            // Formatando para TIME (HH:mm:ss)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Validando o horário de saída
            LocalTime saida;
            try {
                // Convertendo o horário de saída inserido pelo usuário para LocalTime
                saida = LocalTime.parse(horario, formatter);
            } catch (DateTimeParseException e) {
                // Exibe uma mensagem caso o formato do horário esteja incorreto
                JOptionPane.showMessageDialog(null, "Erro: O horário de saída deve estar no formato HH:mm:ss", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;  // Encerra a função sem continuar
            }

            // Convertendo o horário de entrada para LocalTime (apenas horas/minutos/segundos)
            LocalTime entrada = LocalTime.parse(horarioEntrada, formatter);

            // Calculando a diferença em horas e minutos
            long horasPassadas = ChronoUnit.HOURS.between(entrada, saida);
            long minutosPassados = ChronoUnit.MINUTES.between(entrada, saida) % 60;

            // Arredonda para cima se houver minutos extras
            if (minutosPassados > 0) {
                horasPassadas++;
            }

            // Calculando o valor total (R$15 por hora)
            double valorTotal = horasPassadas * 15;
            JOptionPane.showMessageDialog(null, "Horas: " + horasPassadas + 
                    "\nValor a pagar: R$ " + valorTotal + 
                    "\n O registro do carro de placa: " + placa + " será removido do sistema.");

            // Agora, excluindo o registro do carro
            String sqlExcluir = "DELETE FROM carro WHERE placa = ?";
            PreparedStatement pstmtExcluir = (PreparedStatement) con.prepareStatement(sqlExcluir);
            pstmtExcluir.setString(1, placa);
            pstmtExcluir.execute();

            JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!", "", -1);
            excluirPlaca_txt.setText("");
            horarios_txt.setText("");
            refresh();

        } else {
            JOptionPane.showMessageDialog(null, "Placa não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage(), "ERRO!", 0);
    }
}

    public static void interroga(){
        JOptionPane.showMessageDialog(null,"Por favor, para o formato das horas, escreva dessa maneira: \n HH:mm:ss");
    }
    
    
     

public static void refresh() {
    Controller.Conecta_DB.carregaDriver();
    try {
        // Conectando ao banco de dados
        Connection conn;
        conn = (Connection) DriverManager.getConnection(url, username, password);

        // Consulta SQL para selecionar todos os registros da tabela CARRO
        String sql = "SELECT * FROM CARRO;";
        PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Obtendo o modelo da tabela
        DefaultTableModel model = new DefaultTableModel();
        tabela.setModel(model);

        // Obtendo os metadados para capturar o número de colunas
        ResultSetMetaData rsMeta = rs.getMetaData();
        int columnCount = rsMeta.getColumnCount();

        // Adicionando os nomes das colunas ao modelo
        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(rsMeta.getColumnName(i));
        }

        // Adicionando as linhas de dados ao modelo
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            model.addRow(row);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}


       

    //--- Fim Atualiza

    

    
    
}
