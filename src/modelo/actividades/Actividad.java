package modelo.actividades;


import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.util.ArrayList;
import java.time.LocalDate;


public abstract class Actividad {
    protected int id;
    protected String titulo;
    protected int cupoMaximo;
    protected static final int CUPO_MINIMO;

    private ArrayList<Inscripcion> incripciones;


    static  {
        CUPO_MINIMO = 5;
    }

    public Actividad (int id, String titulo, int cupoMaximo){
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = (cupoMaximo > CUPO_MINIMO) ? cupoMaximo : CUPO_MINIMO;
        this.incripciones = new ArrayList<>();

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getCupoMinimo(){
        return CUPO_MINIMO;
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException{
        if(incripciones.size() >= cupoMaximo){
            throw new CupoExcedidoException("No se puede inscribir al estudiante " + estudiante.getNombre() + "sin cupo disponible");
        }
        Inscripcion inscripcion = new Inscripcion(LocalDate.now(), "Registrado", this, estudiante);
        this.incripciones.add(inscripcion);
        return  inscripcion;
    }
    public void mostrarActividad(){
        System.out.println("\nmodelo.actividades.Actividad:");
        System.out.println("titulo: " + titulo);
        System.out.println("id: " + id);
        System.out.println("cupoMaximo: " + cupoMaximo);
    }
    public abstract double calcularCostoMateriales();

    public void mostrarInscripciones(){
        for(Inscripcion i:incripciones){
            System.out.println("\nmodelo.Inscripcion");
            System.out.println("Fecha: " + i.getFecha());
            System.out.println("Estado: " + i.getEstado());
            System.out.println("modelo.Estudiante: " + i.getEstudiante().getNombre());
            System.out.println("Legajo: " + i.getEstudiante().getLegajo());

        }
    }


}