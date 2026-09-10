package modelo.actividades;

import modelo.certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook){
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales(){
        return requiereNotebook ? 5000 : 2000;
    }

    public void setRequiereNotebook( boolean rn){
        this.requiereNotebook = rn;
    }
    public boolean getRequiereNotebook(){ return this.requiereNotebook;}

    @Override
    public String generarCertificado(Estudiante estudiante){
        String mensaje = "Certificado de asistencia del Estudiante: "
                + estudiante.getNombre() + "al taller: " + getTitulo() +
                "emitido por la entidad: " + ENTIDAD_EMISORA;
        return mensaje;
    }
}
