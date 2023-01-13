package com.epam.esm.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GiftCertificates {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;
    private List<Tag> tags;


    public GiftCertificates() {

    }
}
