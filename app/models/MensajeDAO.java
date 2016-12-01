package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;
import java.util.List;
import java.util.Date;

public class MensajeDAO {
    public static boolean create (Mensaje mensaje) {
        JPA.em().persist(mensaje);
        // Hacemos un flush y un refresh para asegurarnos de que se realiza
        // la creación en la BD y se devuelve el id inicializado
        JPA.em().flush();
        JPA.em().refresh(mensaje);
        Logger.debug(mensaje.toString());
        return true;
    }

    public static List<Mensaje> findAll() {
        TypedQuery<Mensaje> query = JPA.em().createQuery(
                  "SELECT u FROM Mensaje u WHERE borrado = 0 ORDER BY id ASC", Mensaje.class);
        return query.getResultList();
    }
}
