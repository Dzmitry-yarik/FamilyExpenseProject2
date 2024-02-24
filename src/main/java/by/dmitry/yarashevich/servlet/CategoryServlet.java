package by.dmitry.yarashevich.servlet;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;
import by.dmitry.yarashevich.services.ExpenseCategoryService;
import by.dmitry.yarashevich.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            req.setAttribute("categories", expenseCategoryService.readAllExpenseCategory());
            req.getRequestDispatcher("/pages/category/expense-category-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        req.setAttribute("users", userService.readAllUsers());
        List<ExpenseCategory> categories = expenseCategoryService.readAllExpenseCategory();
        req.setAttribute("categories", categories);

        if ("create-category".equals(action)) {
            req.getRequestDispatcher("/pages/category/expense-category-form.jsp").forward(req, resp);

        } else if ("save-category".equals(action)) {
            handleSaveCategoryAction(req, resp, categories);

        } else if ("edit-category".equals(action)) {
            handleEditCategoryAction(req, resp);

        } else if ("update-category".equals(action)) {
            handleUpdateCategoryAction(req, resp);

        } else if ("delete-category".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            expenseCategoryService.deleteCategory(categoryId);
            resp.sendRedirect("category?action=list");

        } else if ("user-categories".equals(action)) {
            handleUserCategoriesAction(req, resp);
        }
    }

    private void handleSaveCategoryAction(HttpServletRequest req, HttpServletResponse resp, List<ExpenseCategory> categories) throws IOException {
        String name = req.getParameter("name");
        ExpenseCategory newCategory = new ExpenseCategory(name);

        if (!categoryExists(name, categories)) {
            expenseCategoryService.createCategory(newCategory);
            resp.sendRedirect("category?action=list");
        } else {
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("Такая категория уже существует");
        }
    }

    private boolean categoryExists(String name, List<ExpenseCategory> categories) {
        return categories.stream().anyMatch(category -> name.equals(category.getName()));
    }

    private void handleEditCategoryAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int categoryId = Integer.parseInt(req.getParameter("id"));
        ExpenseCategory category = expenseCategoryService.getCategoryById(categoryId);
        req.setAttribute("category", category);
        req.getRequestDispatcher("/pages/category/expense-category-form.jsp").forward(req, resp);
    }

    private void handleUpdateCategoryAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int categoryId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        ExpenseCategory updatedCategory = new ExpenseCategory(categoryId, name);
        expenseCategoryService.updateCategory(updatedCategory);
        resp.sendRedirect("category?action=list");
    }

    private void handleUserCategoriesAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        User user = userService.getUserById(userId);
        Set<ExpenseRecord> recordsSet = user.getRecordSet();
        Set<ExpenseCategory> userCategories = extractCategoriesFromRecords(recordsSet);
        req.setAttribute("user", user);
        req.setAttribute("recordsSet", recordsSet);
        req.setAttribute("categories", userCategories);
        req.getRequestDispatcher("/pages/category/user-categories.jsp").forward(req, resp);
    }

    private Set<ExpenseCategory> extractCategoriesFromRecords(Set<ExpenseRecord> recordsSet) {
        Set<ExpenseCategory> categories = new HashSet<>();
        for (ExpenseRecord record : recordsSet) {
            categories.add(record.getCategory());
        }
        return categories;
    }
}