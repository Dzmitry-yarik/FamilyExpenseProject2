package by.dmitry.yarashevich.servlet.Category;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;
import by.dmitry.yarashevich.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UserCategoriesServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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