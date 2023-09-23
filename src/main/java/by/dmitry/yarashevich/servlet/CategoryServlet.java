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
import java.util.Set;

//@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            List<ExpenseCategory> categories = expenseCategoryService.readAllExpenseCategory();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/pages/category/expense-category-list.jsp").forward(req, resp);
        }
    }

    private User getUserById(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        UserService userService = new UserService();
        return userService.getUserById(Integer.parseInt(userId));
    }

//    private ExpenseCategory getCategoryById(HttpServletRequest req) {
//        int categoryId = Integer.parseInt(req.getParameter("id"));
//        return expenseCategoryService.getCategoryById(categoryId);
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        List<User> users = new UserService().readAllUsers();
        List<ExpenseCategory> categories = new ExpenseCategoryService().readAllExpenseCategory();
        List<ExpenseRecord> records = new ExpenseRecordService().readAllExpenseRecord();
        req.setAttribute("users", users);
        req.setAttribute("categories", categories);
        req.setAttribute("records", records);

//        List<ExpenseCategory> categories = new ExpenseCategoryService().readAllExpenseCategory();
//        req.setAttribute("categories", categories);

        if ("create-category".equals(action)) {
            req.getRequestDispatcher("/pages/category/expense-category-form.jsp").forward(req, resp);

        } else if ("save-category".equals(action)) {
            User user = getUserById(req);
            String name = req.getParameter("name");
            ExpenseCategory newCategory = new ExpenseCategory(name, user);
            boolean userExists = false;

//            List<ExpenseCategory> categories = new ExpenseCategoryService().readAllExpenseCategory();
            for (ExpenseCategory category : categories) {
                if (name.equals(category.getName())) {
                    userExists = true;
                    break;
                }
            }

            if (!userExists) {
                expenseCategoryService.createCategory(newCategory);
            } else {
                PrintWriter writer = resp.getWriter();
                writer.println("Такая категория уже существует");
            }

            resp.sendRedirect("category?action=list");

        } else if ("edit-category".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            ExpenseCategory category = expenseCategoryService.getCategoryById(categoryId);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/pages/category/expense-category-form.jsp").forward(req, resp);

        } else if ("update-category".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            User user = getUserById(req);

            ExpenseCategory newCategory = new ExpenseCategory(categoryId, name, user);

            expenseCategoryService.updateCategory(newCategory);
            resp.sendRedirect("category?action=list");

        } else if ("delete-category".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            expenseCategoryService.deleteCategory(categoryId);
            resp.sendRedirect("category?action=list");

        } else if ("user_categories".equals(action)) {
            User user = getUserById(req);
            Set<ExpenseCategory> categoriesSet = user.getCategorySet();
            Set<ExpenseRecord> recordsSet = user.getRecordSet();
            req.setAttribute("user", user);
            req.setAttribute("categoriesSet", categoriesSet);
            req.setAttribute("recordsSet", recordsSet);
            req.getRequestDispatcher("/pages/category/user_categories.jsp").forward(req, resp);
        }
    }
}

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("action");

//        List<User> users = new UserService().readAllUsers();
//        req.setAttribute("users", users);
//
//        if ("create-category".equals(action)) {
//            req.getRequestDispatcher("/pages/category/expense-category-form.jsp").forward(req, resp);
//
//        } else if ("save-category".equals(action)) {
//            String name = req.getParameter("name");
////            UserService userService = new UserService();
////            String userId = req.getParameter("userId");
////            User user = userService.getUserById(Integer.parseInt(userId));
//            ExpenseCategory newCategory = new ExpenseCategory(name, user);
//
//            List<ExpenseCategory> categories = new ExpenseCategoryService().readAllExpenseCategory();
//            boolean userExists = false;
//
//            for (ExpenseCategory category : categories) {
//                if (name.equals(category.getName())) {
//                    userExists = true;
//                    break;
//                }
//            }
//
//            if (!userExists) {
//                expenseCategoryService.createCategory(newCategory);
//            } else {
//                PrintWriter writer = resp.getWriter();
//                writer.println("Такая категория уже существует");
//            }
//
//            resp.sendRedirect("category?action=list");
//
//        } else if ("edit-category".equals(action)) {
////            int categoryId = Integer.parseInt(req.getParameter("id"));
//            ExpenseCategory category = expenseCategoryService.getCategoryById(categoryId);
//            req.setAttribute("category", category);
//            req.getRequestDispatcher("/pages/category/expense-category-form.jsp").forward(req, resp);
//
//        } else if ("update-category".equals(action)) {
////            int categoryId = Integer.parseInt(req.getParameter("id"));
//            String name = req.getParameter("name");
//
////            UserService userService = new UserService();
////            String userId = req.getParameter("userId");
////            User user = userService.getUserById(Integer.parseInt(userId));
//            ExpenseCategory newCategory = new ExpenseCategory(categoryId, name, user);
//
//            expenseCategoryService.updateCategory(newCategory);
//            resp.sendRedirect("category?action=list");
//
//        } else if ("delete-category".equals(action)) {
////            int categoryId = Integer.parseInt(req.getParameter("id"));
//            expenseCategoryService.deleteCategory(categoryId);
//            resp.sendRedirect("category?action=list");
//
//        } else if ("user_categories".equals(action)) {
////            String userId = req.getParameter("userId");
////            UserService userService = new UserService();
////            User selectedUser = userService.getUserById(Integer.parseInt(userId));
//
//            req.setAttribute("user", user);
//            req.getRequestDispatcher("/pages/user/user_categories.jsp").forward(req, resp);
//        }
//    }

