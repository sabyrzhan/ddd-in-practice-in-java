package kz.sabyr.dddinpractice.web.controller;

import kz.sabyr.dddinpractice.core.snackmachine.domain.SnackMachine;
import kz.sabyr.dddinpractice.core.snackmachine.domain.repository.SnackMachineRepository;
import kz.sabyr.dddinpractice.web.controller.views.SnackPileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private SnackMachineRepository snackMachineRepository;

    @GetMapping
    public String index(Model mv) {
        setCommon(mv);
        SnackMachine snackMachine = snackMachineRepository.findById(1L).orElse(null);
        List<SnackPileView> allSnackPiles = snackMachine.getAllSnackPiles().stream().map(s -> new SnackPileView(s)).collect(Collectors.toList());
        mv.addAttribute("piles", allSnackPiles);
        return "index";
    }

    private Model setCommon(Model model) {
        model.addAttribute("title", "Snack Machine");

        return model;
    }
}
