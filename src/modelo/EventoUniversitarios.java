package modelo;

import modelo.actividades.Actividad;
import java.io.*;

import java.util.List;
import java.util.ArrayList;


public class EventoUniversitarios implements Serializable{
    private final String  Id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;



    private static int cantEventos;

    private Sala sala;
    private ArrayList<Actividad> actividades;


    static {
        cantEventos = 0;
        System.out.println("Inicializador estatico: Se cargo la clase EventoUniversitario.");
    }

    public EventoUniversitarios (String Id, String titulo, double costoBase, boolean gratuito, Actividad actividad){
        this.Id = Id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        this.actividades.add(actividad);

        cantEventos++;
    }

    public EventoUniversitarios (EventoUniversitarios otro) {
        this.Id = otro.Id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.actividades = new ArrayList<>(otro.actividades);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId(){ return this.Id; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public boolean isGratuito() {
        return gratuito;
    }

    public void setGratuito(boolean gratuito) {
        this.gratuito = gratuito;
    }

    public static int getCantEventos() {
        return cantEventos;
    }

    public void agregarSala(Sala sala){
        this.sala = sala;
    }

    public void agregarActividad(Actividad actividad) { this.actividades.add(actividad);}


    public ArrayList<Actividad> getActividades(){
        return actividades;
    }

    //metodos
    public void mostrar(){
        System.out.println("\n\ntitulo: " + titulo );
        System.out.println("Id: " + Id);
        System.out.println("costo: " + calcularCostoEstimado());
        System.out.println("gratuito: " + gratuito );
        System.out.println("modelo.Sala:" + (sala !=null ? sala.getNombre() : "Sin sala asignada"));
        for(Actividad i: actividades) {
            i.mostrarActividad();
            i.mostrarInscripciones();
        }

    }

    public double calcularCostoEstimado(){
        double total = costoBase;
        if (this.gratuito)
            return 0;
        else
            for (Actividad i: actividades){
                total += i.calcularCostoMateriales();
            }
            return total * 1.21;
    }

    public boolean persistirEvento () throws IOException{
        String archivo = "Evento_" + this.Id + ".dat";
        try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream (archivo))){
            oos.writeObject(this);
            return true;
        }
    }
    public EventoUniversitarios recuperarEvento(String id) throws IOException, ClassNotFoundException{
        String archivo = "Evento_" + id + ".dat";
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream (archivo))){
            return (EventoUniversitarios) ois.readObject();
        }
    }
    //de la lista Actividades obtengo una mas pequeña con el tipo especificado en Class <T> tipo
    public <T extends Actividad> List<T> filtrarActividadesPorTipo (Class <T> tipo){
        List<T> resultado = new ArrayList<>();
        for(Actividad act : actividades)
            if(tipo.isInstance(act)){
            resultado.add(tipo.cast(act));
        }
        return resultado;
    }
    //sumo el costo de todas las actividades de la lista que pasa como parametro
    public double calcularCostoMateriales (List<? extends Actividad> actividadList){
        double total = 0.0;
        for (Actividad act : actividadList){
            total =+ act.calcularCostoMateriales();
        }
        return total;
    }



}