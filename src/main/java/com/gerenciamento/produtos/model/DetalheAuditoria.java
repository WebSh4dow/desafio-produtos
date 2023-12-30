package com.gerenciamento.produtos.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "detalhe_auditoria")
@SequenceGenerator(name = "seq_detalhe_auditoria", sequenceName = "seq_detalhe_auditoria",allocationSize = 1, initialValue = 1)
@Data
@ApiModel(description = "Modelo de representação de detalhes da auditoria")
public class DetalheAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_detalhe_auditoria")
    @ApiModelProperty(notes = "id da auditoria detalhada", example = "1")
    private Long id;

    @NotBlank(message = "O objeto alterado é um campo obrigatório")
    @ApiModelProperty(notes = "objeto alterado da auditoria detalhada", example = "Produto")
    private String objetoAlterado;

    @ApiModelProperty(notes = "id do objeto alterado da auditoria detalhada", example = "1")
    private Long objetoId;

    @NotBlank(message = "O campo é um campo obrigatório")
    @ApiModelProperty(notes = "campo alterado e capturada pela auditoria detalhada", example = "nome,descricao,dataCadastro,status")
    private String campo;

    @Column(length = 1000)
    @ApiModelProperty(notes = "Valor anterior do campo que foi capturada pela auditoria detalhada", example = "nome,descricao,dataCadastro,status")
    private String valorAnterior;

    @Column(length = 1000)
    @ApiModelProperty(notes = "Valor atual do campo que foi capturada pela auditoria detalhada", example = "nome,descricao,dataCadastro,status")
    private String valorAtual;

}