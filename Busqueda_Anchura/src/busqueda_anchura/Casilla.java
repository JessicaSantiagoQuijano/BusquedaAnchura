/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_anchura;


/**
 *
 * @author jessi
 */
public class Casilla {
    //Atributos de la clase Casilla
    private char estado;
    private int[] posicion;
    private int[] posicionVicitante;

    //Constructor de la clase sin parametro, se utiliza para poder construir una casilla inicial
    public Casilla() {                
        posicion = new int[2];
        posicionVicitante = new int[2];
        estado = 'x';
        posicion[0] = -1;
        posicion[1] = -1;
        posicionVicitante[0] = -1;
        posicionVicitante[1] = -1;
    }
    
    //Constructor de la Casilla con atributos de entrada, utilizado para construir una casilla inicializada
    public Casilla(int fila, int columna, char estado){
        posicion = new int[2];
        posicionVicitante = new int[2];
        this.estado = estado;
        posicion[0] = fila;
        posicion[1] = columna;
        // Se establecen las posiciones con -1 debido a la posibilidad de no encontrar la casilla "S"
        posicionVicitante[0] = -1;
        posicionVicitante[1] = -1;
    }
    
    
    //Getters and Setters de los atributos principales
    public int[] getPosicion(){
        return posicion;
    }
    public int[] getPosicionVisitante(){
        return posicionVicitante;
    }
    public char getEstado(){
        return estado;
    }
    
    //Función para determinar cuando una casilla diferente de "S" es visitada
    public void visitado(int[] posVisitante){
        posicionVicitante = posVisitante;
        if(estado != 's'){
            estado = 'v';
        }
    }
}
