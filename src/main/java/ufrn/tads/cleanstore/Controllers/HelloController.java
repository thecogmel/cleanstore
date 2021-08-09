package ufrn.tads.cleanstore.Controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {
    @GetMapping
    public void getHello(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String name = request.getParameter("nome");
         if (name != null) {
             response.getWriter().println("Bem vindo a clean-api, " + name + ".");   
         } else {
            response.getWriter().println("Bem vindo a clean-api, anonimo.");
         }
 
        /*  Connection connection = null;
         try {
             connection = ConectaBanco.getConnection();
         } catch (SQLException | URISyntaxException ex) {
             response.getWriter().println("Connection Failed! Check output console");
         }
 
         if (connection != null) {
             response.getWriter().println("A conexao com o banco foi realizada!");
         } else {
             response.getWriter().println("A conexao com o banco falhou!");
         }
 
         try {
             assert connection != null;
             connection.close();
         } catch (SQLException e) {
             e.printStackTrace();
         } */
    }
}
