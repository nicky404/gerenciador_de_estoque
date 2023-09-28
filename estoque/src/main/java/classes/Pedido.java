/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;


import java.util.Date;
import java.util.List;

/**
 *
 * @author Arthur
 */
public class Pedido {
    int id;
    Date data;
    List <ItemPedido> itens;

    public Pedido(int id, Date data, List<ItemPedido> itens) {
        this.id = id;
        this.data = data;
        this.itens = itens;
    }
        
    
public void adicionarItem (ItemPedido item) {
    
}

//public double calcularTotal () {
    //return true;
//}


}
