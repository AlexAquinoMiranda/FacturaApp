package com.factura.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.factura.app.models.entity.Factura;
import com.factura.app.models.entity.ItemFactura;
import com.factura.app.models.entity.Usuario;
import com.factura.app.models.service.IFacturaItemService;
import com.factura.app.models.service.IFacturaService;
import com.factura.app.models.service.IUsuarioService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IFacturaService facturaService;

	@Autowired
	private IUsuarioService userService;

	@Autowired
	private IFacturaItemService facturaItemService;

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

		ItemFactura factura = facturaItemService.findOne(id);

		Factura fac = new Factura();
		fac.setId((long) 1);
		fac.setCreateAt(new Date());
		fac.setDescripcion("nada");
		fac.setItems(facturaItemService.findAll());
		fac.setUsuario(userService
				.findOne((long) 1));/**
									 * 
									 *  aquí tengo que poner un campo de usuarioId para que pueda recuperarla.
									 *
									 *
									 *
									 * 
									 */
		fac.setObservacion("nada de nada");

		this.facturaService.save(fac);
		

		if (factura == null) {
			flash.addFlashAttribute("error", "Se ha producido un error, no existe en la BBDD");
			return "redirect:/listarFactura";
		}

		model.addAttribute("factura", fac);
		model.addAttribute("titulo", "Detalles de dato de producto diario");
		return "factura/ver";
	}

	@PostMapping("/form")
	public String guardar(ItemFactura facturaItem, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status, Locale locale) {

		if (facturaItem == null) {
			model.addAttribute("titulo", "Formulario");
			flash.addFlashAttribute("error", "error");
			return "factura/formItemFactura";
		}

		String mensajeFlash = (facturaItem.getId() != null) ? "Editado con exito" : "Creado con éxito!";

		System.err.println(facturaItem.toString());
		this.facturaItemService.save(facturaItem);
		status.setComplete();

		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarFactura";

	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model, RedirectAttributes flash, Locale locale, SessionStatus status) {
		model.put("factura", new ItemFactura());
		model.put("titulo", "Detalles");

		return "factura/formItemFactura";
	}

	/**
	 * Eliminar el registro de un item de una factura.
	 * 
	 * @param id
	 * @param flash
	 * @param locale
	 * @return
	 */
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		ItemFactura factura = facturaItemService.findOne(id);

		if (factura != null) {
			facturaService.delete(id);
			flash.addFlashAttribute("success", " Se ha eliminado el registro correctamente. ");
			return "redirect:/factura/listarFactura";
		}
		flash.addFlashAttribute("error", "No se ha podido realizar la operación de delete. ");

		return "redirect:listarFactura";
	}

	/**
	 * listado de item facturas
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listarFactura", method = RequestMethod.GET)
	public String listar(Model model) {

		List<ItemFactura> jugadores = this.facturaItemService.findAll();
		model.addAttribute("titulo", "Listado de jugadores");
		model.addAttribute("items", jugadores);
		
		return "factura/listarFactura";
	}

	/**
	 * eidtar datos de usuario
	 * 
	 * @param id
	 * @param model
	 * @param flash
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		ItemFactura usuario = null;

		if (id > 0) {
			usuario = this.facturaItemService.findOne(id);

			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la BBDD!");
				return "redirect:factura/listarFactura";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			return "redirect:factura/listarFactura";
		}

		model.put("factura", usuario);
		model.put("titulo", "Editar Usuario");
		return "factura/formItemFactura";
	}

}
