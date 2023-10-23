/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.busticketbooking.busticketbooking.controllers;

import com.busticketbooking.busticketbooking.dao.impl.DAO;
import com.busticketbooking.busticketbooking.models.Account;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 *
 * @author Admin
 */
@WebServlet(name = "EditProfile", urlPatterns = {"/profile"})
public class EditProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String phone = request.getParameter("phone");   
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String image = request.getParameter("image");
        String name = request.getParameter("name");
            
            DAO dao = new DAO();
            dao.update(phone, name, address, gender, image, email);
            HttpSession session = request.getSession();
            response.sendRedirect("index.jsp"); 
        }
    }
