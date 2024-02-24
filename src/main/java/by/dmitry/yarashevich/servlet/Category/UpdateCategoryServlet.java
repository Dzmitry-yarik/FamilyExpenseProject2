package by.dmitry.yarashevich.servlet.Category;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.services.ExpenseCategoryService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UpdateCategoryServlet extends HttpServlet {

    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int categoryId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        ExpenseCategory updatedCategory = new ExpenseCategory(categoryId, name);
        expenseCategoryService.updateCategory(updatedCategory);
        resp.sendRedirect("category?action=list");
    }
}