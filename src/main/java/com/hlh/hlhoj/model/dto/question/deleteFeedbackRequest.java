package com.hlh.hlhoj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class deleteFeedbackRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
