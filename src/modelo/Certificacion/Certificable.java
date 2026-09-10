package modelo.Certificacion;
import modelo.Estudiante;

public interface Certificable {
    String ENTIDAD_EMISORA = "UTN - FRM";
    String generarCertificado(Estudiante estudiante);
}
