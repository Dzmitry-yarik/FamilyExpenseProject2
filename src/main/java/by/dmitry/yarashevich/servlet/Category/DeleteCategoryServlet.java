package by.dmitry.yarashevich.servlet.Category;

import by.dmitry.yarashevich.services.ExpenseCategoryService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteCategoryServlet extends HttpServlet {

    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int categoryId = Integer.parseInt(req.getParameter("id"));
        expenseCategoryService.deleteCategory(categoryId);
        resp.sendRedirect("user?action=list");
    }
}