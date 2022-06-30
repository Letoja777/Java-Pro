package academy.prog;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    static final String LOGIN = "admin";
    //static final String PASS = "admin";

    private boolean check(String s){
        return s.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_.,;:?+=]).{10,})");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        char [] arr_login = login.toCharArray();

        String age = request.getParameter("age");
        char [] arr_age = age.toCharArray();

        int intAge = Integer.parseInt(age);

        if (arr_login.length > 0 && check(password) && intAge > 18) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user_login", login);
            session.setAttribute("user_age", intAge);
            response.sendRedirect("index.jsp");
        } else if (arr_login.length > 0 && !check(password) && intAge > 18) {
            PrintWriter pw = response.getWriter();
            pw.println("<html><head><title>Prog.kiev.ua Test</title></head>");
            pw.println("<body><h1>Entered password is uncorrect!</h1></body></html>");
            pw.println("<br>Click this link to <a href=\"/login?a=exit\">logout</a>");
        } else if (arr_login.length > 0 && check(password) && intAge < 18) {
            PrintWriter pw = response.getWriter();
            pw.println("<html><head><title>Prog.kiev.ua Test</title></head>");
            pw.println("<body><h1>Your age less than 18! </h1></body></html>");
            pw.println("<br>Click this link to <a href=\"/login?a=exit\">logout</a>");
        } else {
            PrintWriter pw = response.getWriter();
            pw.println("<html><head><title>Prog.kiev.ua Test</title></head>");
            pw.println("<body><h1>Uncorrect login or password</h1></body></html>");
            pw.println("<br>Click this link to <a href=\"/login?a=exit\">logout</a>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null)) {
            session.removeAttribute("user_login");
            session.removeAttribute("user_age");
            //session.removeAttribute("user_pass");
        }

        response.sendRedirect("index.jsp");
    }
}
