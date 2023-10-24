///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package com.busticketbooking.busticketbooking.controllers;
//
//
//
//import com.busticketbooking.busticketbooking.dao.impl.DAO;
//import com.busticketbooking.busticketbooking.models.Account;
//import com.busticketbooking.busticketbooking.dao.SendOtpSMS;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import javax.servlet.http.HttpSession;
//
///**
// *
// * @author Admin
// */
//@WebServlet(name = "SignUpControl", urlPatterns = {"/SignUpControl"})
//public class SignUpControl extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        String repassword = request.getParameter("re-password");
//        
//        SendEmail sm = new SendEmail();
//        String code = sm.getRandom();
//        
//        Account account = new Account(0, code, password, code, email, code, email, code, name, code);
//        boolean test = sm.sendEmail(account);
//        
//
//        if (!password.equals(repassword)){
//            request.setAttribute("mess3","Mật khẩu không trùng nhau" );
//            response.sendRedirect("login.jsp");
//        }else{
//            DAO dao = new DAO();
//            Account a =dao.checkExistAccount(email);
//            if (a == null){
//                if (test){
//                    HttpSession session = request.getSession();
//                    session.setAttribute("authcode", account);
//                    response.sendRedirect("verify.jsp"); 
//                }
//            }else{
//               request.setAttribute("mess2","Địa chỉ Email đã tồn tại" );
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//            }
//        }
//        }
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
