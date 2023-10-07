package com.lis.listest.filters;

import com.lis.listest.tools.Log;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 这是一个一级过滤器，不是拦截器，不能跳转
@Order(1)
@WebFilter(filterName = "FilterTest",urlPatterns = "/testproject/*")
public class FilterTest implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 操作1
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String url = httpServletRequest.getRequestURL().toString();
        Log.l("一级过滤器：接收到的url为：" + url);
        // 操作2
        if(url.equals("http://localhost:8080/")){
            //httpServletRequest.getRequestDispatcher("/sqltest").forward(request, response);
            // 虽然转发成功，但是因为返回的不是一个页面（@RestController）所以报错？
        }

        //到下一个Filter
        chain.doFilter(request, response);
    }
}
