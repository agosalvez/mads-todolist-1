package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class UsuariosService {

        public static Usuario crearUsuario(Usuario usuario) {
        	return UsuarioDAO.create(usuario);
        }

        public static List<Usuario> findAllUsuarios() {
        	List<Usuario> lista = UsuarioDAO.findAll();
        	Logger.debug("Numero de usuarios: " + lista.size());
        
        	return lista;
    	}

    	public static Usuario findUsuario(String id) {
        	Usuario user = UsuarioDAO.find(id);
        	Logger.debug("Se accede al usuario: "+ user.id + " " + user.nombre);

        	return user;
    	}
}