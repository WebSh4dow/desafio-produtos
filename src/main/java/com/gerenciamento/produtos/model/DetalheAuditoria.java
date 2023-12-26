package com.gerenciamento.produtos.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "detalhe_auditoria")
@SequenceGenerator(name = "seq_detalhe_auditoria", sequenceName = "seq_detalhe_auditoria",allocationSize = 1, initialValue = 1)
@Data
public class DetalheAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_detalhe_auditoria")
    private Long id;

    @NotBlank(message = "O objeto alterado é um campo obrigatório")
    private String objetoAlterado;

    private Long objetoId;

    @NotBlank(message = "O campo é um campo obrigatório")
    private String campo;

    @Column(length = 1000)
    private String valorAnterior;

    @Column(length = 1000)
    private String valorAtual;

}