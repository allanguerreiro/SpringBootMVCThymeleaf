package br.spring.controller;

import br.spring.model.Employee;
import br.spring.persistence.EmployeeDao;
import br.spring.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 02/12/16.
 */
@Controller
public class OptionalController {

    private final Logger log = LoggerFactory.getLogger(OptionalController.class.getName());
    private final UserDao userDao;
    private final EmployeeDao employeeDao;

    @Autowired
    public OptionalController(UserDao userDao, EmployeeDao employeeDao) {
        this.userDao = userDao;
        this.employeeDao = employeeDao;
    }

    @RequestMapping(value = "/optional/isPresent", method = RequestMethod.GET)
    public String isPresent(Model model) {
        List<Employee> employees;
        List<Employee> newEmployees;
        try {
            employees = employeeDao.findAll();
            newEmployees = new ArrayList();
            employees.forEach(employee -> {
                employee.getUserOptional()
                        .ofNullable(employee.getUser())
                        .ifPresent(userOptional -> newEmployees.add(employee));
            });
            model.addAttribute("newEmployees", newEmployees);
            return "pages/optional/show";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }
}
