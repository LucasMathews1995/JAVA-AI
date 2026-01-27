package dev.ia.trip;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;



@Entity
@Table(name = "tb_viagens")
public class Trip extends PanacheEntity {

    @Column(unique = true,length = 100,nullable = false)
  public  String destination;
    public  Integer duracao;
    public String descricao;
    @Column(length = 200,nullable = false)
    public String atividades;
    @Column(nullable = false)
    public Double preco;
    @Column(nullable = false)
    public String politicaCancelamento;



    @Override
    public String toString() {
        return "Trip{" +
                "destination='" + destination + '\'' +
                ", duration=" + duracao +
                ", description='" + descricao + '\'' +
                ", atividades='" + atividades + '\'' +
                ", preco=" + preco +
                ", politicaCancelamento='" + politicaCancelamento + '\'' +
                '}';
    }
}
