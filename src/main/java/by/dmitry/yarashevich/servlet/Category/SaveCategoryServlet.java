package by.dmitry.yarashevich.servlet.Category;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.services.ExpenseCategoryService;
import by.dmitry.yarashevich.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SaveCategoryServlet extends HttpServlet {

    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("users", userService.readAllUsers());
        List<ExpenseCategory> categories = expenseCategoryService.readAllExpenseCategory();
        req.setAttribute("categories", categories);
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
}
