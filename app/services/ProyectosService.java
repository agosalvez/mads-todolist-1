package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class ProyectosService {
		public static Proyecto crearProyecto(Proyecto proyecto) {
				return ProyectoDAO.create(proyecto);
    }
		public static List<Proyecto> findAllProyectos() {
				List<Proyecto> lista = ProyectoDAO.findAll();
				Logger.debug("Numero de proyectos: " + lista.size());

				return lista;
		}
}
