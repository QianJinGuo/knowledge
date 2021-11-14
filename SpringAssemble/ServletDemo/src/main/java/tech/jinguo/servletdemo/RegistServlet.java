package tech.jinguo.servletdemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegistServlet", value = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    /**
     *  伪注册【无jdbc,假设数据库只有一个用户】
     *      注册成功：转发跳转regist_success.html
     *      注册失败：重定向回regist.html
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //响应
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        if ("hangman".equals(username)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/regist_success.html");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/regist.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
