import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;
import org.junit.*;
import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import services.*;
import models.*;

public class AnadirTareasTest{

    static Database db;
    static JPAApi jpa;
    JndiDatabaseTester databaseTester;

    @BeforeClass
    static public void initDatabase() {
        db = Databases.inMemoryWith("jndiName", "DefaultDS");
        // Necesario para inicializar el nombre JNDI de la BD
        db.getConnection();
        // Se activa la compatibilidad MySQL en la BD H2
        db.withConnection(connection -> {
            connection.createStatement().execute("SET MODE MySQL;");
        });
        jpa = JPA.createFor("memoryPersistenceUnit");
    }

    @Before
    public void initData() throws Exception {
        databaseTester = new JndiDatabaseTester("DefaultDS");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new
        FileInputStream("test/resources/tareas_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @After
    public void clearData() throws Exception {
        databaseTester.onTearDown();
    }

    @AfterClass
    static public void shutdownDatabase() {
        jpa.shutdown();
        db.shutdown();
    }

    @Test
    public void nuevaTareaTestDAO() {
            jpa.withTransaction(() -> {

                Usuario user = UsuarioDAO.find(1);
                Tarea tarea = new Tarea("Terminar la practica 2", user);
                tarea = TareaDAO.create(tarea);
                user.tareas.add(tarea);

                assertEquals("Terminar la practica 2", TareaDAO.find(tarea.id).descripcion);
                assertEquals(user.tareas.size(), 5);

                Tarea tarea2 = new Tarea("Terminar la practica", user);
                tarea2 = TareaDAO.create(tarea2);
                user.tareas.add(tarea2);

                assertEquals("Terminar la practica", TareaDAO.find(tarea2.id).descripcion);
                assertEquals(user.tareas.size(), 6);
            });
    }

    @Test
    public void nuevaTareaTestService() {
            jpa.withTransaction(() -> {

                Usuario user = UsuariosService.findUsuario(1);
                Tarea tarea = new Tarea("Terminar la practica 2", user);
                tarea = TareasService.crearTarea(tarea);
                user.tareas.add(tarea);

                assertEquals("Terminar la practica 2", TareasService.findTarea(tarea.id).descripcion);
                assertEquals(user.tareas.size(), 5);

                Tarea tarea2 = new Tarea("Terminar la practica", user);
                tarea2 = TareaDAO.create(tarea2);
                user.tareas.add(tarea2);

                assertEquals("Terminar la practica", TareasService.findTarea(tarea2.id).descripcion);
                assertEquals(user.tareas.size(), 6);
            });
    }

    @Test
    public void CrearTareaConTamaño(){
      jpa.withTransaction(() -> {

      Usuario user = UsuariosService.findUsuario(1);
      Tarea tarea = new Tarea("Terminar la practica 2", user,0,"pequeña");
      tarea = TareaDAO.create(tarea);

      assertEquals("Terminar la practica 2", TareasService.findTarea(tarea.id).descripcion);
      assertEquals("pequeña",  TareasService.findTarea(tarea.id).tamano);

      });
    }


}
