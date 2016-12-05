package br.spring.controller;

import br.spring.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by allan on 01/12/16.
 */
@Controller
public class LambdaController {
    private final Logger log = LoggerFactory.getLogger(LambdaController.class.getName());
    private final UserDao userDao;

    @Inject
    public LambdaController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/lambda/expression", method = RequestMethod.GET)
    public String expression(Model model) {
        List<String> names;
        try {
            names = userDao.getUserNames();

            // Antes do Java 8:
            for (String name : names) {
                System.out.println(name);
            }

            //   Como fica o código com uso de Lambda no Java 8:
            names.forEach(name -> System.out.println(name));

            model.addAttribute("names", names);
            return "pages/lambda/show";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }
}
