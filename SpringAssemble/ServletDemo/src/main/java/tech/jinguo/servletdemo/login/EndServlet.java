package tech.jinguo.servletdemo.login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Chunsheng Zhang
 * @Date 2020/12/7
 * @Time 9:10
 */
public class EndServlet extends HttpServlet {

    /**
     * post请求方式【测试response对象】
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置服务器编码：GBK【支持少量中文】
//        response.setCharacterEncoding("GBK");   //设置服务器编码

        //设置服务器编码与浏览器解码：UTF-8【推荐使用】
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("doPost!");
        //设置响应头
//        response.setHeader("Content-Type","text/html;charset=UTF-8");

        //获取响应流，响应数据
        PrintWriter writer = response.getWriter();
        writer.write("login success!登录成功！");

        //重定向
//        response.sendRedirect("login_success.html");

    }

    /**
     * get请求方式[测试requset对象]
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        //测试获取请求参数
        String uname = request.getParameter("uname");
        System.out.println("uname = " + uname);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        String password = request.getParameter("password");
        System.out.println("password = " + password);

        //测试获取请求头
        String header = request.getHeader("User-Agent");
        System.out.println("header = " + header);

        //测试获取URL
        String scheme = request.getScheme();        //获取协议
        String serverName = request.getServerName();//获取服务器名称
        int serverPort = request.getServerPort();   //获取服务器端口
        String contextPath = request.getContextPath();  //获取上下文路径
        //  http://localhost:8080/day06_servlet/
        System.out.println(scheme+"://"+serverName+":"+serverPort+contextPath);

        //转发【登录成功、login_success.html】
        //1. 获取转发器
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login_success.html");
        //2. 执行转发
        requestDispatcher.forward(request,response);
    }

}
