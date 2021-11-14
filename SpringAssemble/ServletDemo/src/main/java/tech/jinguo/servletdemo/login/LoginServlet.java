package tech.jinguo.servletdemo.login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Chunsheng Zhang
 * @Date 2020/12/7
 * @Time 11:29
 */
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    /**
     * 处理请求，做出响应
     * 伪登录【无jdbc,假设数据库只有一个用户，用户名:cxk[蔡徐坤],密码123456】
     *      登录成功：转发跳转login_success.html
     *      登录失败：重定向回login.html
     *
     *  注意：默认Servlet在web目录下
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决post请求乱码【将服务器解码设置为：UTF-8。】
        request.setCharacterEncoding("UTF-8");
        //获取请求参数【username&pwd】
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username = " + username);
        if("cxk".equals(username) && "123456".equals(password)){
            //登录成功：转发跳转login_success.html
            request.getRequestDispatcher("/pages/login_success.html").forward(request,response);
        }else{
//            getServletContext().getContextPath()
            String contextPath = request.getContextPath();
            System.out.println("contextPath = " + contextPath);
            //登录失败：重定向回login.html
            response.sendRedirect(request.getContextPath()+"/pages/login.html");
        }
    }
}
