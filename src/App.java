import excepciones.CupoExcedidoException;
import modelo.*;
import modelo.certificacion.Certificable;
import modelo.actividades.*;
import modelo.actividades.Charla;
import modelo.actividades.Taller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

/**
 * Clase principal desde la cual se crean y vinculan los objetos del modelo.
 * Permite ejercitar dependencia o uso desde App hacia las clases del dominio.
 */
public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar=true;
        boolean esGratuito= true;
        double costoBase = 0;
        int id = 1;

        /* Se crean estudiantes } */
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        System.out.println("REGISTRO DE ESTUDIANTES: ");
        System.out.println("======================");

        while (continuar){
            System.out.println("Ingese legajo del estudiante: ");
            String legajo = scanner.nextLine();
            System.out.println("Ingese nombre y apellido del estudiante: ");
            String apenomb = scanner.nextLine();
            estudiantes.add(new Estudiante(legajo, apenomb));
            System.out.println("desea crear otro estudiante  S/N?");
            String respuesta = scanner.nextLine().toLowerCase();
            continuar = esAfirmativa(respuesta);
        }

        /* Se itera construyendo eventos */
        System.out.println("\n\nREGISTRO DE EVENTOS: ");
        System.out.println("====================");
        continuar=true;
        while(continuar) {
            /* Se requieren datos por consola para construir un evento */
            System.out.println("Ingese un titulo para el evento: ");
            String titulo = scanner.nextLine();
            System.out.println("El evento tendra costo para los participantes S/N?");
            String respuesta = scanner.nextLine().toLowerCase();
            if (esAfirmativa(respuesta)) {
                esGratuito= false;
                System.out.println("Ingese el costo base:  ");
                costoBase = scanner.nextDouble();
                scanner.nextLine();
            }



            /* Se crean las actividades del evento */
            System.out.println("\n\nREGISTRO DE ACTIVIDADES PARA EL EVENTO " );
            int idActividad=1;
            Actividad actividad = solicitarDatosActividad(scanner, idActividad);
            ++idActividad;
            EventoUniversitarios evento = new EventoUniversitarios(
                    "EVT-" + id,
                    titulo,
                    costoBase,
                    esGratuito,
                    actividad
            );

            System.out.println("Desea crear otra actividad para el  evento " + evento.getTitulo() + " S/N?");
            respuesta = scanner.nextLine().toLowerCase();
            continuar  = esAfirmativa(respuesta);
            while(continuar){
                Actividad actividadExtra = solicitarDatosActividad(scanner, idActividad);
                ++idActividad;
                evento.agregarActividad(actividadExtra);
                System.out.println("Desea crear otra actividad para el  evento " + evento.getTitulo() + " S/N?");
                respuesta = scanner.nextLine().toLowerCase();
                continuar  = esAfirmativa(respuesta);
            }

            /* Se crea una sala y se asigna al evento */
            System.out.println("Ingese el nombre de la sala donde se realizará el evento: ");
            String nombreSala= scanner.nextLine();
            Sala sala = new Sala(id, nombreSala);

            evento.agregarSala(sala);


            /* Se inscriben estudiantes en actividades */
            try{
                System.out.println("\n\nINSCRIPCION DE ESTUDIANTES EN ACTIVIDADES DEL  EVENTO " + evento.getTitulo());
                System.out.println("===============================================================================");
                continuar=true;
                while (continuar){
                    System.out.println("Ingese legajo del estudiante a inscribir: ");
                    String legajo = scanner.nextLine();
                    System.out.println("Ingese id de la Actividad: ");
                    idActividad = scanner.nextInt();
                    scanner.nextLine(); // se consume linea
                    for (Estudiante estudiante: estudiantes){
                        if (estudiante.getLegajo().equals(legajo)){

                            evento.getActividades().get(--idActividad).inscribir(estudiante); //.get(--idActividad) es para acceder a la instancia modelo.actividades.Actividad de la lista actividades
                        }
                    }
                    System.out.println("Desea generar otra inscripción  S/N?");
                    respuesta = scanner.nextLine().toLowerCase();
                    continuar  = esAfirmativa(respuesta);
                }

            }catch (CupoExcedidoException e){
                System.out.println("Error al inscribir: " + e.getMessage());
            }



            try{

                evento.persistirEvento();
                System.out.println("Persistinedo evento: " + evento.getTitulo());
                EventoUniversitarios copiaArchivo = evento.recuperarEvento(evento.getId());
                System.out.println("Evento recuperado de archivos");

            }catch (FileNotFoundException e){
                System.out.println("Archivo no encontrado");
            }
            catch (ClassNotFoundException e) {
                System.out.println("Clase no encontrada");
            }
            catch (IOException e){
                System.out.println("Error de entrada/salida");
            }


            /* Se muestran datos del evento */
            System.out.println("\n\n DATOS DEL EVENTO");
            evento.mostrar();
            System.out.println("\nSe emiten certificados de asistencia:");
            for(Actividad a: evento.getActividades()){
                if(a instanceof Certificable certificable){
                    for(Inscripcion i: a.getIncripciones()){
                        System.out.println(certificable.generarCertificado(i.getEstudiante()));
                    }
                }
            }

            List<Curso> listaDeCursos = evento.filtrarActividadesPorTipo(Curso.class);
            List<Charla> listaDeCharlas = evento.filtrarActividadesPorTipo(Charla.class);
            List<Taller> listaDeTalleres = evento.filtrarActividadesPorTipo(Taller.class);

            System.out.println("Cantidad de Cursos en el evento: " + listaDeCursos.size());
            System.out.println("Cantidad de Charlas en el evento: " + listaDeCharlas.size());
            System.out.println("Cantidad de Talleres en el evento: " + listaDeTalleres.size());

            System.out.println("Costo de los Cursos en el evento: " + evento.calcularCostoMateriales(listaDeCursos));
            System.out.println("Costo de los Charlas en el evento: " + evento.calcularCostoMateriales(listaDeCharlas));
            System.out.println("Costo de los Talleres en el evento: " + evento.calcularCostoMateriales(listaDeTalleres));


            /* Se consulta si se desea continuar creando eventos*/
            System.out.println("\n\nDesea crear otro evento  S/N?");
            respuesta = scanner.nextLine().toLowerCase();
            continuar  = esAfirmativa(respuesta);
        }

        /* Se muestra la cantidad total de eventos creados */
        System.out.println("\n\nTOTAL DE EVENTOS CREADOS: " + EventoUniversitarios.getCantEventos());
    }
    private static boolean esAfirmativa(String respuesta){
        return (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));
    }


    private static Actividad solicitarDatosActividad(Scanner scanner, int id){
        int idActividad= id;
        System.out.println("Ingese el título de la actividad: ");
        String tituloActividad= scanner.nextLine();
        System.out.println("Ingese el cupo máximo de estudiantes admitidos para la actividad: ");
        int cupo= scanner.nextInt();
        scanner.nextLine();
        System.out.println("Tipo de Actividad (c/charla  || t/Taller) || Curso)");
        String tipo = scanner.nextLine().toLowerCase();
        if(tipo.equals("c") || tipo.equals("charla")){
            System.out.println("Ingrese el disertante de la charla: ");
            String disertante = scanner.nextLine();
            Charla charla = new Charla(id, tituloActividad, cupo, disertante);
            return  charla;

        } else if (tipo.equals("t")|| tipo.equals("taller")) {
            System.out.println("Requiere notebook S/N?");
            String repuesta = scanner.nextLine();
            boolean requiereNotebook = esAfirmativa(repuesta) ;
            Taller taller = new Taller(id, tituloActividad, cupo, requiereNotebook);
            return taller;
        }else if(tipo.equals("curso")  ){
            System.out.println("Nivel del curso? ");
            int nivel = scanner.nextInt();
            String jose = scanner.nextLine();
            Curso curso = new Curso(id, tituloActividad, cupo, nivel);
            return curso;
        }
        else {
            System.out.println("Creando charla por defecto");
            System.out.println("Ingrese el disertante de la charla: ");
            String disertante = scanner.nextLine();
            Charla charla = new Charla(id, tituloActividad, cupo, disertante);
            return  charla;
        }

    }
}
