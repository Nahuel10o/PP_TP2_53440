package modelo;

import modelo.actividades.Actividad;

import java.time.LocalDate;

public class Inscripcion {
    private LocalDate fecha;
    private String estado;
    private Actividad actividad;
    private Estudiante estudiante;
    private TicketDeAccesso ticket;

    public Inscripcion(LocalDate fecha, String estado, Actividad actividad, Estudiante estudiante) {
        this.fecha = fecha;
        this.estado = estado;
        this.actividad = actividad;
        this.estudiante = estudiante;
    }


    public class TicketDeAccesso {
        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAccesso(){
            this.idTicket =("ickect- " +actividad.getId() +" " + estudiante.getLegajo() + " " + System.currentTimeMillis());
            this.fechaEmision = LocalDate.now();
            System.out.println("Ticket generado, inscripcion " + idTicket);
        }

        public void enviarTicket(){
            System.out.println("Enviando Ticket " + idTicket + " al estudiante " + estudiante.getNombre()+ " para la actividad " + actividad.getTitulo());
        }
        }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public Actividad getActividad() {
        return actividad;
    }
    public Estudiante getEstudiante(){
        return  estudiante;
    }
    public void confirmar(){
        this.estado ="CONFIRMADA";
        this.ticket = new TicketDeAccesso();
    }
    public TicketDeAccesso getTicket(){
        return ticket;
    }
}