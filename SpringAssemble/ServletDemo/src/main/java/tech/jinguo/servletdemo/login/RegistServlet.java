package tech.jinguo.servletdemo.login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Chunsheng Zhang
 * @Date 2020/12/7
 * @Time 11:40
 */
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    /**
     * 处理注册请求
     *
     * 伪注册【无jdbc,假设数据库只有一个用户，用户名:cxk,密码123456】
     *      *      注册成功：转发跳转regist_success.html
     *      *      注册失败：重定向回regist.html
     *      *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //POST
        request.setCharacterEncoding("UTF-8");
        //响应
        response.setContentType("text/html;charset=UTF-8");
        //用户名不是cxk,就成功
        //获取请求参数【username】
        String username = request.getParameter("username");
        if("cxk".equals(username)){
            //注册失败：重定向回regist.html
            response.sendRedirect(request.getContextPath()+"/pages/regist.html");
        }else{
            //注册成功：转发跳转regist_success.html
            request.getRequestDispatcher("/pages/regist_success.html").forward(request,response);
        }


    }
}
