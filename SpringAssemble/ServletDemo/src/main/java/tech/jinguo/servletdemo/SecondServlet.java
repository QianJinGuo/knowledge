package tech.jinguo.servletdemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SecondServlet", value = "/SecondServlet")
public class SecondServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        //通过PrintWriter对象向浏览器端发送响应信息
//        PrintWriter printWriter = response.getWriter();
//        printWriter.write(request.getAttribute("attrName").toString());
//        printWriter.close();

        // RequestDispatcher dispatcher = request.getRequestDispatcher("success.html");
        // dispatcher.forward(request, response);//发起转发

        //注意路径问题，加上/会失败，会以主机地址为起始，重定向一般需要加上项目名
        response.sendRedirect(request.getContextPath() + "/pages/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
