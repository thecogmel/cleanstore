package ufrn.tads.cleanstore.controller;

import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
	List<Produto> carrinho = new ArrayList<Produto>();

	@Autowired
	public void setFileStorageService(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@Autowired
	public void setService(ProdutoService service) {
		this.service = service;
	}

	@RequestMapping(value = { "/", "/produto" }, method = RequestMethod.GET)
	public String getHome(Model model, HttpServletResponse response) {

		Date data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
		String currentTime = sdf.format(data);
		Cookie c = new Cookie("visita", currentTime);
		c.setMaxAge(86400);
		response.addCookie(c);

		var listaProdutos = service.findAll();
		model.addAttribute("listaProdutos", listaProdutos);
		return "index";
	}

	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String getAdmin(Model model) {
		var listaProdutos = service.findAll();
		model.addAttribute("listaProdutos", listaProdutos);
		return "admin";
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public String doSalvar(@ModelAttribute @Valid Produto produto, Errors errors,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "cadastro";
		} else {

			/*
			 * System.out.println(file.getOriginalFilename());
			 * System.out.println(file.getContentType());
			 * System.out.println(file.getSize());
			 */

			produto.setImagemUri(file.getOriginalFilename());
			service.save(produto);
			fileStorageService.save(file);

			redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
			return "redirect:/";
		}
	}

	@RequestMapping("/cadastro")
	public String getFormCadastro(Model model) {
		Produto produto = new Produto();
		model.addAttribute("produto", produto);
		return "cadastro";
	}

	@RequestMapping("/deletar/{id}")
	public String doDelete(@PathVariable(name = "id") Long id) {
		service.delete(id);
		return "redirect:/";
	}

	@RequestMapping("/editar/{id}")
	public ModelAndView getFormEdicao(@PathVariable(name = "id") Long id) {
		ModelAndView modelAndView = new ModelAndView("edicao");
		Produto produto = service.findById(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}

	@RequestMapping("/adicionarCarrinho/{id}")
	public String getCarrinho(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute("cart") == null) {
			session.setAttribute("cart", new ArrayList<Produto>());
		}
		ArrayList<Produto> produtosCarrinho = (ArrayList<Produto>) session.getAttribute("cart");

		produtosCarrinho.add(service.findById(id));
		System.out.println("");

		return "redirect:/";
	}

	@RequestMapping("/verCarrinho/{id}")
	public String getVerCarrinho(@PathVariable(name = "id") Long id) {
		Produto produto = service.findById(id);
		carrinho.add(produto);
		return "redirect:/";
	}
}
