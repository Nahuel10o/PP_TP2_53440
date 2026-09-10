package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Curso extends Actividad implements Certificable {
    private int nivel;
    public Curso(int id, String titulo, int cupoMaximo, int nivel){
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;

    }

    @Override
    public double calcularCostoMateriales(){
        return nivel * 1000;
    }

    @Override
    public String generarCertificado(Estudiante estudiante){
        String mensaje ="Certificado de asistenca al curso: " + this.titulo +
                " por el alumno: " + estudiante.getNombre() + " emitido por: " + ENTIDAD_EMISORA;
        return mensaje;
    }

}
