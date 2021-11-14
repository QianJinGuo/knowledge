package tech.jinguo.servletdemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(name = "FirstServlet", value = "/FirstServlet")
public class FirstServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("servlet init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(MessageFormat.format("contextPath:{0},serverPort:{1},serverName:{2},schema:{3},servletPath:{4}",
                request.getContextPath(), request.getServerPort(), request.getServerName(), request.getScheme(), request.getServletPath()));
        System.out.printf("Agent:%s,refer:%s%n", request.getHeader("User-Agent"), Optional.ofNullable(request.getHeader("Referer")).orElse("没有带RefererHeader"));
        String[] values = request.getParameterValues("footballTeam");
        if (values != null && values.length > 0) {
            Arrays.stream(values).forEach(System.out::println);
        }
        //获取请求转发对象
        //RequestDispatcher dispatcher = request.getRequestDispatcher("success.html");
        //dispatcher.forward(request, response);//发起转发

        //将数据保存到request对象的属性域中
        request.setAttribute("attrName", "attrValueInRequest");
        // 两个Servlet要想共享request对象中的数据，必须是转发的关系
        request.getRequestDispatcher("/SecondServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("servlet has been destroyed");
    }
}
