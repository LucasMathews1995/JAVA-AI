package dev.ia.trip;

import dev.ia.trip.Category;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "tb_viagens")
public class Trip extends PanacheEntity {

    @Column(length = 100,nullable = false)
  public  String destination;
    public  Integer duracao;
    public String descricao;
    @Column(length = 200,nullable = false)
    public String atividades;
    @Column(nullable = false)
    public Double preco;
    @Column(nullable = false, name = "politica_cancelamento")
    public Integer politicaCancelamento;
    @Column(nullable = false )
    @Enumerated(EnumType.STRING)
    public Category category;



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
