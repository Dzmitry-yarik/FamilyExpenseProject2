package by.dmitry.yarashevich.servlet.Category;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.services.ExpenseCategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EditCategoryServlet extends HttpServlet {

    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int categoryId = Integer.parseInt(req.getParameter("id"));
        ExpenseCategory category = expenseCategoryService.getCategoryById(categoryId);
        req.setAttribute("category", category);
        resp.sendRedirect("user?action=list");
    }

}