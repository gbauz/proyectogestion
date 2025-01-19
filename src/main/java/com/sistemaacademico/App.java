package com.sistemaacademico;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Estudiante> estudiantes = new ArrayList<>();
    private static ArrayList<Materia> materias = new ArrayList<>();
    private static ArrayList<Calificacion> calificaciones = new ArrayList<>();

    public static void main(String[] args) {
        inicializarDatos();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al sistema de gestión educativa");

        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Usuario usuario = login(username, password);
        if (usuario == null) {
            System.out.println("Credenciales inválidas");
            return;
        }

        switch (usuario.getRolId()) {
            case 1 -> menuDocente(scanner);
            case 2 -> System.out.println("Bienvenido, Secretario");
            case 3 -> System.out.println("Bienvenido, Administrador");
            default -> System.out.println("Rol no reconocido");
        }
    }

    private static void inicializarDatos() {
        usuarios.add(new Usuario(1, "docente1", "1234", 1));
        usuarios.add(new Usuario(2, "secretario1", "1234", 2));
        usuarios.add(new Usuario(3, "admin1", "1234", 3));

        estudiantes.add(new Estudiante(1, "Juan Pérez", "10-A"));
        estudiantes.add(new Estudiante(2, "María López", "10-B"));

        materias.add(new Materia(1, "Matemáticas", "10-A"));
        materias.add(new Materia(2, "Historia", "10-B"));
    }

    private static Usuario login(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(username) && usuario.getContrasena().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    private static void menuDocente(Scanner scanner) {
        while (true) {
            System.out.println("\nMódulo Docente:");
            System.out.println("1. Registrar estudiante y gestionar calificaciones");
            System.out.println("2. Mostrar estudiantes registrados por materia");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> registrarYGestionarEstudiante(scanner);
                case 2 -> mostrarEstudiantesPorMateria();
                case 3 -> {
                    System.out.println("Saliendo del módulo docente...");
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private static void registrarYGestionarEstudiante(Scanner scanner) {
        System.out.print("Nombre del estudiante: ");
        String nombre = scanner.nextLine();

        System.out.print("Curso del estudiante: ");
        String curso = scanner.nextLine();

        System.out.print("Seleccione una materia (1: Matemáticas, 2: Historia): ");
        int materiaId = scanner.nextInt();
        Materia materiaSeleccionada = materias.get(materiaId - 1);

        System.out.print("Calificación del primer parcial: ");
        double cal1 = scanner.nextDouble();

        System.out.print("Calificación del segundo parcial: ");
        double cal2 = scanner.nextDouble();

        System.out.print("Calificación del examen final: ");
        double cal3 = scanner.nextDouble();

        int nuevoId = estudiantes.size() + 1;
        Estudiante nuevoEstudiante = new Estudiante(nuevoId, nombre, curso);
        estudiantes.add(nuevoEstudiante);

        Calificacion nuevaCalificacion = new Calificacion(nuevoId, materiaSeleccionada.getId(), cal1, cal2, cal3);
        calificaciones.add(nuevaCalificacion);

        System.out.println("Estudiante y calificaciones registradas exitosamente.");
    }

    private static void mostrarEstudiantesPorMateria() {
        if (calificaciones.isEmpty()) {
            System.out.println("No hay calificaciones registradas.");
            return;
        }

        // Agrupar las calificaciones por materia
        for (Materia materia : materias) {
            System.out.println("\nMateria: " + materia.getNombre());
            boolean found = false;

            // Mostrar estudiantes de esta materia
            for (Calificacion calificacion : calificaciones) {
                if (calificacion.getMateriaId() == materia.getId()) {
                    for (Estudiante estudiante : estudiantes) {
                        if (estudiante.getId() == calificacion.getEstudianteId()) {
                            System.out.println("Estudiante: " + estudiante.getNombre() + 
                                               " - Curso: " + estudiante.getCurso() + 
                                               " - Calificación 1: " + calificacion.getCal1() +
                                               " - Calificación 2: " + calificacion.getCal2() +
                                               " - Examen: " + calificacion.getCal3());
                            found = true;
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("No hay estudiantes registrados para esta materia.");
            }
        }
    }
}

class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private int rolId;

    public Usuario(int id, String nombreUsuario, String contrasena, int rolId) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rolId = rolId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getRolId() {
        return rolId;
    }
}

class Estudiante {
    private int id;
    private String nombre;
    private String curso;

    public Estudiante(int id, String nombre, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCurso() {
        return curso;
    }
}

class Materia {
    private int id;
    private String nombre;
    private String curso;

    public Materia(int id, String nombre, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCurso() {
        return curso;
    }
}

class Calificacion {
    private int estudianteId;
    private int materiaId;
    private double cal1;
    private double cal2;
    private double cal3;

    public Calificacion(int estudianteId, int materiaId, double cal1, double cal2, double cal3) {
        this.estudianteId = estudianteId;
        this.materiaId = materiaId;
        this.cal1 = cal1;
        this.cal2 = cal2;
        this.cal3 = cal3;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public double getCal1() {
        return cal1;
    }

    public double getCal2() {
        return cal2;
    }

    public double getCal3() {
        return cal3;
    }
}
