/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_anchura;

import java.util.LinkedList;


/**
 *
 * @author jessi
 */
public class Matriz {
    //Atributos principales de la calse
    private Casilla[][] m;
    //Pantalla para inicializar a la hora de correr el problema
    private char[][] pantalla = new char[][]{{'i', 'o', 'o', '#', 'o', 'o', 'o'},
                                             {'o', '#', 'o', 'o', 'o', '#', 'o'},
                                             {'o', '#', 'o', 'o', 'o', 'o', 'o'},
                                             {'o', 'o', '#', '#', 'o', 'o', 'o'},
                                             {'#', 'o', '#', 's', 'o', '#', 'o'} };;
    
    private int filas;
    private int columnas; 
    private boolean encontrado;
    //Lista que contiene el camino solucion
    LinkedList<Casilla> vuelta;
    
    //Constructor de la clase Matriz que inicializa los atributos incluyendo una matriz de tipo Casilla[][]
    public Matriz(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;         
        m = new Casilla[filas][columnas];
        vuelta = new LinkedList();
        encontrado = false;
        //For que inicializa la matriz m con los elementos de la pantalla
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {                
                m[i][j] = new Casilla(i,j,pantalla[i][j]);                
            }                        
        }            
    }
    
    //Getters y setters de los atributos principales incluyendo la solucion "Vuelta"
    public char[][] getPantalla(){
        return pantalla;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    
    public Casilla[][] getM(){
        return m;
    }  
        
    public LinkedList<Casilla> getVuelta(){
        return vuelta;
    }
    
    //Funcion que inicializa el metodo principal del algoritmo, dandole la posicion donde iniciará el algoritmo
    public LinkedList<Casilla> buscaCamino(){
        vuelta.add(camino(obtieneVecinos(new int[] {0,0})));
        return vuelta;
    }
    
    //Metodo recursivo de tipo casilla que resuelve el algoritmo
    public Casilla camino(LinkedList<Casilla> vecindad){
        //Variable reg de tipo Casilla inicializada con el constructor simple
        Casilla reg  = new Casilla();                
        
        //If encargado de verificar si obtenemos la lista vecinos vacia, en caso
        //de que no encuentre ningun vecino al rededor, o que no encuentre la casilla "s"
        if(vecindad.isEmpty()){
            encontrado = false;
            reg = obtieneCasilla(new int[] {0,0});
        }else{
            //for each que recorre la lista vecindad analizando cada uno de los elementos por si ya se encontro la casilla "S"
            for(Casilla vecino: vecindad){
                if(vecino.getEstado() == 's'){
                    encontrado = true;
                    vuelta.add(vecino);
                    //Asigna a la variable reg la posicion del visitante del vecino para comenzar hacia atras la solucion "Vuelta"
                    reg = obtieneCasilla(vecino.getPosicionVisitante());
                    break;
                }
            }
            //Pregunta si aun el algoritmo no ha encontrado la casilla "S"
            if(!encontrado){
                //Utilizamos una lista de Casillas para ubicar los vecinos externos de la posicion evaluada
                LinkedList<Casilla> externos = new LinkedList<Casilla>();
                //For each que recorre la lista vecindad
                for(Casilla vecino: vecindad){
                    //Guarda progesivamente en la lista externos los elementos de la lista ext, que 
                    //es la encargada de guardar los vecinos de cada vecino disponible
                    LinkedList<Casilla> ext = obtieneVecinos(vecino.getPosicion());
                    for(Casilla vec: ext){
                        externos.add(vec);
                    }
                }
                //Llama de manera recursiva a la misma funcion entrgandole la nueva lista de vecinos externos                
                reg = camino(externos);
                //En esta seccion le entrega a la lista vuelta el valor actualizado de reg
                vuelta.add(reg);
                //Ne caso de haberce encontrado la casilla final "S", el algoritmo fevuelve poco a poco 
                //la casilla de los padres del vecino concluyendo poco a poco con la lista final resultante 
                if(encontrado){
                    reg = obtieneCasilla(reg.getPosicionVisitante());
                }             
            }
        }
        return reg;
    }
    
    //Metodo de la matriz encargado de obtener los vecinos resiviendo una posicion int [][] de una casilla
    private LinkedList<Casilla> obtieneVecinos(int[] pos){        
        LinkedList<Casilla> vecinos = new LinkedList();
        char estado;

        //Seccion que nos devuelve el vecino en la ubicacion derecha        
        //Si no ha sido visitado v y si no es pared # dentro del metodo "visitado(pos)"
        //Verifica si la posición de la casilla actual es menor a 6, debido a que es el numero máximo de columnas
        if(pos[1] < 6)
        {
            //Obtiene en la variable estado el estado de la posicion a la derecha de la actual
            estado = m[pos[0]][pos[1] + 1].getEstado();
            //Compara el estado y agrega el vecino de la derecho a la lista vecinos
            if(estado == 'o' || estado == 's'){
                vecinos.add(m[pos[0]][pos[1] + 1]);
                m[pos[0]][pos[1] + 1].visitado(pos);
            }
                        
        }
        
        //Seccion que nos devuelve el vecino en la ubicacion abajo
        //Si no ha sido visitado v y si no es pared # dentro del metodo "visitado(pos)"
        //Verifica si la posicion de la casilla actual es menor 4, debido a que es el numero máximo de filas      
        if(pos[0] < 4)
        {
            //Obtiene en la variable estado el estado de la posición de abajo de la actual
            estado = m[pos[0] + 1][pos[1]].getEstado();
            //Compara el estado y agrega el vecino de abajo a la lista vecinos
            if(estado == 'o' || estado == 's'){
                vecinos.add(m[pos[0] +1 ][pos[1]]);
                m[pos[0] + 1][pos[1]].visitado(pos);
            }            
        }
        
        //Seccion que nos devuelve el vecino en la ubicacion izquierda
        //Si no ha sido visitado v y si no es pared # dentro del metodo "visitado(pos)" 
        //Verifica si la posicion de la casilla actual es mayor a 0, debido a que es el numero minimo de columnas
        if(pos[1] > 0)
        {
            //Obtiene en la variable estado el estado de la posicion izquierda de la actual
            estado = m[pos[0]][pos[1] - 1].getEstado();
            //Compara el estado y agrega el vecino de la izquierda a la lista vecinos
            if(estado == 'o' || estado == 's'){
                vecinos.add(m[pos[0]][pos[1] - 1]);
                m[pos[0]][pos[1] - 1].visitado(pos);
            }
        }

        //Seccion que nos devuelve el vecino en la ubicacion superior
        //Si no ha sido visitado v y si no es pared # dentro del metodo "visitado(pos)" 
        //Verifica si la posicion de la casilla actual es mayor a 0, debido a que es el numero minimo de filas
        if(pos[0] > 0)
        {
            //Obiene en la variable estado el estado de la posición superior de la casilla actual
            estado = m[pos[0] - 1][pos[1]].getEstado();
            //Compara el estado y agrega el vecino superior a la lista vecinos
            if(estado == 'o' || estado == 's'){
                vecinos.add(m[pos[0] - 1 ][pos[1]]);
                m[pos[0] - 1][pos[1]].visitado(pos);
            }            
        }
        return vecinos;
    }    
    
    //Obtiene la casilla obtenida con la posicion
    private Casilla obtieneCasilla(int [] pos){
        return m[pos[0]][pos[1]];
    }
}
