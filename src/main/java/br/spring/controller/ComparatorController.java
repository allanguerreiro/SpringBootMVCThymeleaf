package br.spring.controller;

import br.spring.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

/**
 * Created by allan on 01/12/16.
 */
@Controller
public class ComparatorController {
    private final Logger log = LoggerFactory.getLogger(ComparatorController.class.getName());
    private final UserDao userDao;

    @Inject
    public ComparatorController(UserDao userDao) {
        this.userDao = userDao;
    }

    private Comparator<String> userName = (String name, String otherName) -> name.compareTo(otherName);

    @RequestMapping(value = "/comparator/ordering", method = RequestMethod.GET)
    public String orderedList(Model model) {
        List<String> names;
        try {
            names = userDao.getUserNames();
            names.sort(userName);
            model.addAttribute("names", names);
            return "pages/comparator/show";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }
}
