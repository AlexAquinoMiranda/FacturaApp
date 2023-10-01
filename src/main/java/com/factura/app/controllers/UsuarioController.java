package com.factura.app.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.factura.app.models.entity.Usuario;
import com.factura.app.models.service.IUploadFileService;
import com.factura.app.models.service.IUsuarioService;

/**
 * 
 * UsuarioController es una clase en donde se va a gestionar operaciones crud
 * para el usuario.
 */
@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	private IUsuarioService userService;

	public static Long idUsuarioGlobal = (long) 0;

	/**
	 * guardar datos
	 * 
	 * @param usuario
	 * @param result
	 * @param model
	 * @param foto
	 * @param flash
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Usuario usuario, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuario");
			return "form";
		}

		if (!foto.isEmpty()) {

			if (usuario.getId() != null && usuario.getId() > 0 && usuario.getFoto() != null
					&& usuario.getFoto().length() > 0) {

				uploadFileService.delete(usuario.getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {

				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			usuario.setFoto(uniqueFilename);
		}

		String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!" : "Usuario creado con éxito!";

		userService.save(usuario);
		idUsuarioGlobal = usuario.getId();
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	/**
	 * listado de jugadores
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {

		List<Usuario> jugadores = userService.findAll();

		model.addAttribute("titulo", "Listado de jugadores");
		model.addAttribute("jugadores", jugadores);
		return "listar";
	}

	/**
	 * Método que guarda los datos de un usuario
	 * 
	 * @param usuario
	 * @param result
	 * @param model
	 * @param flash
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String guardar(Usuario usuario, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuario");
			return "form";
		}
	

		String mensajeFlash = (usuario.getId() != null) ? "Inicio de sesión correcto!" : "Perfil creado con éxito!";

		userService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	/**
	 * método para autenticar usuario, iniciando con una cuenta actual o registrando
	 * una nueva
	 * 
	 * @param model model para las variables.
	 * @return home home view.
	 */
	@RequestMapping(value = "/home")
	public String home(Map<String, Object> model) {
		model.put("inicio", "Sing in");
		model.put("crear", "Sing up");
		model.put("titulo", "Autenticación de usuario");
		model.put("usuario", new Usuario());
		return "home";
	}

	/**
	 * eliminar cuenta de usuario
	 * 
	 * @param id
	 * @param flash
	 * @return redirect view home
	 */
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Usuario usuario = this.userService.findOne(id);

			userService.delete(id);
			flash.addFlashAttribute("success", "Usuario " + usuario.getNombre() + " eliminado con éxito!");

			

		}
		return "redirect:/home";
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

		Usuario usuario = null;

		if (id > 0) {
			usuario = this.userService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
		return "form";
	}

	/**
	 * método para ver al usuario de acuerdo a su id
	 * 
	 * @param id
	 * @param model
	 * @param flash
	 * @return
	 */
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario jugador = userService.findOne(id);
		if (jugador == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("jugador", jugador);
		model.put("titulo", "Nombre de Jugador: " + jugador.getNombre());
		return "ver";
	}

	@RequestMapping(value = "/account")
	public String account( Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = userService.findOne((long) 1);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("jugador", usuario);
		model.put("titulo", "Nombre de Jugador: " + usuario.getNombre());
		return "account";
	}
}
