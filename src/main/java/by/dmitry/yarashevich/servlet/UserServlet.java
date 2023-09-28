package by.dmitry.yarashevich.servlet;

import by.dmitry.yarashevich.models.User;
import by.dmitry.yarashevich.services.ExpenseCategoryService;
import by.dmitry.yarashevich.services.ExpenseRecordService;
import by.dmitry.yarashevich.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

// http://localhost:8080/user?action=list - для запуска приложения
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
    private final ExpenseRecordService expenseRecordService = new ExpenseRecordService();
    private String filePath;

    public UserServlet() {
        String classPath = UserServlet.class.getResource("").getPath();
        String s = classPath.replace("/servlet", "") + "numericData.txt";
        this.filePath = s.replace("target/FamilyExpenseProject/WEB-INF/classes", "src/main/java");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            req.setAttribute("users", userService.readAllUsers());
            req.setAttribute("initialAmount", readInitialAmountFromFile());
            req.getRequestDispatcher("/pages/user/user-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        req.setAttribute("categories", expenseCategoryService.readAllExpenseCategory());
        req.setAttribute("records", expenseRecordService.readAllExpenseRecord());

        if ("create".equals(action)) {
            req.getRequestDispatcher("/pages/user/user-form.jsp").forward(req, resp);

        } else if ("save".equals(action)) {
            handleSaveAction(req, resp);

        } else if ("edit".equals(action)) {
            handleEditAction(req, resp);

        } else if ("update".equals(action)) {
            handleUpdateAction(req, resp);

        } else if ("delete".equals(action)) {
            int userId = Integer.parseInt(req.getParameter("id"));
            userService.deleteUser(userId);
            resp.sendRedirect("user?action=list");

        } else if ("list-category".equals(action)) {
            req.getRequestDispatcher("/pages/category/expense-category-list.jsp").forward(req, resp);

        } else if ("list-record".equals(action)) {
            req.getRequestDispatcher("/pages/record/expense-record-list.jsp").forward(req, resp);

        } else if ("save_initial_amount".equals(action)) {
            int initialAmount = Integer.parseInt(req.getParameter("initialAmount"));
            saveInitialAmountToFile(initialAmount);
            resp.sendRedirect("user?action=list");

        } else if ("replenish_account".equals(action)) {
            handleReplenishAccount(req, resp);
        }
    }

    private void handleSaveAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User newUser = new User(name, password);

        boolean userExists = userService.readAllUsers().stream().anyMatch(user -> name.equals(user.getName()));

        if (!userExists) {
            userService.createUser(newUser);
        } else {
            PrintWriter writer = resp.getWriter();
            writer.println("Такой пользователь уже существует");
        }

        resp.sendRedirect("user?action=list");
    }

    private void handleEditAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        User user = userService.getUserById(userId);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/pages/user/user-form.jsp").forward(req, resp);
    }

    private void handleUpdateAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User updatedUser = new User(userId, name, password);
        userService.updateUser(updatedUser);
        resp.sendRedirect("user?action=list");
    }

    private void saveInitialAmountToFile(int initialAmount) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))){
            writer.print("initialAmount=" + initialAmount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readInitialAmountFromFile() {
        int initialAmount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("initialAmount")) {

                    String[] parts = line.split("=");

                    if (parts.length == 2) {
                        initialAmount = Integer.parseInt(parts[1].trim());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return initialAmount;
    }

    public void handleReplenishAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int replenishAmount = Integer.parseInt(req.getParameter("replenishAmount"));
        int currentInitialAmount = readInitialAmountFromFile();
        int newInitialAmount = currentInitialAmount + replenishAmount;
        saveInitialAmountToFile(newInitialAmount);
        resp.sendRedirect("user?action=list");
    }
}