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
import java.time.LocalDate;

public class RecordServlet extends HttpServlet {

    private final ExpenseRecordService expenseRecordService = new ExpenseRecordService();
    private final UserService userService = new UserService();
    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            req.setAttribute("records", expenseRecordService.readAllExpenseRecord());
            req.getRequestDispatcher("/pages/record/expense-record-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        req.setAttribute("users", userService.readAllUsers());
        req.setAttribute("categories", expenseCategoryService.readAllExpenseCategory());

        if ("create-record".equals(action)) {
            req.getRequestDispatcher("/pages/record/expense-record-form.jsp").forward(req, resp);

        } else if ("save-record".equals(action)) {
            if (validateParameters(req, resp)) {
                handleSaveRecordAction(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Go back and fill in all the fields of the form");
            }

        } else if ("edit-record".equals(action)) {
            handleEditRecordAction(req, resp);

        } else if ("update-record".equals(action)) {
            if (validateParameters(req, resp)) {
                handleUpdateRecordAction(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Go back and fill in all the fields of the form");
            }

        } else if ("delete-record".equals(action)) {
            int recordId = Integer.parseInt(req.getParameter("id"));
            expenseRecordService.deleteRecord(recordId);
            resp.sendRedirect("record?action=list");
        }
    }

    private void handleSaveRecordAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExpenseRecord newRecord = buildRecordFromRequest(req);
        expenseRecordService.createRecord(newRecord);
        resp.sendRedirect("record?action=list");
    }

    private void handleEditRecordAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordId = Integer.parseInt(req.getParameter("id"));
        ExpenseRecord record = expenseRecordService.getRecordById(recordId);
        req.setAttribute("record", record);
        req.getRequestDispatcher("/pages/record/expense-record-form.jsp").forward(req, resp);
    }

    private void handleUpdateRecordAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ExpenseRecord updatedRecord = buildRecordFromRequest(req);
        expenseRecordService.updateRecord(updatedRecord);
        resp.sendRedirect("record?action=list");
    }

    /**
     This method converts the request parameters to an ExpenseRecord object when saving and updating records.
     */
    private ExpenseRecord buildRecordFromRequest(HttpServletRequest req) {
        String idString = req.getParameter("id");
        int recordId = (idString == null || idString.isEmpty()) ? 0 : Integer.parseInt(idString);
        String name = req.getParameter("name");
        double amount = Double.parseDouble(req.getParameter("amount"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        User user = userService.getUserById(userId);
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        ExpenseCategory category = expenseCategoryService.getCategoryById(categoryId);
        return new ExpenseRecord(recordId, name, amount, date, user, category);
    }

    private boolean validateParameters(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String amount = req.getParameter("amount");
        String date = req.getParameter("date");
        String userId = req.getParameter("userId");
        String categoryId = req.getParameter("categoryId");

        return name != null && !name.isEmpty() &&
                amount != null && !amount.isEmpty() &&
                date != null && !date.isEmpty() &&
                userId != null && !userId.isEmpty() &&
                categoryId != null && !categoryId.isEmpty();
    }
}