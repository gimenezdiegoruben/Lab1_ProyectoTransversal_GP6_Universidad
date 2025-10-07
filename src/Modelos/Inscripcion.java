package Modelos;

/**
 *
 * @author Grupo 6 
    Gimenez Diego Ruben
    Carlos German Mecias Giacomelli
    Tomas Migliozzi Badani
    Urbani Jose
 */
public class Inscripcion {
    private int idInscripto;
    private double nota;
    private Alumno alumno;
    private Materia materia;

    public Inscripcion() {
    }

    public Inscripcion(double nota, Alumno alumno, Materia materia) {
        this.idInscripto = -1;
        this.nota = nota;
        this.alumno = alumno;
        this.materia = materia;
    }

    public int getIdInscripto() {
        return idInscripto;
    }

    public void setIdInscripto(int idInscripto) {
        this.idInscripto = idInscripto;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "idInscripto=" + idInscripto + ", nota=" + nota + ", alumno=" + alumno + ", materia=" + materia + '}';
    }
    
    
    
    
}
