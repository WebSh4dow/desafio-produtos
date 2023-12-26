package com.gerenciamento.produtos.model;

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
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_auditoria")
    private Long id;

    @Column(nullable = false)
    private String objetoAlterado;

    @Column(nullable = false)
    private String acaoRealizada;

    @Column(nullable = false)
    private Date dataHora;

    @Column(nullable = false)
    @CreatedBy
    private String usuario;


}