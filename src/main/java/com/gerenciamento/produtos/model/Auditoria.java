package com.gerenciamento.produtos.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "auditoria")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "seq_auditoria", sequenceName = "seq_auditoria",allocationSize = 1, initialValue = 1)
@Data
@ApiModel(description = "Modelo de representação de Auditoria")
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_auditoria")
    @ApiModelProperty(notes = "id da auditoria", example = "1")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "objeto que foi alterado", example = "Produtos")
    private String objetoAlterado;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Ação realizada", example = "EXCLUIR, DELETAR, INSERIR")
    private String acaoRealizada;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Informações sobre a data da auditoria", example = "2023-12-12")
    private Date dataHora;

    @Column(nullable = false)
    @CreatedBy
    @ApiModelProperty(notes = "Informações sobre qual usuário realizou determinada ação no sistema", example = "Jarmison Paiva")
    private String usuario;


}