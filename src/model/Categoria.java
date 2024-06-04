package model;

public class Categoria {

    public int id;
    public String nome;

    //Metodo construtor que nao recebe parametros
    public Categoria(){
        this.id = 0;
        this.nome = "Sem nome";
    }

    public Categoria(String nome){
        this.id = 0;
        this.nome = nome;

    }
    
    public Categoria(int id,String nome){
        this.id = id;
        this.nome = nome;

    }

}
