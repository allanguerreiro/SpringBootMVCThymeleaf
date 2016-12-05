package br.spring.controller;

import br.spring.model.User;
import br.spring.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by allan on 01/12/16.
 */
@Controller
public class StreamController {
    private final Logger log = LoggerFactory.getLogger(StreamController.class.getName());
    private final UserDao userDao;

    @Autowired
    public StreamController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/stream/filter", method = RequestMethod.GET)
    public String filter(Model model) {
        Set<User> users;
        try {
            users = userDao.findAllUsers();
            Stream<User> userStream = users.stream();
            users = userStream.filter(user -> user.getName().startsWith("A")).collect(Collectors.toSet());
            model.addAttribute("users", users);
            return "pages/stream/filter";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }

    @RequestMapping(value = "/stream/groupingBy", method = RequestMethod.GET)
    public String groupingBy(Model model) {
        List<User> users;
        try {
            users = userDao.findAll();
            Map<String, List<User>> maps = users.stream().collect(Collectors.groupingBy(User::getCity));
            maps.get("Oklahoma City");
            model.addAttribute("maps", maps);
            return "pages/stream/groupingBy";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }

    @RequestMapping(value = "/stream/map", method = RequestMethod.GET)
    public String map(Model model) {
        List<String> users;
        try {
            users = userDao.getUserNames();
            Stream<String> stringStream = users.stream();
            Stream<String> names = stringStream.map(name -> name.toUpperCase());
            model.addAttribute("names", names);
            return "pages/stream/map";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }
}
