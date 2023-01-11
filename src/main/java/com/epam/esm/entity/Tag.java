package com.epam.esm.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;
//    private  List<GiftCertificates> giftCertificates;

}
