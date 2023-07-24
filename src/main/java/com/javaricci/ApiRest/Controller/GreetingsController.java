package com.javaricci.ApiRest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaricci.ApiRest.Model.Usuario;
import com.javaricci.ApiRest.Repository.UsuarioRepository;

//EXECUTAR BROWSE: http://localhost:8080/ApiRest/index.html
@RestController
public class GreetingsController {

	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private UsuarioRepository usuarioRepository;


	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Meu Spring Boot API: " + name + "!";
	}


	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);

		return "Ola mundo " + nome;
	}


	//POSTMAN: GET http://localhost:8080/ApiRest/listatodos
	@GetMapping(value = "listatodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}


	//POSTMAN: POST http://localhost:8080/ApiRest/salvar
	//{"nome": "FABIO KATAYAMA","idade": 42}
	@PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

	}


	//POSTMAN: PUT http://localhost:8080/ApiRest/atualizar
	//{"id": 8,"nome": "FABIO KATAYAMA ALTERADO","idade": 42}
	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);

	}


	//POSTMAN: DELETE http://localhost:8080/ApiRest/delete/
	//Informar Params--> KEY: iduser VALUE: 10
	@DeleteMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam Long iduser) {

		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}


	//POSTMAN: http://localhost:8080/ApiRest/buscaruserid
	//Informar Params--> KEY: iduser VALUE: 10
	@GetMapping(value = "buscaruserid")
	@ResponseBody
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) {

		Usuario usuario = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

	}


	//POSTMAN: GET http://localhost:8080/ApiRest/buscarPorNome
	//Informar Params--> KEY: name VALUE: EDUARDO ABDALA
	@GetMapping(value = "buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {

		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);

	}

}
