package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    public static double  TAXA_DE_ENTREGA = 8.99;
    
    public int id;
    public String endereco;
    public List<Produto> produtos;
    public Cliente cliente;
    private double totalPedido;

    public Pedido(){
        this.produtos = new ArrayList<Produto>();
    }

    public Pedido(String end, Cliente cli){
        this.endereco = end;
        this.cliente = cli;
        this.produtos = new ArrayList<Produto>();
        this.totalPedido = 0;
    }

    //Metodos acessores - Getters (GET)
    public double getTotalPedido(){
        return this.totalPedido;
    }

    //Metodo Modificador - Setters (SET)
    public void setTotalPedido(double valor){
        if (valor >= TAXA_DE_ENTREGA)
            this.totalPedido = valor;
        else
            System.out.println("Valor nao permitido");
    }



    public void addProduto(Produto prod){
        if (this.produtos.size() ==0){
            this.totalPedido += TAXA_DE_ENTREGA;
        }
            this.produtos.add(prod);
            this.totalPedido += prod.preco;
    }

    // public void addProduto(Produto[] listadeProdutos){
    //     for (Produto p : listadeProdutos){
    //         this.produtos.add(p);
    //     }
    // }

    public void addProduto(Produto...n){
        if (this.produtos.size() == 0){
            this.totalPedido += TAXA_DE_ENTREGA;
        }
        
        for (Produto p : n){
            this.produtos.add(p);
        }
    }

    public void imprimirPedido(){
        System.out.println("\n-----------------");
        System.out.println("Pedido no end: " + this.endereco);
        System.out.println("Nome do cliente: " + this.cliente.nome);
        System.out.println("Nome da cidade do Cliente: " + this.cliente.cidade.nome);
        System.out.println("Total do Pedido: R$" + String.format("%.2f",this.totalPedido));
        if(this.produtos.size() == 0){
            System.out.println("Pedido Vazio");
        }else{
            for(Produto prod:this.produtos){
                System.out.println(prod.nome + " - " + prod.preco);
            }
        }
    }
}
