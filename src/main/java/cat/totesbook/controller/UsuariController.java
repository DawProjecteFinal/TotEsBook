/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.controller;

import cat.totesbook.service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jmiro
 */
@Controller
public class UsuariController {
    @Autowired
    private UsuariService usuariService;

    // mapping relatiu a l'arrel de l'aplicació: /TotEsBook/mostrarUsuaris
    @RequestMapping("/mostrarUsuaris")
    public ModelAndView mostrarUsuaris() {
        ModelAndView modelview = new ModelAndView("mostrarUsuaris");
        
        modelview.addObject("usuaris", usuariService.getAllUsuaris());
        return modelview;
    }
}
