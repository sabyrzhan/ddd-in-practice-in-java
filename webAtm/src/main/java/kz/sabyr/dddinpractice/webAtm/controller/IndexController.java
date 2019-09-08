package kz.sabyr.dddinpractice.webAtm.controller;

import kz.sabyr.dddinpractice.core.atms.domain.Atm;
import kz.sabyr.dddinpractice.core.atms.domain.repository.AtmRepository;
import kz.sabyr.dddinpractice.webAtm.controller.views.AtmView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private AtmRepository atmRepository;

    @GetMapping
    public String index(Model mv) {
        setCommon(mv);
        Optional<Atm> atmOptional = atmRepository.findById(1L);
        AtmView atmView = new AtmView(atmOptional.get());
        mv.addAttribute("atm", atmView);
        return "index";
    }

    private Model setCommon(Model model) {
        model.addAttribute("title", "Snack Machine");

        return model;
    }
}
