package com.example.myownlab2.Servlet;

import com.example.myownlab2.myTools.AreaChecker;
import com.example.myownlab2.myTools.Data;
import com.example.myownlab2.myTools.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "AreaCheckServlet", value = "/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {

    private static Storage storage;
    public static Storage getStorage(){
        return storage;
    }

    @Override
    public void init(){
        this.storage = new Storage();
    }

//    @Override
//    protected static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        LinkedList<Data> hits = storage.getList();
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedList<Data> hits = storage.getList();
        request.setAttribute("hitsFromServer",hits);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/table.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Double x = Double.parseDouble(request.getParameter("x"));
            Double y = Double.parseDouble(request.getParameter("y"));
            Double r = Double.parseDouble(request.getParameter("r"));
            if(!((1>r) || (r>5) || x<-3 || x>5 || y>5 || y<-5)){
                Data data = new Data();
                data.setR(r);
                data.setX(x);
                data.setY(y);
                data.setHitted(AreaChecker.wasHitted(x,y,r));
                storage.add(data);
                doGet(request,response);
            }
        }
        catch(NumberFormatException e){
            System.out.println("Пупупупупупупу, ты что дурак?");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
