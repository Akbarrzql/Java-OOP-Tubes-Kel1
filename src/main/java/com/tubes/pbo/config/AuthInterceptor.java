package com.tubes.pbo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request == null || response == null) {
            return true;
        }
        String path = request.getRequestURI();

        if (path.startsWith("/admin/")) {
            return hasAdminRole(request, response);
        }

        if (isTravelerPage(path)) {
            return hasAnyRole(request, response);
        }

        return true;
    }

    private boolean isTravelerPage(String path) {
        return path.equals("/dashboard")
                || path.startsWith("/itinerary")
                || path.equals("/explore")
                || path.equals("/profile");
    }

    private boolean hasAnyRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userRole") != null) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }

    private boolean hasAdminRole(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        String role = session == null ? null : (String) session.getAttribute("userRole");

        if ("ADMIN".equals(role)) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }
}

