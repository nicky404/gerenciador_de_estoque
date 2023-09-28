/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author Arthur
 */
public class Produto {
    private int id;
    private String nome;
    private double preço;
    private int qntEstoque;

    public Produto(int id, String nome, double preço, int qntEstoque) {
        this.id = id;
        this.nome = nome;
        this.preço = preço;
        this.qntEstoque = qntEstoque;
    }
    
        
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the preço
     */
    public double getPreço() {
        return preço;
    }

    /**
     * @param preço the preço to set
     */
    public void setPreço(double preço) {
        this.preço = preço;
    }

    /**
     * @return the qntEstoque
     */
    public int getQntEstoque() {
        return qntEstoque;
    }

    /**
     * @param qntEstoque the qntEstoque to set
     */
    public void setQntEstoque(int qntEstoque) {
        this.qntEstoque = qntEstoque;
    }
}
