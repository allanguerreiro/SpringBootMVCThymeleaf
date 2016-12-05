package br.spring.controller;

import br.spring.model.User;
import br.spring.persistence.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by allan on 30/11/16.
 */
@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class.getName());
    private final UserDao userDao;

    @Inject
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/user/new")
    public String newUser(Model model) {
        try {
            model.addAttribute("user", new User());
            return "pages/user/create";
        } catch (Exception ex) {
            log.error("Erro ao criar usuário" + ex.getStackTrace());
        }
        return null;
    }

    @RequestMapping("/user/{id}")
    public String show(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("user", userDao.findOne(id));
            return "pages/user/show";
        } catch (Exception ex) {
            log.error("Erro ao exibir" + ex.getStackTrace());
        }
        return null;
    }

    @RequestMapping(value = "/user/users", method = RequestMethod.GET)
    public String list(Model model) {
        try {
            model.addAttribute("users", userDao.findAll());
            return "pages/user/users";
        } catch (Exception ex) {
            log.error("Erro ao listar" + ex.getStackTrace());
        }
        return null;
    }

    /**
     * /delete  --> Delete the user having the passed id.
     *
     * @param id The id of the user to delete
     * @return A string describing if the user is succesfully deleted or not.
     */
    @Transactional
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            log.info("Preparando para apagar o usuário: " + id);
            userDao.delete(id);
            log.info("Usuário: " + id + " - Removido com sucesso");
        } catch (Exception ex) {
            log.error("Erro ao remover usuário");
            return "Error deleting the user: " + ex.toString();
        }
        return "pages/user/users";
    }

    /**
     * /get-by-email  --> Return the id for the user having the passed email.
     *
     * @param email The email to search in the database.
     * @return The user id or a message error if the user is not found.
     */
    @RequestMapping("/user/get-by-email/{email:.+}")
    public String getByEmail(@PathVariable String email) {
        Long userId;
        try {
            log.info("Consultando usuário por email: " + email);
            User user = userDao.findByEmail(email);
            userId = user.getId();
        } catch (Exception ex) {
            log.error("Erro ao consultar usuário por email ");
            return "User not found";
        }
        return "redirect:/user/" + userId;
    }

    /**
     * /update  --> Update the email and the name for the user in the database
     * having the passed id.
     *
     * @param id The id for the user to update.
     * @return A string describing if the user is succesfully updated or not.
     */
    @Transactional
    @RequestMapping("/user/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        User user;
        try {
            log.info("Preparando para atualizar usuário por id: " + id);
            user = userDao.findOne(id);
            model.addAttribute("user", user);
        } catch (Exception ex) {
            log.error("Erro ao atualizar" + ex.getStackTrace());
        }
        return "pages/user/create";
    }

    @Transactional
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String save(User user) {
        try {
            userDao.saveAndFlush(user);
            return "redirect:/user/" + user.getId();
        } catch (Exception ex) {
            log.error("Erro ao salvar" + ex.getStackTrace());
        }
        return null;
    }
}
