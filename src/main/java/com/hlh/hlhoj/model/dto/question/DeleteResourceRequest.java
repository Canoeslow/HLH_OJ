package com.hlh.hlhoj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
@Data
public class DeleteResourceRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
