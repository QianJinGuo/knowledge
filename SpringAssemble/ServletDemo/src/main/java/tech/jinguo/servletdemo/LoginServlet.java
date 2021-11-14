package tech.jinguo.servletdemo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    /**
     * 处理请求，做出响应
     * 伪登录【无jdbc,假设数据库只有一个用户】
     * 登录成功：转发跳转login_success.html
     * 登录失败：重定向回login.html
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if ("hangman".equals(username) && "123456".equals(password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login_success.html");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/login.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
