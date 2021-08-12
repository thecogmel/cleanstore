package ufrn.tads.cleanstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufrn.tads.cleanstore.model.Produto;
import ufrn.tads.cleanstore.service.FileStorageService;
import ufrn.tads.cleanstore.service.ProdutoService;

@Controller
public class ProdutoController {

    ProdutoService service;
    FileStorageService fileStorageService;

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setService(ProdutoService service) {
        this.service = service;
    }

    @RequestMapping(value = { "/", "/produto" }, method = RequestMethod.GET)
    public String getHome(Model model) {
        var listaProdutos = service.findAll();
        model.addAttribute("listaProdutos", listaProdutos);
        return "index";
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public String doSalvar(@ModelAttribute @Valid Produto produto, Errors errors, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
		if (errors.hasErrors()){
			return "cadastro";
		}else{

			/*
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getContentType());
			System.out.println(file.getSize());
			 */

			produto.setImagemUri(file.getOriginalFilename());
			service.save(produto);
			fileStorageService.save(file);

			redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
			return "redirect:/";
		}
	}

    @RequestMapping("/cadastro")
	public String getFormCadastro(Model model){
		Produto produto = new Produto();
		model.addAttribute("produto", produto);
		return "cadastro";
	}

    @RequestMapping("/deletar/{id}")
	public String doDelete(@PathVariable(name = "id") Long id){
		service.delete(id);
		return "redirect:/";
	}

    @RequestMapping("/editar/{id}")
	public ModelAndView getFormEdicao(@PathVariable(name = "id") Long id){
		ModelAndView modelAndView = new ModelAndView("edicao");
		Produto produto = service.findById(id);
		modelAndView.addObject("produto",produto);
		return modelAndView;
	}

}
