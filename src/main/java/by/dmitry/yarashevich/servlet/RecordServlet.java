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
import java.util.List;


import java.time.LocalDate;

public class RecordServlet extends HttpServlet {

    private ExpenseRecordService expenseRecordService = new ExpenseRecordService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            List<ExpenseRecord> records = expenseRecordService.readAllExpenseRecord();
            req.setAttribute("records", records);
            req.getRequestDispatcher("/pages/record/expense-record-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        List<User> users = new UserService().readAllUsers();
        List<ExpenseCategory> categories = new ExpenseCategoryService().readAllExpenseCategory();
        req.setAttribute("users", users);
        req.setAttribute("categories", categories);

        if ("create-record".equals(action)) {
            req.getRequestDispatcher("/pages/record/expense-record-form.jsp").forward(req, resp);

        } else if ("save-record".equals(action)) {
            String name = req.getParameter("name");
            double amount = Double.parseDouble(req.getParameter("amount"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));

            UserService userService = new UserService();
            String userId = req.getParameter("userId");
            User user = userService.getUserById(Integer.parseInt(userId));

            ExpenseCategoryService categoryService = new ExpenseCategoryService();
            String categoryId = req.getParameter("categoryId");
            ExpenseCategory category = categoryService.getCategoryById(Integer.parseInt(categoryId));

            ExpenseRecord newRecord = new ExpenseRecord(name, amount, date, user, category);

            expenseRecordService.createRecord(newRecord);

            resp.sendRedirect("record?action=list");

        } else if ("edit-record".equals(action)) {
            int recordId = Integer.parseInt(req.getParameter("id"));
            ExpenseRecord record = expenseRecordService.getRecordById(recordId);
            req.setAttribute("record", record);
            req.getRequestDispatcher("/pages/record/expense-record-form.jsp").forward(req, resp);

        } else if ("update-record".equals(action)) {
            int recordId = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            double amount = Double.parseDouble(req.getParameter("amount"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));

            UserService userService = new UserService();
            String userId = req.getParameter("userId");
            User user = userService.getUserById(Integer.parseInt(userId));

            ExpenseCategoryService categoryService = new ExpenseCategoryService();
            String categoryId = req.getParameter("categoryId");
            ExpenseCategory category = categoryService.getCategoryById(Integer.parseInt(categoryId));

            ExpenseRecord newRecord = new ExpenseRecord(recordId, name, amount, date, user, category);

            expenseRecordService.updateRecord(newRecord);
            resp.sendRedirect("record?action=list");

        } else if ("delete-record".equals(action)) {
            int recordId = Integer.parseInt(req.getParameter("id"));
            expenseRecordService.deleteRecord(recordId);
            resp.sendRedirect("record?action=list");

        }
    }
}