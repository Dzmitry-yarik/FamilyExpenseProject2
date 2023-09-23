package by.dmitry.yarashevich.servlet;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;
import by.dmitry.yarashevich.services.ExpenseCategoryService;
import by.dmitry.yarashevich.services.ExpenseRecordService;
import by.dmitry.yarashevich.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            List<User> users = userService.readAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/pages/user/user-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        List<ExpenseCategory> categories = new ExpenseCategoryService().readAllExpenseCategory();
        req.setAttribute("categories", categories);
        List<ExpenseRecord> records = new ExpenseRecordService().readAllExpenseRecord();
        req.setAttribute("records", records);

        if ("create".equals(action)) {
            req.getRequestDispatcher("/pages/user/user-form.jsp").forward(req, resp);

        } else if ("save".equals(action)) {

            String name = req.getParameter("name");
            String password = req.getParameter("password");
            User newUser = new User(name, password);

            List<User> users = new UserService().readAllUsers();
            boolean userExists = false;

            for (User user : users) {
                if (name.equals(user.getName())) {
                    userExists = true;
                    break;
                }
            }

            if (!userExists) {
                userService.createUser(newUser);
            } else {
                PrintWriter writer = resp.getWriter();
                writer.println("Такой пользователь уже существует");
            }

            resp.sendRedirect("user?action=list");

        } else if ("edit".equals(action)) {
            int userId = Integer.parseInt(req.getParameter("id"));
            User user = userService.getUserById(userId);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/pages/user/user-form.jsp").forward(req, resp);

        } else if ("update".equals(action)) {
            int userId = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            User updatedUser = new User(userId, name, password);
            userService.updateUser(updatedUser);
            resp.sendRedirect("user?action=list");

        } else if ("delete".equals(action)) {
            int userId = Integer.parseInt(req.getParameter("id"));
            userService.deleteUser(userId);
            resp.sendRedirect("user?action=list");

        }  else if ("list-category".equals(action)) {
            req.getRequestDispatcher("/pages/category/expense-category-list.jsp").forward(req, resp);

        } else if ("list-record".equals(action)) {
            req.getRequestDispatcher("/pages/record/expense-record-list.jsp").forward(req, resp);
        }
    }
}
